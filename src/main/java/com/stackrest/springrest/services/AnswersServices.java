package com.stackrest.springrest.services;


import com.stackrest.springrest.exception.ResourceNotFoundException;
import com.stackrest.springrest.model.Answers;
import com.stackrest.springrest.model.Question;
import com.stackrest.springrest.model.User;
import com.stackrest.springrest.repository.AnswerRepository;
import com.stackrest.springrest.repository.QuestionRepository;
import com.stackrest.springrest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AnswersServices {

    private static Logger log = LoggerFactory.getLogger(AnswersServices.class);

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Async("taskExecutor")
    public CompletableFuture<Iterable<Answers>> getAnswersByQuestionId(int q_id) throws InterruptedException{
        log.info("Answer by questions id start");
        Iterable<Answers> answers= null;
        try{
            answers = answerRepository.findByQuestionId(q_id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(answers);
    }

    @Async
    public CompletableFuture<Iterable<Answers>> getAnswersByUserId(int u_id) throws InterruptedException{
        log.info("Answer by user id start");
        Iterable<Answers> answers= null;
        try{
            answers = answerRepository.findByUserId(u_id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(answers);
    }

    public Optional<Answers> getAnswerById(int id){

            return answerRepository.findById(id);

    }

    public ResponseEntity<?> insertAnswer(int u_id, int q_id, Answers answer){
        if (!userRepository.existsById(u_id)) {
            throw new ResourceNotFoundException("user not found with user Id: ", String.valueOf(u_id));
        }
        Optional<User> userdetails = userRepository.findById(u_id);
        Optional<Question> question = questionRepository.findById(q_id);
        if (question.isPresent()){
            answer.setUser(userdetails.get());
            answer.setQuestion(question.get());
            answerRepository.save(answer);
            return ResponseEntity.ok().build();
        }
        throw new ResourceNotFoundException("question not found with user Id: ", String.valueOf(q_id));
    }

    public ResponseEntity<?> updateAnswer(int u_id, int a_id, Answers answer){
        if (!userRepository.existsById(u_id)) {
            throw new ResourceNotFoundException("user not found with user Id: ", String.valueOf(u_id));
        }
        Optional<Answers> answerDetails = answerRepository.findById(a_id);
        if (answerDetails.isPresent()){
            Answers updatedAnswer = answerDetails.get();
            updatedAnswer.setContent(answer.getContent());
            answerRepository.save(updatedAnswer);
            return ResponseEntity.ok().build();
        }
        throw new ResourceNotFoundException("Answer not found with user Id: ", String.valueOf(a_id));
    }

    public ResponseEntity<?> deleteAnswers(int a_id){
        if (!answerRepository.existsById(a_id)){
            throw new ResourceNotFoundException("Answer not found with user Id: ", String.valueOf(a_id));
        }
        answerRepository.deleteById(a_id);
        return ResponseEntity.ok().build();
    }

}
