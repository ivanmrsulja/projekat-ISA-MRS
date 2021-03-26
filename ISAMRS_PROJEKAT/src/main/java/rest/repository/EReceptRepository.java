package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.ERecept;

@Repository
public interface EReceptRepository extends JpaRepository<ERecept, Integer> {

}
