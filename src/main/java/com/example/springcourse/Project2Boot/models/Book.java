package com.example.springcourse.Project2Boot.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Book title should not be empty")
//    @Size(min = 2, max = 50, message = "Book title has incorrect size!")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Author name should not be empty")
    @Size(min = 2, max = 50, message = "Author name has incorrect size!")
    private String author;

    @Column(name = "year")
    @Min(value = 1, message = "Book year should be greater than 1")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person reader;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar takenAt;

    @Transient
    private Boolean isExpired;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public Person getReader() {
        return reader;
    }

    public void setReader(Person reader) {
        this.reader = reader;
    }

    public Calendar getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Calendar takenAt) {
        this.takenAt = takenAt;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }
}
