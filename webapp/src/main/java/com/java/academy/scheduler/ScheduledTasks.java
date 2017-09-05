package com.java.academy.scheduler;

import com.java.academy.service.BookService;
import googlebookstore.GoogleBookStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import webScrappers.BookScrapper;
import webScrappers.JSOUPLoader;
import webScrappers.czytamPl.CzytamyScrapper;
import webScrappers.gandalf.GandalfScrapper;
import webScrappers.mapper.BookMapper;
import webScrappers.mapper.BookMapperByStore;
import webScrappers.matras.MatrasScrapper;
import webScrappers.ravelo.RaveloScrapper;
import com.java.academy.model.Book;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    @Autowired
    private BookService bookService;
    final BookMapper mapper = new BookMapperByStore();
    @Scheduled(fixedRate = 24000000)
    public void scheduleFixedDelayTask() {
        System.out.println("Collecting data: " + new Date().toString());

        List<BookScrapper> bookstores = Arrays.asList(
                new GandalfScrapper(new JSOUPLoader()),
                new CzytamyScrapper(new JSOUPLoader()),
                new MatrasScrapper(new JSOUPLoader()),
                new RaveloScrapper(new JSOUPLoader())
        );

        bookService.addBooksFromLibrary(bookstores.stream()
                .flatMap(i -> mapper.collectBooksFromBookStore(i).stream())
                .collect(Collectors.toList()));

        GoogleBookStore bookStore = new GoogleBookStore();
        bookService.addBooksFromLibrary(bookStore.collectBooksFromGoogle());
    }
}