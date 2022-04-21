package com.system.management.library.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UiController {

    @RequestMapping("/")
    public String ui() {
        return "redirect:/ui/library";
    }
}
