package com.java.academy.controller;

import com.java.academy.model.Book;
import com.java.academy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

	@RequestMapping(value = "/rest/startBooks", method = RequestMethod.GET)
	public @ResponseBody List<Book> readStartBooks() {
		return bookService.getBooksByFilter("category", "book");
	}

	@RequestMapping(value = "/rest/books/{filter}/{data}", method = RequestMethod.GET)
	public @ResponseBody List<Book> read(@PathVariable String filter, @PathVariable String data) {
		List<Book> books = bookService.getBooksByFilter(filter, data);

		return books;
	}

	@RequestMapping("/addBooks")
	public String addBooks(Model model) {
		GandalfScrapper gandalfScrapper = new GandalfScrapper();
		bookService.addBooksFromLibrary(gandalfScrapper.getBooksFromGandalf());

		return "redirect:/robot/books";
	}
}
