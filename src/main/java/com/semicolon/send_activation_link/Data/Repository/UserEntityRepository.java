package com.semicolon.send_activation_link.Data.Repository;

import com.semicolon.send_activation_link.Data.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity,String> {
    UserEntity findByEmailIdIgnoreCase(String emailId);
}
