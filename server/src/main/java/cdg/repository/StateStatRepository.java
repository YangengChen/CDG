package cdg.repository;

import org.springframework.data.repository.CrudRepository;

import cdg.dao.StateStat;

public interface StateStatRepository extends CrudRepository<StateStat, Long> {

    StateStat findOneByName(String name);
    
}
