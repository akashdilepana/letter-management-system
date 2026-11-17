package com.lms.letter_management_system.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lms.letter_management_system.dto.SlimSelectDTO;
import com.lms.letter_management_system.model.DivisionLvlDes;
import com.lms.letter_management_system.repository.DivisionLvlDesRepo;

public interface DivisionLvlDesRepo extends CrudRepository <DivisionLvlDes, Integer>{

    @Query("select `id` as `value`,`name` as `text` from `division_level_designation` where `name` like :search order by `name` limit 20")
    Iterable<SlimSelectDTO> getIdAndName(@Param("search") String search);
    
}
