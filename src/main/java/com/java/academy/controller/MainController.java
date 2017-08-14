package com.java.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.academy.service.BookService;

@Controller
public class MainController {
	
	@Autowired
	BookService bookService;

	@RequestMapping("/")
	public String welcome(Model model) {
		model.addAttribute("start", "Robot application!");
		model.addAttribute("content", "Team: Pawel S., Artur, Mateusz B.");
		
		return "start";
	}
	
	@RequestMapping("/books")
	public String books(Model model) {
		model.addAttribute("start", "Robot application!");
		model.addAttribute("content", "Team: Pawel., Artur, Mateusz B.");
		
		model.addAttribute("books", bookService.getAllBooks());
		
		return "books";
	}
}
