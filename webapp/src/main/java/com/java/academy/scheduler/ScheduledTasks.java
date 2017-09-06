package com.java.academy.scheduler;

import logger.RLog;
import com.java.academy.service.BookService;
import googlebookstore.GoogleBookStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import webScrappers.BookScrapper;
import webScrappers.JSOUPLoader;
import webScrappers.czytamPl.CzytamyScrapper;
import webScrappers.gandalf.GandalfScrapper;
import webScrappers.ksiegarniaPWN.PWNscrapper;
import webScrappers.mapper.BookMapper;
import webScrappers.mapper.BookMapperByStore;
import webScrappers.matras.MatrasScrapper;
import webScrappers.ravelo.RaveloScrapper;
import webScrappers.taniaKsiazka.TaniaKsiazkaScrapper;
import com.java.academy.model.Book;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private BookService bookService;

    final BookMapper mapper = new BookMapperByStore();


//    @Scheduled(cron = "0 0 8 * * *")
    @Scheduled(fixedRate = 3200000)
    public void bookCollector() {

        RLog.info(RLog.getLogger(getClass()), ("Collecting data: " + new Date().toString()));

        List<BookScrapper> bookstores = Arrays.asList(
                new GandalfScrapper(new JSOUPLoader()),
                new MatrasScrapper(new JSOUPLoader()),
                new CzytamyScrapper(new JSOUPLoader()),
                new RaveloScrapper(new JSOUPLoader()),
                new PWNscrapper(new JSOUPLoader()),
                new TaniaKsiazkaScrapper(new JSOUPLoader())
        );

        for (BookScrapper bookScrapper : bookstores) {
            List<Book> books = mapper.collectBooksFromBookStore(bookScrapper);

            for (Book book : books) {
                System.out.println(book);
                bookService.addBook(book);
            }
        }

        GoogleBookStore bookStore = new GoogleBookStore();
        List<Book> books = bookStore.collectBooksFromGoogle();

        for (Book book : books) {
            bookService.addBook(book);
        }
    }
}