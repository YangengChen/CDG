package cdg.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cdg.dao.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findOneByEmail(String email);

}

