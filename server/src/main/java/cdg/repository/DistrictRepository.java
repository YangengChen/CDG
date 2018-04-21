package cdg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdg.dao.CongressionalDistrict;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DistrictRepository extends JpaRepository<CongressionalDistrict, Integer> {

}