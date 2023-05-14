package com.fsd.sportyshoes.controllers;
import com.fsd.sportyshoes.models.Categories;
import com.fsd.sportyshoes.models.CustomerOrders;
import com.fsd.sportyshoes.models.Products;
import com.fsd.sportyshoes.models.Users;
import com.fsd.sportyshoes.repositories.CategoriesRepository;
import com.fsd.sportyshoes.repositories.CustomerOrdersRepository;
import com.fsd.sportyshoes.repositories.ProductsRepository;
import com.fsd.sportyshoes.repositories.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/customer")
public class CustomerController {

    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CustomerOrdersRepository customerOrdersRepository;

    @GetMapping(path="/")
    public String customerHome(Model model, HttpSession session){
        this.validateSession(model, session);

        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("products", productsRepository.findAll());
        model.addAttribute("title", "Welcome "+session.getAttribute("userName"));

        model.addAttribute("selectedCategory", null);
        return "customer/index";
    }

    @GetMapping("/categories/{categoryId}")
    public String showProductsByCategoryId(Model model, @PathVariable("categoryId") Long categoryId) {
        model.addAttribute("categories", categoriesRepository.findAll());

        List<Products> products = productsRepository.findProductsByCategory_Id(categoryId);
        Categories category = categoriesRepository.findById(categoryId);
        String title = category.getName()+" Products";
        model.addAttribute("products", products);
        model.addAttribute("title", title);
        model.addAttribute("selectedCategory", category);
        return "customer/index";
    }


    @GetMapping(path="/orders")
    public String customerOrders(Model model, HttpSession session){
        String userId = (String) session.getAttribute("userId");
        Users user = usersRepository.findFirstByEmail(userId);
        List customerOrders = customerOrdersRepository.findCustomerOrdersByUser_Id(user.getId());

        model.addAttribute("orders", customerOrders);

        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("products", productsRepository.findAll());
        model.addAttribute("title", "My Orders");

        model.addAttribute("selectedCategory", null);
        return "customer/orders";
    }

    @PostMapping(path="/order")
    public String customerOrder(Model model, HttpSession session, @RequestParam Long productId){
        //save the order
        String userId = (String) session.getAttribute("userId");
        Users user = usersRepository.findFirstByEmail(userId);
        Products product  = productsRepository.findFirstById(productId);
        CustomerOrders order = new CustomerOrders();
        order.setProducts(product);
        order.setUser(user);
        order.setOrderDate(new Date());
        customerOrdersRepository.save(order);


        this.setAlert(model, "Order added successfully!", "alert-success");

        List customerOrders = customerOrdersRepository.findCustomerOrdersByUser_Id(user.getId());
        model.addAttribute("orders", customerOrders);

        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("products", productsRepository.findAll());
        model.addAttribute("title", "My Orders");

        model.addAttribute("selectedCategory", null);
        return "redirect:/customer/orders";
    }

    @PostMapping(path="/order/delete")
    public String deleteOrder(Model model, HttpSession session, @RequestParam Integer orderId){
        //save the order
        String userId = (String) session.getAttribute("userId");
        Users user = usersRepository.findFirstByEmail(userId);

        customerOrdersRepository.deleteById(orderId);
        this.setAlert(model, "Order deleted successfully.", "alert-success");


        //users orders
        List customerOrders = customerOrdersRepository.findCustomerOrdersByUser_Id(user.getId());
        model.addAttribute("orders", customerOrders);

        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("products", productsRepository.findAll());
        model.addAttribute("title", "My Orders");

        model.addAttribute("selectedCategory", null);
        return "customer/index";
    }

    //validate the user
    public String validateSession(Model model, HttpSession session){
        String userId = (String) session.getAttribute("userId");
        Users user = usersRepository.findFirstByEmail(userId);
        if(user == null){
            return "redirect:auth/login";
        }

        if(!user.getRights().equals("customer")){
            return "redirect:auth/logout";
        }
        return "";
    }

    private void setAlert(Model model, String alert, String alertClass){
        model.addAttribute("alert", alertClass);
        model.addAttribute("alertText", alert);
    }


}
