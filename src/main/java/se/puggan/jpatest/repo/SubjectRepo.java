package se.puggan.jpatest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.puggan.jpatest.entites.Subject;

import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Integer>
{
    Optional<Subject> findByName(String name);
}
