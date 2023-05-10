package com.fsd.sportyshoes.controllers;

import com.fsd.sportyshoes.models.Categories;
import com.fsd.sportyshoes.models.Products;
import com.fsd.sportyshoes.repositories.CategoriesRepository;
import com.fsd.sportyshoes.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("products", productsRepository.findAll());
        model.addAttribute("title", "All Products");

        model.addAttribute("selectedCategory", null);
        return "index";
    }
    @GetMapping("/shop/categories/{categoryId}")
    public String showProductsByCategoryId(Model model, @PathVariable("categoryId") Long categoryId) {
        model.addAttribute("categories", categoriesRepository.findAll());

        List<Products> products = productsRepository.findProductsByCategory_Id(categoryId);
        Categories category = categoriesRepository.findById(categoryId);
        String title = category.getName()+" Products";
        model.addAttribute("products", products);
        model.addAttribute("title", title);
        model.addAttribute("selectedCategory", category);
        return "index";
    }
}
