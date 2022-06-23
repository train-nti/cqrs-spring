package id.go.beacukai.training.repository;

import id.go.beacukai.training.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Integer> {

    List<Actor> findByFirstNameContainingIgnoreCase(String infix);
    List<Actor> findByLastNameContainingIgnoreCase(String infix);
}
