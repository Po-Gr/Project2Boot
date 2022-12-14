package com.example.springcourse.Project2Boot.services;

import com.example.springcourse.Project2Boot.dao.BookDAO;
import com.example.springcourse.Project2Boot.models.Book;
import com.example.springcourse.Project2Boot.models.Person;
import com.example.springcourse.Project2Boot.repositories.BooksRepository;
import com.example.springcourse.Project2Boot.repositories.PeopleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final BookDAO bookDAO;
    private final PeopleRepository peopleRepository;

    public BooksService(BooksRepository booksRepository, BookDAO bookDAO, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.bookDAO = bookDAO;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    public List<Book> getAllBooks(int page, int booksPerPage, String sortBy) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by(sortBy))).getContent();
    }

    public Book getBook(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> getBooksByTitleStarting(String string) {
        return booksRepository.findByTitleStartingWith(string);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book book, int id) {
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void setFree(int id) {
        Book book = getBook(id);
        book.setReader(null);
        book.setTakenAt(null);
    }

    @Transactional
    public void setBookToPerson(int id, int personId) {
        Book book = getBook(id);
        Person person = peopleRepository.findById(personId).orElse(null);

        book.setReader(person);
        book.setTakenAt(Calendar.getInstance());
        person.getBooks().add(book);
    }
}
