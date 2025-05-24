package ru.gnezdilov.web.json.type.delete;

import lombok.Getter;
import ru.gnezdilov.web.AbstractRequest;

@Getter
public class TypeDeleteRequest extends AbstractRequest {
    private int id;

    public void setId(String id) {
        this.id = this.extractInt("id", id);
    }
}
