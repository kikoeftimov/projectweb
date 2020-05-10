package com.example.wpproject.project.repository_persistence;

import com.example.wpproject.project.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
      List<Course> findAllByName(String name);

      @Query("select c from Course c where c.name like :term or c.description like :term or c.author.name like :term or c.author.surname like :term or c.category.name like :term or c.category.description like :term")
      List<Course> searchCourses(@Param("term")String term);

}
