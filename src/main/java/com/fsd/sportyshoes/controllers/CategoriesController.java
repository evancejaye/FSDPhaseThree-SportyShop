package com.fsd.sportyshoes.controllers;

import com.fsd.sportyshoes.models.Categories;
import com.fsd.sportyshoes.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/categories")
public class CategoriesController {
    @Autowired
    private CategoriesRepository categoriesRepository;

    //list all categories
    @GetMapping("/")
    public String showUserList(Model model) {
        model.addAttribute("categories", categoriesRepository.findAll());
        return "categories/index";
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name) {
        Categories c = new Categories();
        c.setName(name);
        categoriesRepository.save(c);
        return "Saved";
    }

}
