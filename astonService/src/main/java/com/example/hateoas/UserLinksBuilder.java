package com.example.hateoas;

import com.example.dto.UserResponse;
import com.example.dto.UserResponseLinksValue;

import java.util.Map;

public class UserLinksBuilder {

    public static Map<String, UserResponseLinksValue> buildForUser(Long id) {
        return Map.of(
                "self", new UserResponseLinksValue().href("/users/" + id),
                "all", new UserResponseLinksValue().href("/users"),
                "create", new UserResponseLinksValue().href("/users"),
                "update", new UserResponseLinksValue().href("/users/" + id),
                "delete", new UserResponseLinksValue().href("/users/" + id)
        );
    }

    public static void attachTo(UserResponse user) {
        user.setLinks(buildForUser(user.getId()));
    }
}