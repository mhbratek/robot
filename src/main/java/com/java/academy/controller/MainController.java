package com.java.academy.controller;

import com.google.gson.Gson;
import com.java.academy.logger.Log;
import com.java.academy.model.Book;
import com.java.academy.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
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

	private static @Log Logger LOG;

	@RequestMapping()
	public String welcome(Model model) {
		model.addAttribute("start", "Robot application!");
		model.addAttribute("content", "Team: Paweł S., Artur, Mateusz B.");


		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange("https://www.googleapis.com/books/v1/volumes?&q=-&fields=totalItems," +
				"items(volumeInfo/title,volumeInfo/subtitle,volumeInfo/description," +
				"saleInfo/retailPrice,saleInfo/listPrice/amount,volumeInfo/authors," +
				"volumeInfo/imageLinks/smallThumbnail)&maxResults=3" +
				"", HttpMethod.GET, entity, String.class);


		LOG.info(response.getBody());


		Book book = new Gson().fromJson(response.getBody(), Book.class);
		LOG.info(book.getItems().get(0).getVolumeInfo().getTitle());
		LOG.info(book.getItems().get(1).getVolumeInfo().getTitle());
		LOG.info(book.getItems().get(2).getVolumeInfo().getTitle());




		LOG.info("Welcome Logger!");
		return "start";
	}
	
	@RequestMapping("/books")
	public String books(Model model) {
		model.addAttribute("start", "Robot application!");
		model.addAttribute("content", "Team: Paweł S., Artur, Mateusz B.");
		
		model.addAttribute("books", bookService.getAllBooks());


		return "books";
	}

	@RequestMapping("/books2")
	public String books2(Model model) {
		model.addAttribute("books", bookService.getAllBooks());

		return "books2";
	}

	@RequestMapping(value = "/rest/books", method = RequestMethod.GET)
	public @ResponseBody
    List<Book> read() {

		return bookService.getAllBooks();
	}

	@RequestMapping("/addBooks")
	public String addBooks(Model model) {
		GandalfScrapper gandalfScrapper = new GandalfScrapper();
		bookService.addBooksFromLibrary(gandalfScrapper.getBooksFromGandalf());

		return "redirect:/robot/books";
	}
/*
	@RequestMapping("/addBooks")
	public String addBooks(Model model) {
		Bookstore bonito = new Bookstore();
		bonito.setName("Bonito");
		bonito.setUrl("www.bonito.pl");

		Book potop = new Book("Potop", "Henryk Sienkiewicz", "history novel", "-15%", new BigDecimal(42), bonito);
		potop.setUrl(bonito.getUrl());
		Book lalka = new Book("Lalka", "Bolesław Prus", "novel", "-25%", new BigDecimal(25), bonito);
		lalka.setUrl(bonito.getUrl());
		Book krewElfow = new Book("Krew Elfów", "Andrzej Sapkowski", "fantasy", "-15%", new BigDecimal(35), bonito);
		krewElfow.setUrl(bonito.getUrl());
		
		bookService.addBook(potop);
		bookService.addBook(lalka);
		bookService.addBook(krewElfow);

		Bookstore helion = new Bookstore();
		helion.setName("Helion");
		helion.setUrl("www.helion.pl");

		potop = new Book("Potop", "Henryk Sienkiewicz", "history novel", "-25%", new BigDecimal(32), helion);
		potop.setUrl(helion.getUrl());
		lalka = new Book("Lalka", "Bolesław Prus", "novel", "-20%", new BigDecimal(20), helion);
		lalka.setUrl(helion.getUrl());
		krewElfow = new Book("Krew Elfów", "Andrzej Sapkowski", "fantasy", "-35%", new BigDecimal(30), helion);
		krewElfow.setUrl(helion.getUrl());

		bookService.addBook(potop);
		bookService.addBook(lalka);
		bookService.addBook(krewElfow);

		return "redirect:/robot/books";
	}
}
