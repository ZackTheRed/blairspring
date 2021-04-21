package com.blair.blairspring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/view")
public class MyViewController {

    @GetMapping("/greeting")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("view/greeting");
        modelAndView.addObject("name", "Lilimpakis");
        return modelAndView;
    }

}
