package com.example.RGrid.controllers;

import java.util.Calendar;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.RGrid.model.*;
import com.example.RGrid.service.CustomUserDetailsService;

import events.OnRegistrationCompleteEvent;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	CustomUserDetailsService userService;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@GetMapping({"/", "/index"})
	public String index(Model model){
		model.addAttribute("hello", "Испытайте новую платформу для ведения совместных научных разработок");
		model.addAttribute("user", new Users());
		return "index";
	}
	
	@PostMapping("/register")
	public ModelAndView registerUser(@ModelAttribute("user") Users registeredUser, BindingResult result, 
			WebRequest request, Errors errors){
		Users u = new Users(registeredUser);
		System.out.println(u.toString());
		if (userService.addNewUser(u)){
			String appUrl = request.getContextPath();
	        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(u, request.getLocale(), appUrl));
			return new ModelAndView("registered", "user", u);
		}
		return new ModelAndView("index", "user", new Users());
	}
	
	
	@GetMapping(value = "/regitrationConfirm")
	public String confirmRegistration
	  (WebRequest request, Model model, @RequestParam("token") String token) {
	  
	    Locale locale = request.getLocale();
	     
	    VerificationToken verificationToken = userService.getVerificationToken(token);
	    if (verificationToken == null) {
	        model.addAttribute("message", "Недействительная ссылка подтверждения");
	        return "redirect:/badUser.html?lang=" + locale.getLanguage();
	    }
	     
	    Users user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        model.addAttribute("message", "Ссылка для подвтерждения истекла");
	        return "redirect:/badUser.html?lang=" + locale.getLanguage();
	    } 
	     
	    user.setActive(true); 
	    userService.saveRegisteredUser(user); 
	    return "redirect:/login.html?lang=" + request.getLocale().getLanguage(); 
	}

}
