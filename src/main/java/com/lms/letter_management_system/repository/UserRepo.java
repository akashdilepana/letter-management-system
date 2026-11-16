package com.lms.letter_management_system.repository;

import com.lms.letter_management_system.dto.SlimSelectDTO;
import com.lms.letter_management_system.model.User;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    @Query("SELECT `id` FROM `users` WHERE `username`=:username")
    Integer getUserDetailsByUsername(@Param("username") String username);
    
    @Query("SELECT `full_name` FROM `users` WHERE `id`=:id")
    String findEmpById(@Param("id") Integer id);

    @Query("select `id` as `value`,`full_name` as `text` from `users` where `full_name` like :search order by `full_name` limit 20")
    Iterable<SlimSelectDTO> getIdAndName(@Param("search") String search);

    Optional<User> findByUsername(String username);

}
