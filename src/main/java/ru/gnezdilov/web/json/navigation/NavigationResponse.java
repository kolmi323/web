package ru.gnezdilov.web.json.navigation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NavigationResponse {
    private String url;
    private String userName;
    private String[] navigationItems;
}
