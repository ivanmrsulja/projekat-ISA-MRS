package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Preparat;

@Repository
public interface PreparatRepository extends JpaRepository<Preparat, Integer> {

}
