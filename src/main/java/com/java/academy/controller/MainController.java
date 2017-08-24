package com.java.academy.controller;

import com.google.gson.Gson;
import com.java.academy.logger.Log;

import com.java.academy.model.bookstore.googlebookstore.Book;
import com.java.academy.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Controller
@RequestMapping("/robot")
public class MainController {
	
	@Autowired
	BookService bookService;


	private static final Logger log = LoggerFactory.getLogger(MainController.class);

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
	
	@RequestMapping("/addBooks")
	public String addBooks(Model model) {
		model.addAttribute("start", "Robot application!");
		model.addAttribute("content", "Team: Paweł S., Artur, Mateusz B.");

		

		return "redirect:/robot/books";
	}
}
