package com.java.academy.Scheduler;

import com.java.academy.service.BookService;
import googlebookstore.GoogleBookStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import webScrappers.JSOUPLoader;
import webScrappers.gandalf.GandalfScrapper;
import webScrappers.mapper.BookMapper;
import webScrappers.mapper.BookMapperByStore;

import java.util.Date;

@Component
public class ScheduledTasks {

    @Autowired
    private BookService bookService;

    @Scheduled(fixedRate = 40000)
    public void scheduleFixedDelayTask() {
        System.out.println("Start collecting data: " + new Date().toString());
        GandalfScrapper gandalfScrapper = new GandalfScrapper(new JSOUPLoader());
        BookMapper mapper = new BookMapperByStore(gandalfScrapper);
        bookService.addBooksFromLibrary(mapper.collectBooksFromBookStore());
        GoogleBookStore bookStore = new GoogleBookStore();
        bookService.addBooksFromLibrary(bookStore.collectBooksFromGoogle());
    }
}