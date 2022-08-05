package com.example.springcourse.Project2Boot.repositories;

import com.example.springcourse.Project2Boot.models.Book;
import com.example.springcourse.Project2Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByReader(Person reader);

    List<Book> findByTitleStartingWith(String letters);
}
