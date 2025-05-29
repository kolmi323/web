package ru.gnezdilov.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.web.abstractcustom.AbstractController;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.controller.start.AuthController;
import ru.gnezdilov.web.exception.MissingRequestParameterException;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.ErrorResponse;
import ru.gnezdilov.web.json.auth.AuthResponse;

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
    private Map<String, AbstractSecureController> secureControllers = new HashMap<>();

    public MainServlet() {
        ApplicationContext context = SpringContext.getContext();
        for (String beanName : context.getBeanNamesForType(AbstractController.class)) {
            controllers.put(beanName,context.getBean(beanName, AbstractController.class));
        }
        for (String beanName : context.getBeanNamesForType(AbstractSecureController.class)) {
            secureControllers.put(beanName,context.getBean(beanName, AbstractSecureController.class));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String uri = req.getRequestURI();
        try {
            AbstractController controller = controllers.get(uri);
            SecureController secureController = secureControllers.get(uri);
            if (controller != null) {
                manageController(req, resp, controller);
            } else if (secureController != null) {
                manageSecureController(req, resp, secureControllers.get(uri));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            om.writeValue(resp.getWriter(), new ErrorResponse(e.getMessage()));
        }
    }

    private Integer extractUserId(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (Integer) session.getAttribute("userId");
    }

    private void manageController(HttpServletRequest req, HttpServletResponse resp, AbstractController controller) throws IOException {
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

    private void manageSecureController(HttpServletRequest req, HttpServletResponse resp, AbstractSecureController secureController) throws IOException {
        Integer userId = extractUserId(req);
        if (userId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            om.writeValue(
                    resp.getWriter(),
                    secureController.doHandle(req, resp, userId)
            );
        }
    }
}
