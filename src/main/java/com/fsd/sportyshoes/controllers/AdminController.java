package com.fsd.sportyshoes.controllers;

import com.fsd.sportyshoes.models.Categories;
import com.fsd.sportyshoes.models.Products;
import com.fsd.sportyshoes.repositories.CategoriesRepository;
import com.fsd.sportyshoes.repositories.CustomerOrdersRepository;
import com.fsd.sportyshoes.repositories.ProductsRepository;
import com.fsd.sportyshoes.repositories.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {


    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CustomerOrdersRepository customerOrdersRepository;

    @GetMapping(path = "/")
    public String adminHome(Model model, HttpSession session) {

        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("products", productsRepository.findAll());
        model.addAttribute("title", "Welcome " + session.getAttribute("userName"));

        model.addAttribute("selectedCategory", null);
        return "admin/index";
    }

    @GetMapping(path = "/products")
    public String adminProducts(Model model, HttpSession session) {

        model.addAttribute("products", productsRepository.findAll());
        model.addAttribute("title", "Products");

        model.addAttribute("selectedCategory", null);
        return "admin/products";
    }

    @PostMapping(path = "/products/edit")
    public String adminEditProducts(Model model, HttpSession session, @RequestParam String productId) {

        Products p = productsRepository.findFirstById(Long.valueOf(productId));
        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("products", productsRepository.findAll());
        model.addAttribute("product", p);
        model.addAttribute("title", "Products");

        model.addAttribute("selectedCategory", null);
        return "admin/productsEdit";
    }

    @PostMapping(path = "/products/update")
    public String adminUpdateProducts(
            Model model,
            HttpSession session,
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam String price,
            @RequestParam String description,
            @RequestParam String categoryId,
            @RequestParam String stock
    ) {

        Products p = productsRepository.findFirstById(Long.valueOf(id));
        p.setStock(Integer.valueOf(stock));
        p.setName(name);
        p.setPrice(Double.valueOf(price));
        p.setDescription(description);
        Categories c = categoriesRepository.findById(Long.valueOf(categoryId));
        p.setCategory(c);
        productsRepository.save(p);

        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("products", productsRepository.findAll());
        model.addAttribute("product", p);
        model.addAttribute("title", "Products");

        model.addAttribute("selectedCategory", null);

        model.addAttribute("alert", "alert-success");
        model.addAttribute("alertText", "Product saved successfully!");
        return "admin/products";
    }

    @GetMapping(path = "/categories")
    public String adminCategories(Model model, HttpSession session) {

        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("title", "Categories");

        model.addAttribute("selectedCategory", null);
        return "admin/categories";
    }

    @PostMapping(path = "/categories/store")
    public String storeCategory(Model model, HttpSession session, @RequestParam String name) {

        Categories category = new Categories();
        category.setName(name);
        categoriesRepository.save(category);
//        this.setAlert(model, "Category saved successfully!", "alert-success");
        model.addAttribute("alert", "alert-success");
        model.addAttribute("alertText", "Category saved successfully!");
        return "redirect:/admin/categories";

    }

    @PostMapping(path = "/categories/delete")
    public String deleteCategory(Model model, HttpSession session, @RequestParam String id) {
        categoriesRepository.deleteById(Integer.valueOf(id));
        this.setAlert(model, "Category deleted successfully!", "alert-success");
        model.addAttribute("alert", "alert-success");
        model.addAttribute("alertText", "Category saved successfully!");
        return "redirect:/admin/categories";

    }

    @GetMapping(path = "/orders")
    public String adminOrders(Model model, HttpSession session) {

        model.addAttribute("orders", customerOrdersRepository.findAll());
        model.addAttribute("title", "Customer Orders");

        model.addAttribute("selectedCategory", null);
        return "admin/orders";
    }

    @GetMapping(path = "/customers")
    public String adminCustomers(Model model, HttpSession session) {

        model.addAttribute("customers", usersRepository.findAll());
        model.addAttribute("orders", customerOrdersRepository.findAll());
        model.addAttribute("title", "Customers");

        model.addAttribute("selectedCategory", null);
        return "admin/customers";
    }

    private void setAlert(Model model, String alert, String alertClass) {
        model.addAttribute("alert", alertClass);
        model.addAttribute("alertText", alert);
    }


}
