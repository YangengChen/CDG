package cdg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdg.dao.ElectionResult;

public interface ElectionResultRepository extends JpaRepository<ElectionResult, Integer> {

}
