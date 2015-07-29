package org.gradle.responsitory;

import org.gradle.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
 

@RepositoryRestResource
public interface UserRepository extends MongoRepository<User, String>{

		User findByEmail(@Param("email")String email);
}
