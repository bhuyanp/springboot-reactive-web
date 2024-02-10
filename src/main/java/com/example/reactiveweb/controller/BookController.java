package com.example.reactiveweb.controller;

import com.example.reactiveweb.model.Book;
import com.example.reactiveweb.model.BookRequest;
import com.example.reactiveweb.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> getBooks(){
        log.info("Getting all books");
        return bookService.getBooks().delayElements(Duration.ofSeconds(1));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> addBooks(@RequestBody BookRequest bookRequest){
        return bookService.addBook(bookRequest);
    }
}
