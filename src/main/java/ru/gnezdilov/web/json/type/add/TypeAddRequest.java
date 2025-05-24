package ru.gnezdilov.web.json.type.add;

import lombok.Getter;
import ru.gnezdilov.web.AbstractRequest;

@Getter
public class TypeAddRequest extends AbstractRequest {
    private String name;

    public void setName(String name) {
        this.name = this.extractString("name", name);
    }
}
