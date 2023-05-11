package com.template.domain.repositories;

import java.util.Optional;
import com.template.domain.models.User;
import java.util.List;

public interface UserRepository {
	Optional<User> findById(Long id);

	User save(User user);

	List<User> findAll();

	void deleteById(Long id);
}