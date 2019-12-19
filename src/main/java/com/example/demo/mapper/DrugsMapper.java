package com.example.demo.mapper;

import com.example.demo.entity.Drugs;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugsMapper {
    public void save(Drugs drug);

    @Select({"select * from drugs where id=#{id}"})
    public Drugs getDrugs(Long id);
}
