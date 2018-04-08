package cdg.repository;

import java.util.Collection;
import java.util.List;

import cdg.dao.NameOnly;
import cdg.dao.State;

public interface StateRepository /*extends CrudRepository<State,Integer>*/{
	List<State> findByName(String name);
	Collection<NameOnly> findAllProjectedBy();
}
