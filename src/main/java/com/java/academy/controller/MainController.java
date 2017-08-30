package com.java.academy.controller;

import com.java.academy.model.Book;
import com.java.academy.model.bookstore.googlebookstore.*;
import com.java.academy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

		return "books";
	}

	@RequestMapping("/booksTiles")
	public String books2(Model model) {

		return "booksTiles";
	}

	@RequestMapping(value = "/rest/startBooks", method = RequestMethod.GET)
	public @ResponseBody List<Book> readStartBooks() {
		List<Book> books = bookService.getBooksByFilter("category", "book");
		return books;
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

		GoogleBookStore bookStore = new GoogleBookStore();
		bookService.addBooksFromLibrary(bookStore.collectBooksFromGoogle());
		return "redirect:/robot/books";
	}

}
