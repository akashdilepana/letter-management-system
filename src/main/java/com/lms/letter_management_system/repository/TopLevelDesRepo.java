package com.lms.letter_management_system.repository;

import com.lms.letter_management_system.dto.SlimSelectDTO;
import com.lms.letter_management_system.model.TopLevelDes;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository 
public interface TopLevelDesRepo extends CrudRepository <TopLevelDes, Integer>{

    @Query("select `id` as `value`,`name` as `text` from `top_level_designation` where `name` like :search order by `name` limit 20")
    Iterable<SlimSelectDTO> getIdAndName(@Param("search") String search);
    
}
