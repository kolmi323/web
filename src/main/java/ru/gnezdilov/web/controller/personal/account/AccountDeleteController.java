package ru.gnezdilov.web.controller.personal.account;

import lombok.RequiredArgsConstructor;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.json.DeleteRequest;
import ru.gnezdilov.web.json.DeleteResponse;


@RequiredArgsConstructor
public class AccountDeleteController extends AbstractSecureController<DeleteRequest, DeleteResponse> {
    private final AccountService accountService;

    @Override
    public DeleteResponse handle(DeleteRequest request, int userId) {
        boolean isDelete = accountService.delete(request.getId(), userId);
        return new DeleteResponse(isDelete);
    }

    @Override
    public Class<DeleteRequest> getRequestClass() {
        return DeleteRequest.class;
    }
}
