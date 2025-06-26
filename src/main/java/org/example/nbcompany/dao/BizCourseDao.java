package org.example.nbcompany.dao;

import org.apache.ibatis.annotations.Param;
import org.example.nbcompany.dto.CourseDto.CourseQueryDTO;
import org.example.nbcompany.entity.BizCourse;

import java.util.List;

public interface BizCourseDao {

    BizCourse findById(@Param("id") Long id);

    BizCourse findByCourseName(@Param("courseName") String CourseName);

    List<BizCourse> findAll();
    List<BizCourse> findByQuery(@Param("query") CourseQueryDTO query);
    int insert(BizCourse bizCourse);

    int update(BizCourse bizCourse);

    int deleteById(@Param("id") Long id);

    int incrementViewCount(@Param("id") Long id);
}
