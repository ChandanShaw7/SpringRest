package com.stackrest.springrest.repository;


import com.stackrest.springrest.model.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {

    @Query(value = "SELECT * FROM question q where q.u_id=?1",nativeQuery=true)
    Iterable<Question> findByUserId(int u_id);
}
