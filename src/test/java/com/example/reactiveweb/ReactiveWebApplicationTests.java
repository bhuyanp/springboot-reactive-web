package com.example.reactiveweb;

import com.example.reactiveweb.model.Book;
import com.example.reactiveweb.repo.BookReactiveRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ReactiveWebApplicationTests {

    @MockBean
    BookReactiveRepository bookReactiveRepository;


    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldGetFluxBook() {
        //given
        List<Book> bookList = List.of(
                new Book(1, "Book A", "PBhuyan 11", LocalDate.now()),
                new Book(2, "Book BB", "PBhuyan 22", LocalDate.now()),
                new Book(3, "Book CCC", "PBhuyan 33", LocalDate.now())
        );
        when(bookReactiveRepository.findAll()).thenReturn(Flux.fromIterable(
                bookList
        ));
        //when
        WebTestClient.ResponseSpec responseSpec = webTestClient
                .get().uri("/api/books")
                .header(HttpHeaders.ACCEPT, MediaType.TEXT_EVENT_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange();
        //then
        responseSpec
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .consumeWith(result->
                        assertThat(result.getResponseBody()).containsAll(bookList)
                );
        Mockito.verify(bookReactiveRepository, times(1)).findAll();

    }
}
