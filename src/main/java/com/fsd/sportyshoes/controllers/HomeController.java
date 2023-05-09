package com.fsd.sportyshoes.controllers;

import com.fsd.sportyshoes.repositories.CategoriesRepository;
import com.fsd.sportyshoes.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/")
    public String showUserList(Model model) {
        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("products", productsRepository.findAll());
        model.addAttribute("title", "All Products");
        return "index";
    }
}
