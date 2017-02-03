package com.self.exercise.search.euler.controllers;

import com.self.exercise.search.euler.model.Problem;
import com.self.exercise.search.euler.service.ProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by prime23 on 1/29/17.
 */
@Controller
public class ProblemController {
    private static final Logger log = LoggerFactory.getLogger(ProblemController.class);

    private static final int PROBLEM_PER_PAGE = 53;

    @Autowired
    private ProblemService problemService;

    @GetMapping(path = {"/", "/index", "/problems"})
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page) {
        model.addAttribute("title", "Euler Search Page " + page);
        model.addAttribute("page", page + 1);
        List<Problem> problems = problemService.getAll(PROBLEM_PER_PAGE * (page - 1), PROBLEM_PER_PAGE);
        model.addAttribute("problems", problems);
        return "index";
    }

    @GetMapping(path = "/euler")
    public String search(Model model,
                         @RequestParam(name = "query") String searchParam) {
        log.info("search param: {}", searchParam);
        List<Problem> problems = problemService.getProblemsByQuery(searchParam);
        model.addAttribute("query", searchParam);
        model.addAttribute("problems", problems);
        return "index";
    }
}
