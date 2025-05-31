package ru.gnezdilov.web.abstractcustom;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.web.interfaces.AbstractRequest;
import ru.gnezdilov.web.interfaces.AbstractResponse;
import ru.gnezdilov.web.interfaces.Controller;
import ru.gnezdilov.web.json.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractController<REQ extends AbstractRequest, RES extends AbstractResponse> implements Controller<REQ, RES> {
    private final ObjectMapper om;
    private final UIUtils utils;

    public AbstractController(ObjectMapper om, UIUtils utils) {
        this.om = new ObjectMapper();
        this.utils = new UIUtils();
    }

    public RES doHandle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RES response = null;
        try {
            response = handle(om.readValue(req.getInputStream(), getRequestClass()));
        } catch (InsufficientFundsException | NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            om.writeValue(resp.getWriter(), new ErrorResponse(e.getMessage()));
        } catch (AlreadyExistsException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            om.writeValue(resp.getWriter(), new ErrorResponse(e.getMessage()));
        } catch(NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            om.writeValue(resp.getWriter(), new ErrorResponse(e.getMessage()));
        } catch (DAOException | DataSourceException e) {
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            om.writeValue(resp.getWriter(), new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            om.writeValue(resp.getWriter(), new ErrorResponse(e.getMessage()));
        }
        return response;
    }

    protected String extractEmail(String email) {
        if (!utils.isEmailCorrect(email)) {
            throw new IllegalArgumentException("Email is not valid" +
                    "\nEmail example: vasnecov@yandex.ru");
        }
        return email;
    }

    protected String extractPassword(String password) {
        if (!utils.isPasswordCorrect(password)) {
            throw new IllegalArgumentException("Password is too long");
        }
        return password;
    }
}
