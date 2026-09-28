package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/disasters")
    public String disasters() {
        return "disasters";
    }

    @GetMapping("/resources")
    public String resources() {
        return "resources";
    }

    @GetMapping("/requests")
    public String requests() {
        return "requests";
    }

    @GetMapping("/volunteers")
    public String volunteers() {
        return "volunteers";
    }

    @GetMapping("/donations")
    public String donations() {
        return "donations";
    }
}
