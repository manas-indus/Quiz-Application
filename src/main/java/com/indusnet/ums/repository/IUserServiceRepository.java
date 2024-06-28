package com.indusnet.ums.repository;
import com.indusnet.ums.dto.UserModelDto;
import com.indusnet.ums.model.UserModel;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface IUserServiceRepository extends MongoRepository<UserModel, String> {

	Optional<UserModel> findByEmail(String email);

	boolean existsByEmail(String email);
}


