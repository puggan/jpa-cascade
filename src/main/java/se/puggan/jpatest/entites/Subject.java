package se.puggan.jpatest.entites;

import se.puggan.jpatest.repo.SubjectRepo;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import java.util.Optional;
import java.util.Set;

@Entity
public class Subject
{
    //<editor-fold desc="DatabaseFields">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany
    private Set<Student> students;
    //</editor-fold>

    //<editor-fold desc="Repo">
    private static EntityManager manager;
    private static SubjectRepo repo;

    public static void setRepo(SubjectRepo r, EntityManager em)
    {
        if (repo == null)
        {
            repo = r;
            manager = em;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Constructors">
    public Subject()
    {

    }
    //</editor-fold>

    //<editor-fold desc="Relation students">
    public Subject connect(Student s)
    {
        students.add(s);
        return save();
    }

    public Subject disconnet(Student s)
    {
        students.remove(s);
        return save();
    }
    //</editor-fold>

    //<editor-fold desc="CRUD">
    public static Subject findOrCreate(String name)
    {
        Optional<Subject> maybe = repo.findByName(name);
        if(maybe.isPresent())
        {
            return maybe.get();
        }

        Subject s = new Subject();
        s.name = name;
        return s.save();
    }

    public Subject reload()
    {
        manager.refresh(this);
        return this;
    }

    public Subject save()
    {
        repo.saveAndFlush(this);
        return reload();
    }

    public void delete()
    {
        students.clear();
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

    public Set<Student> getStudents()
    {
        return students;
    }

    public static SubjectRepo getRepo()
    {
        return repo;
    }
    //</editor-fold>
}