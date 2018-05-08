package cdg.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdg.dao.NameOnly;
import cdg.dao.State;

public interface StateRepository extends JpaRepository<State,Integer>,StateRepositoryCustom{
	List<State> findByName(String name);
	Collection<NameOnly> findAllProjectedBy();
	<T> Optional<T> findByPublicID(String publicID, Class<T> type);
}
