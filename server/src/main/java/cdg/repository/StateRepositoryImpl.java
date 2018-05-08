package cdg.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cdg.dao.State;

public class StateRepositoryImpl implements StateRepositoryCustom {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public void detach(State u) {
		entityManager.detach(u);
	}
}
