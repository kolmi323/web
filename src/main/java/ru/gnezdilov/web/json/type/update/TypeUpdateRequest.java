package ru.gnezdilov.web.json.type.update;

import lombok.Getter;
import ru.gnezdilov.web.abstractcustom.AbstractRequest;

@Getter
public class TypeUpdateRequest extends AbstractRequest {
    private int id;
    private String newName;

    public void setId(String id) {
        this.id = this.extractInt("id", id);
    }

    public void setNewName(String newName) {
        this.newName = this.extractString("newName", newName);
    }
}
