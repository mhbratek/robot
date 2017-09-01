package com.java.academy.scheduler;

import com.java.academy.service.BookService;
import googlebookstore.GoogleBookStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import webScrappers.JSOUPLoader;
import webScrappers.czytamPl.CzytamyScrapper;
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
        GandalfScrapper gandalfScrapper = new GandalfScrapper(new JSOUPLoader());
        BookMapper mapper = new BookMapperByStore(gandalfScrapper);
        bookService.addBooksFromLibrary(mapper.collectBooksFromBookStore());

        CzytamyScrapper czytamyScrapper = new CzytamyScrapper(new JSOUPLoader());
        mapper = new BookMapperByStore(czytamyScrapper);
        bookService.addBooksFromLibrary(mapper.collectBooksFromBookStore());
        GoogleBookStore bookStore = new GoogleBookStore();
        bookService.addBooksFromLibrary(bookStore.collectBooksFromGoogle());
    }
}