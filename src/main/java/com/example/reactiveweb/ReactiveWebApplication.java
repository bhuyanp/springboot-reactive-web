package com.example.reactiveweb;

import com.example.reactiveweb.model.BookRequest;
import com.example.reactiveweb.repo.BookReactiveRepository;
import com.example.reactiveweb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ReactiveWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveWebApplication.class, args);
    }

    @Autowired
    BookService bookService;

    @Autowired
    BookReactiveRepository bookReactiveRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            List<BookRequest> bookRequests = List.of(
                    new BookRequest("Book A", "PBhuyan 11", LocalDate.now()),
                    new BookRequest("Book BB", "PBhuyan 22", LocalDate.now()),
                    new BookRequest("Book CCC", "PBhuyan 33", LocalDate.now()),
                    new BookRequest("Book DDDD", "PBbhuyan 44", LocalDate.now()),
                    new BookRequest("Book EEEEE", "PBbhuyan 55", LocalDate.now())
            );
            bookService.addAllBooks(bookRequests).collectList().block();
        };

    }

}
