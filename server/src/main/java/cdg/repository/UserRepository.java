package cdg.repository;

import org.springframework.data.repository.CrudRepository;

import cdg.dao.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findOneByEmail(String email);

}

