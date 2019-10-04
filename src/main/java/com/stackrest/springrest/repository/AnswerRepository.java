package com.stackrest.springrest.repository;


import com.stackrest.springrest.model.Answers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answers, Integer>{

    @Query(value ="SELECT * FROM Answers a WHERE a.q_id = ?1", nativeQuery = true)
    Iterable<Answers> findByQuestionId(int q_id);

    @Query(value ="SELECT * FROM Answers a WHERE a.u_id = ?1", nativeQuery = true)
    Iterable<Answers> findByUserId(int u_id);

}
