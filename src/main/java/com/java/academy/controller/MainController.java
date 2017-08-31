package com.java.academy.controller;

import com.google.gson.Gson;
import com.java.academy.model.Book;
import com.java.academy.model.BookListWrapper;
import com.java.academy.model.bookstore.googlebookstore.GoogleBookStore;
import com.java.academy.service.BookService;
import com.java.academy.webScrappers.JSOUPLoader;
import com.java.academy.webScrappers.gandalf.GandalfScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/robot")
public class MainController {
	
	@Autowired
	BookService bookService;

	@RequestMapping()
	public String welcome(Model model) {
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



    @RequestMapping(value = "/rest/books/{filter}/{data}", method = RequestMethod.GET)
    public @ResponseBody List<Book> read(@PathVariable String filter, @PathVariable String data) {
        List<Book> books = bookService.getBooksByFilter(filter, data);

        return books;
    }

	@RequestMapping(value = "/rest/add", method = RequestMethod.POST)
	public void addItems(@RequestBody String json) {
		Gson gson = new Gson();
		BookListWrapper bookListWrapper = gson.fromJson(json, BookListWrapper.class);
		bookService.addBooksFromLibrary(bookListWrapper.getBooks());
	}

	@RequestMapping("/addBooks")
	public String addBooks(Model model) {
		GandalfScrapper gandalfScrapper = new GandalfScrapper(new JSOUPLoader());
		bookService.addBooksFromLibrary(gandalfScrapper.collectBooksFromGandalfBookstore());
		GoogleBookStore bookStore = new GoogleBookStore();
		bookService.addBooksFromLibrary(bookStore.collectBooksFromGoogle());
		return "redirect:/robot/books";
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Wewnętrzny błąd serwera.")
	public void handleServerErrors(Exception ex) {	}
}
/*

try {
			TempSender.sendingPostRequest("http://localhost:8090/robot/rest/add2");

		} catch (Exception e) {
			e.printStackTrace();
		}

* */