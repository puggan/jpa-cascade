package se.puggan.jpatest.entites;

import se.puggan.jpatest.repo.StudentRepo;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import java.util.Optional;
import java.util.Set;

@Entity
public class Student
{
    //<editor-fold desc="DatabaseFields">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects;
    //</editor-fold>

    //<editor-fold desc="Repo">
    private static EntityManager manager;
    private static StudentRepo repo;

    public static void setRepo(StudentRepo r, EntityManager em)
    {
        if (repo == null)
        {
            repo = r;
            manager = em;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Constructors">
    public Student()
    {

    }
    //</editor-fold>

    public static Optional<Student> find(String name)
    {
        return repo.findByName(name);
    }

    //<editor-fold desc="Relation subjects">
    public void connect(Subject s)
    {
        s.connect(this);
    }

    public void disconnet(Subject s)
    {
        s.disconnet(this);
    }
    //</editor-fold>

    //<editor-fold desc="Crud">
    public static Student findOrCreate(String newName)
    {
        Optional<Student> maybe = repo.findByName(newName);
        if (maybe.isPresent())
        {
            return maybe.get();
        }

        Student s = new Student();
        s.name = newName;
        return s.save();
    }

    public Student reload()
    {
        manager.refresh(this);
        return this;
    }

    public Student save()
    {
        repo.saveAndFlush(this);
        return reload();
    }

    public void delete()
    {
        for (Subject s : subjects)
        {
            s.disconnet(this);
        }
        save();
        repo.delete(save());
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Set<Subject> getSubjects()
    {
        return subjects;
    }

    public static StudentRepo getRepo()
    {
        return repo;
    }
    //</editor-fold>
}