package se.puggan.jpatest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.puggan.jpatest.entites.Student;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>
{
    Optional<Student> findByName(String name);
}
