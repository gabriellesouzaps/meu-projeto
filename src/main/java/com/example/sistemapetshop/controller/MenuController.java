package com.example.sistemapetshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menus")
public class MenuController {

    @GetMapping("/principal")
    public String carregaMenu() {
        return "menus/principal"; // vai procurar em templates/menus/principal.html
    }
}
