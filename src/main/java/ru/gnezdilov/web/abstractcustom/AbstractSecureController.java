package ru.gnezdilov.web.abstractcustom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.web.exception.MissingRequestParameterException;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractSecureController <REQ extends AbstractRequest, RES extends AbstractResponse> implements SecureController<REQ, RES> {
    private final ObjectMapper om;

    public AbstractSecureController() {
        this.om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
    }

    public RES doHandle(HttpServletRequest req, HttpServletResponse resp, int userId) throws IOException {
        RES response = null;
        try {
            response = handle(om.readValue(req.getInputStream(), getRequestClass()), userId);
        } catch (Exception e) {
            manageExceptions(resp, om, e);
        }
        return response;
    }

    private void manageExceptions(HttpServletResponse resp, ObjectMapper om, Exception e) throws IOException {
        if (e instanceof MissingRequestParameterException
                || e instanceof InsufficientFundsException
                || e instanceof java.lang.IllegalArgumentException) {
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

