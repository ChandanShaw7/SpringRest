package com.stackrest.springrest.repository;

import com.stackrest.springrest.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	
	@Nullable
	public User findByUserName(String userName);
	@Nullable
	public Iterable<User> findByCityAndZipOrderByUserNameAsc(String city, int zip);
	public void removeByUserName(String userName);
	
}
