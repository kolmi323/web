package ru.gnezdilov.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.web.abstractcustom.AbstractController;
import ru.gnezdilov.web.controller.start.AuthController;
import ru.gnezdilov.web.exception.MissingRequestParameterException;
import ru.gnezdilov.web.interfaces.Controller;
import ru.gnezdilov.web.interfaces.InformationController;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.auth.AuthRequest;
import ru.gnezdilov.web.json.auth.AuthResponse;
import ru.gnezdilov.web.json.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {
    private ObjectMapper om = new ObjectMapper();
    private Map<String, AbstractController> controllers = new HashMap<>();
    private Map<String, SecureController> secureControllers = new HashMap<>();
    private Map<String, InformationController> informationControllers = new HashMap<>();

    public MainServlet() {
        ApplicationContext context = SpringContext.getContext();
        for (String beanName : context.getBeanNamesForType(AbstractController.class)) {
            controllers.put(beanName,context.getBean(beanName, AbstractController.class));
        }
        for (String beanName : context.getBeanNamesForType(SecureController.class)) {
            secureControllers.put(beanName,context.getBean(beanName, SecureController.class));
        }
        for (String beanName : context.getBeanNamesForType(InformationController.class)) {
            informationControllers.put(beanName,context.getBean(beanName, InformationController.class));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String uri = req.getRequestURI();
        try {
            AbstractController controller = controllers.get(uri);
            SecureController secureController = secureControllers.get(uri);
            InformationController informationController = informationControllers.get(uri);
            if (controller != null) {
                managementController(req, resp, controller);
            } else if (secureController != null) {
                managementSecureController(req, resp, secureControllers.get(uri));
            } else if (informationController != null) {
                managementInformationController(req, resp, informationControllers.get(uri));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            managementException(resp, om, e);
        }
    }

    private Integer extractUserId(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (Integer) session.getAttribute("userId");
    }

    private void managementController(HttpServletRequest req, HttpServletResponse resp, AbstractController controller) throws IOException {
        if (controller instanceof AuthController) {
            AuthController authController = (AuthController) controller;
            AuthResponse authResponse = authController.doHandle(req, resp);
            if (authResponse != null) {
                HttpSession session = req.getSession();
                session.setAttribute("userId", authResponse.getId());
                om.writeValue(resp.getWriter(), authResponse);
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            om.writeValue(
                    resp.getWriter(),
                    controller.doHandle(req, resp)
            );
        }
    }

    private void managementSecureController(HttpServletRequest req, HttpServletResponse resp, SecureController secureController) throws IOException {
        Integer userId = extractUserId(req);
        if (userId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            om.writeValue(
                    resp.getWriter(),
                    secureController.handle(
                            om.readValue(req.getInputStream(),secureController.getRequestClass()), userId
                    )
            );
        }
    }

    private void managementInformationController(HttpServletRequest req, HttpServletResponse resp, InformationController informationController) throws IOException {
        Integer userId = extractUserId(req);
        if (userId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            om.writeValue(
                    resp.getWriter(),
                    informationController.handle(userId)
            );
        }
    }

    private void managementException(HttpServletResponse resp, ObjectMapper om, Exception e) throws IOException {
        if (e instanceof MissingRequestParameterException
                || e instanceof InsufficientFundsException
                || e instanceof IllegalArgumentException) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (e instanceof AlreadyExistsException) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } else if (e instanceof NotFoundException) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if (e instanceof DAOException
                || e instanceof DataSourceException) {
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        om.writeValue(resp.getWriter(), new ErrorResponse(e.getMessage()));
    }
}
