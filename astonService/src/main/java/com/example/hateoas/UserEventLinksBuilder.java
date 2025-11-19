package com.example.hateoas;

import com.example.dto.UserEvent;
import com.example.dto.UserEventLinksValue;

import java.util.Map;

public class UserEventLinksBuilder {

    public static Map<String, UserEventLinksValue> buildFor(String email) {
        return Map.of(
                "all", new UserEventLinksValue().href("/users"),
                "create", new UserEventLinksValue().href("/users"),
                "sendEvent", new UserEventLinksValue().href("/users/event")
        );
    }

    public static void attachTo(UserEvent event) {
        event.setLinks(buildFor(event.getEmail()));
    }
}