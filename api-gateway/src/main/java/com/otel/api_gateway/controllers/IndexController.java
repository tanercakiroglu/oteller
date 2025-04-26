package com.otel.api_gateway.controllers;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping

    public String index(Principal principal) {
        return "How are we doing, " + principal.getName() + "?!";
    }

}