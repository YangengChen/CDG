package cdg.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdg.dao.NameOnly;
import cdg.dao.State;

public interface StateRepository extends JpaRepository<State,Integer>{
	List<State> findByName(String name);
	Collection<NameOnly> findAllProjectedBy();
	<T> List<T> findByPublicID(String publicID, Class<T> type);
	//<T> Optional<T> findByPublicID(String publicID, Class<T> type);
	//Optional<State> findOptionalProjectionByPublicID(String publicID);
}
