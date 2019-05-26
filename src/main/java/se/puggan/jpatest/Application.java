package se.puggan.jpatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.puggan.jpatest.entites.Student;
import se.puggan.jpatest.entites.Subject;
import se.puggan.jpatest.repo.StudentRepo;
import se.puggan.jpatest.repo.SubjectRepo;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@SpringBootApplication
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private StudentRepo students;
    @Autowired
    private SubjectRepo subjects;

    @Autowired
    private EntityManager em;

    @PostConstruct
    public void manualwire()
    {
        Student.setRepo(students, em);
        Subject.setRepo(subjects, em);
    }
}
