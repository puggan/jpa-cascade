package se.puggan.jpatest.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.puggan.jpatest.entites.Student;
import se.puggan.jpatest.entites.Subject;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class Index
{
    @RequestMapping("/reinstall")
    @Transactional
    public String reinstall()
    {
        // 2 not connected entites
        Student.findOrCreate("Anna");
        Subject.findOrCreate("Math");

        // a pair
        Student Bertil = Student.findOrCreate("Bertil");
        Subject English = Subject.findOrCreate("English");
        Bertil.connect(English);

        // 2 Pairs
        Student Cesar = Student.findOrCreate("Cesar");
        Student David = Student.findOrCreate("David");
        Subject History = Subject.findOrCreate("History");
        Subject Biology = Subject.findOrCreate("Biology");

        History.connect(Cesar);
        History.connect(David);
        Biology.connect(Cesar);
        Biology.connect(David);

        return "Re-installed";
    }

    @RequestMapping("/student/{name}/remove")
    @Transactional
    public String deleteStudent(
        @PathVariable String name
    ) {
        Optional<Student> maybe = Student.find(name);
        if(!maybe.isPresent()) {
            return "Not found";
        }
        maybe.get().delete();
        return "Deleted";
    }

    @RequestMapping("/subject/{name}/remove")
    @Transactional
    public String deleteSubject(
        @PathVariable String name
    ) {
        Optional<Subject> maybe = Subject.find(name);
        if(!maybe.isPresent()) {
            return "Not found";
        }
        maybe.get().delete();
        return "Deleted";
    }
}
