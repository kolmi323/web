package ru.gnezdilov.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import ru.gnezdilov.SpringContext;
import ru.gnezdilov.web.controller.Controller;
import ru.gnezdilov.web.controller.SecureController;
import ru.gnezdilov.web.json.auth.AuthRequest;
import ru.gnezdilov.web.json.auth.AuthResponse;
import ru.gnezdilov.web.json.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {
    private ObjectMapper om = new ObjectMapper();
    private Map<String, Controller> controllers = new HashMap<>();
    private Map<String, SecureController> secureControllers = new HashMap<>();

    public MainServlet() {
        ApplicationContext context = SpringContext.getContext();
        for (String beanName : context.getBeanNamesForType(Controller.class)) {
            controllers.put(beanName,context.getBean(beanName, Controller.class));
        }
        for (String beanName : context.getBeanNamesForType(SecureController.class)) {
            secureControllers.put(beanName,context.getBean(beanName, SecureController.class));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String uri = req.getRequestURI();
        try {
            Controller controller = controllers.get(uri);
            if (controller != null) {
                if (controller instanceof AuthController) {
                    AuthController authController = (AuthController) controller;
                    AuthRequest authRequest = om.readValue(req.getInputStream(), authController.getRequestClass());
                    AuthResponse authResponse = authController.handle(authRequest);
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
                            controller.handle(om.readValue(req.getInputStream(), controller.getRequestClass()))
                    );
                }
            } else {
                SecureController secureController = secureControllers.get(uri);
                if (secureController != null) {
                    HttpSession session = req.getSession();
                    Integer userId = (Integer) session.getAttribute("userId");
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
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            om.writeValue(resp.getWriter(), new ErrorResponse(e.getMessage()));
        }
    }
}
