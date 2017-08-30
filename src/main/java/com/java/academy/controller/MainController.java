package com.java.academy.controller;

import com.java.academy.model.Book;
import com.java.academy.model.bookstore.googlebookstore.*;
import com.java.academy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

import com.java.academy.model.Bookstore;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import webscrapper.gandalBookStore.GandalfScrapper;

@Controller
@RequestMapping("/robot")
public class MainController {
	
	@Autowired
	BookService bookService;

	@RequestMapping()
	public String welcome(Model model) {
		model.addAttribute("start", "Robot application!");
		model.addAttribute("content", "Team: Paweł S., Artur, Mateusz B.");

		return "start";
	}
	
	@RequestMapping("/books")
	public String books(Model model) {
		model.addAttribute("books", bookService.getAllBooks());

		return "books";
	}

	@RequestMapping("/books2")
	public String books2(Model model) {
		model.addAttribute("books", bookService.getAllBooks());

		return "books2";
	}

	@RequestMapping(value = "/rest/books", method = RequestMethod.GET)
	public @ResponseBody List<Book> read() {

		return bookService.getAllBooks();
	}

	@RequestMapping("/addBooks")
	public String addBooks(Model model) {
		GandalfScrapper gandalfScrapper = new GandalfScrapper();
		bookService.addBooksFromLibrary(gandalfScrapper.getBooksFromGandalf());

		GoogleBookStore bookStore = new GoogleBookStore();
		bookService.addBooksFromLibrary(bookStore.collectBooksFromGoogle());
		return "redirect:/robot/books";
	}

}
