package com.example.springcourse.Project2Boot.controllers;

import com.example.springcourse.Project2Boot.logic.Paging;
import com.example.springcourse.Project2Boot.models.Book;
import com.example.springcourse.Project2Boot.models.Person;
import com.example.springcourse.Project2Boot.services.BooksService;
import com.example.springcourse.Project2Boot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/library/books")
public class BookController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String getAllBooks(@ModelAttribute("searchBy") Book book, Model model, @RequestParam(value = "page", required = false) Integer page,  // посмотреть без атрибута модели
                              @RequestParam(value = "items_per_page", required = false) Integer itemsPerPage,
                              @RequestParam(value = "sort_by_year",required = false) Boolean sortByYear) {
        Paging paging = Paging.pageFabric(page, itemsPerPage, sortByYear, peopleService.getAllPeople().size(), Book.class);

        model.addAttribute("books", booksService.getAllBooks(paging.getPage(), paging.getItemsPerPage(), paging.getSortBy()));
        model.addAttribute("paging", paging);

        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id")int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.getBook(id));
        model.addAttribute("people", peopleService.getAllPeople());
        model.addAttribute("reader", booksService.getBook(id).getReader());
        return "books/book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        booksService.save(book);
        return "redirect:";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id, Model model) {
        model.addAttribute("book", booksService.getBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id")int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(book, id);
        return "redirect:";
    }

    @GetMapping("/{id}/delete")
    public String exactDelete(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.getBook(id));
        return "books/delete";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id) {
        booksService.delete(id);
        return "redirect:.";
    }

    @PutMapping("/{id}/free")
    public String setFree(@PathVariable("id")int id) {
        booksService.setFree(id);
        return "redirect:.";
    }

    @PatchMapping("/{id}/add")
    public String setBookToPerson(@ModelAttribute("person") Person person, @PathVariable("id")int id) {
        booksService.setBookToPerson(id, person.getId());
        return "redirect:.";
    }

    @GetMapping("/search")
    public String searchByTitle(Model model, @RequestParam(value = "title", required = false) String startWith,
                                @ModelAttribute("searchBy") @Valid Book book, BindingResult bindingResult) {

        if(startWith != null && !startWith.equals("")) {
            List<Book> books = booksService.getBooksByTitleStarting(startWith);
            model.addAttribute("books", books);
            if (bindingResult.hasErrors())
                return "books/search";
        }
        return "books/search";
    }
}
