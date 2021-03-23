package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.Apoteka;

@Repository
public interface ApotekeRepository extends JpaRepository<Apoteka, Integer> {

}
