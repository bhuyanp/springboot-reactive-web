package com.example.reactiveweb.service;

import com.example.reactiveweb.model.Book;
import com.example.reactiveweb.model.BookRequest;
import com.example.reactiveweb.repo.BookReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookReactiveRepository bookReactiveRepository;
    public Flux<Book> getBooks(){
        log.info("Getting all books");
        return bookReactiveRepository.findAll();
    }

    @Transactional
    public Flux<Book> addAllBooks(List<BookRequest> bookRequests) {
        log.info("Saving all books:"+bookRequests);
        List<Book> bookList = bookRequests.stream()
                .map(bookRequest -> Book.builder()
                        .author(bookRequest.author())
                        .title(bookRequest.title())
                        .publishDate(bookRequest.publishDate())
                        .build())
                .toList();
        return bookReactiveRepository.saveAll(bookList);
    }

    public Mono<Book> addBook(BookRequest bookRequest){
        log.info("Adding books: {}",bookRequest);
        return bookReactiveRepository.save(Book.builder()
                        .title(bookRequest.title())
                        .author(bookRequest.author())
                        .publishDate(bookRequest.publishDate())
                .build());
    }
}
