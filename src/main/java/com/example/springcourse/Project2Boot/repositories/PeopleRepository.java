package com.example.springcourse.Project2Boot.repositories;

import com.example.springcourse.Project2Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findByFullNameStartingWith(String letters);
}
