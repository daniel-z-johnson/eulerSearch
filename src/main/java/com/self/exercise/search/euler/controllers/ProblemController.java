package com.self.exercise.search.euler.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by prime23 on 1/29/17.
 */
@Controller
public class ProblemController {
    @GetMapping(path = {"/", "/index", "/problems"})
    public String index(Model model) {
        model.addAttribute("title", "index");
        return "index";
    }
}
