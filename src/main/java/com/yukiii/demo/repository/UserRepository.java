package com.yukiii.demo.repository;

import com.yukiii.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  @Query(value = "select distinct(u.*)\n" +
    "from users u \n" +
    "join users_roles ur \n" +
    "on u.id  = ur.user_id  \n" +
    "where ur.role_id in (:roleIds) \n" +
    "order by u.id asc",
    nativeQuery = true
  )
  List<User> findUserByRoleIn(@Param("roleIds") List<Long> roleIds);
}
