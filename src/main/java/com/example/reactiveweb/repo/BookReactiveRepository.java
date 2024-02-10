package com.example.reactiveweb.repo;

import com.example.reactiveweb.model.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReactiveRepository extends ReactiveCrudRepository<Book, Integer> {
}
