package cdg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdg.dao.SavedMap;

public interface SavedMapRepository extends JpaRepository<SavedMap, String>{

}
