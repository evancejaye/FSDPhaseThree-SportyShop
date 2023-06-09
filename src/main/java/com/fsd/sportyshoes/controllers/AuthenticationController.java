package com.fsd.sportyshoes.controllers;

import com.fsd.sportyshoes.models.Categories;
import com.fsd.sportyshoes.models.Users;
import com.fsd.sportyshoes.repositories.CategoriesRepository;
import com.fsd.sportyshoes.repositories.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping(path="/auth")
public class AuthenticationController {
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("title", "Login");
        return "auth/login";


    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("title", "Signup");
        return "auth/signup";
    }

    @PostMapping("/register")
    public String signUpUser(Model model, @RequestParam String name,@RequestParam String email,@RequestParam String password,@RequestParam String rights) {
        //create a new user
        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setRights(rights);
        user.setPassword(password);
        usersRepository.save(user);

        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("title", "Login");
        model.addAttribute("alert", "alert-success");
        model.addAttribute("alertText", "User created successfully. Please login to continue.");


        return "auth/login";
    }


    //list all categories
    @PostMapping("/authenticate")
    public String loginUser(Model model, HttpSession session, @RequestParam String email, @RequestParam String password) {
        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("title", "Login");

        model.addAttribute("title", "Login");
        model.addAttribute("alert", "alert-danger");
        model.addAttribute("alertText", "Invalid username or password. Please try again.");



        Users user  = usersRepository.findFirstByEmail(email);

        System.out.println(user.getPassword());
        if(user == null || !user.getPassword().equals(password)){
            //user not found
            return "auth/login";
        }

        if(user.getPassword().equals(password)){
            model.addAttribute("alert", "alert-success");
            model.addAttribute("alertText", "Login successfully.");
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getEmail());
            session.setAttribute("userName", user.getName());
            session.setAttribute("rights", user.getRights());
            model.addAttribute("title", "Welcome "+user.getName());
            if(user.getRights().equals("admin")){
                //go to admin page
                return "redirect:/admin/";
            }else{
                //go to customer
                return "redirect:/customer/";
            }

        }

        return "auth/login";
    }

    //logout
    @GetMapping(path = "/logout")
    public String logOut(Model model, HttpSession session){
        //remove the session
        session.removeAttribute("userId");
        session.removeAttribute("userName");
        session.removeAttribute("userRights");

        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("title", "Login");

        model.addAttribute("title", "Login");
        model.addAttribute("alert", "alert-danger");
        model.addAttribute("alertText", "Session expired, please login to continue.");

        return "auth/login";
    }
}
