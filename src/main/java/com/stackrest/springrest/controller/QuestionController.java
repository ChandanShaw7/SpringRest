package com.stackrest.springrest.controller;


//import com.example.demo.AsyncConfigurations;


import com.stackrest.springrest.model.Answers;
import com.stackrest.springrest.model.Question;
import com.stackrest.springrest.services.AnswersServices;
import com.stackrest.springrest.services.QuestionsServices;
import com.stackrest.springrest.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Controller
@RequestMapping("/{user_id}/questions")
public class QuestionController {

    private static Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionsServices questionsServices;
    @Autowired
    private AnswersServices answersServices;
    @Autowired
    private UserServices userServices;

//    @RequestMapping(produces = "application/json")
//    public ResponseEntity<Iterable<Question>> getQuestions(@PathVariable int user_id){
//        return new ResponseEntity<Iterable<Question>>(questionsServices.getAllQuestion(user_id), HttpStatus.OK);
//    }

    @RequestMapping(value = "/all/{status}",produces = "application/json")
    public @ResponseBody CompletableFuture<ResponseEntity<Iterable<Question>>> getQuestions(@PathVariable int user_id, @PathVariable String status) throws InterruptedException{
        log.info("Inside of users complete questions");
        if (status.equals("async")){
            return questionsServices.getAllQuestionAsync(user_id).thenApply(ResponseEntity::ok);
        }
//        return new ResponseEntity<Iterable<Question>>(questionsServices.getAllQuestion(user_id), HttpStatus.OK);
        return questionsServices.getAllQuestion(user_id).thenApply(ResponseEntity::ok);

    }

    @RequestMapping(value = "/{question_id}",produces = "application/json")
    public ResponseEntity<Optional<Question>> getOneQuestion(@PathVariable(value = "question_id") int question_id){
//        System.out.println(questionsServices.getQuestion(question_id).get().getDescription());
//        Optional<Question> questi;
//        questi = questionsServices.getQuestion(question_id);
//        System.out.println(questi.get().getDescription());
//        System.out.println(questi.get().getQ_id());
//        System.out.println(questi.get().getTitle());
//        System.out.println(questi.get().getUser().getId());
        return new ResponseEntity<Optional<Question>>(questionsServices.getQuestion(question_id), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity insertOneQuestion(@PathVariable(value = "user_id") int u_id, @Valid @RequestBody Question question){
//        System.out.println(question);
//        System.out.println(u_id);
//        System.out.println(question.getDescription());
        return questionsServices.insertQuestion(u_id,question);
    }

    @RequestMapping(method = RequestMethod.POST, consumes="multipart/form-data", produces = "application/json")
    public ResponseEntity saveQuestionFromFile(@RequestParam(value = "files") MultipartFile[] files){
        try{
            for(MultipartFile file:files){
                log.info(String.valueOf(file));
                questionsServices.saveAllData(file);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(final Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        }

    @RequestMapping(method = RequestMethod.PUT,value = "/{question_id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity updateOneQuestion(@PathVariable(value = "user_id") int user_id, @PathVariable(value = "question_id") int question_id, @RequestBody Question question){
        System.out.println(question);
        return questionsServices.updateQuestion(question, question_id, user_id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{question_id}")
    public ResponseEntity deleteQuestion(@PathVariable(value = "question_id") int question_id){
        return questionsServices.deleteQuestion(question_id);
    }


    @RequestMapping (value = "/{q_id}/match",method = RequestMethod.GET,
            produces="application/json")
    public @ResponseBody CompletableFuture<ResponseEntity> getAllQuestions(@PathVariable(value = "user_id") int user_id, @PathVariable(value = "q_id") int q_id) {
        try {
            System.out.println("Inside loop");
            CompletableFuture<Iterable<Question>> question1=questionsServices.getAllQuestion(user_id);
            CompletableFuture<Iterable<Answers>> question2=answersServices.getAnswersByUserId(user_id);
            CompletableFuture<Iterable<Answers>> question3= answersServices.getAnswersByQuestionId(q_id);
            CompletableFuture.allOf(question1, question2, question3).join();
//            log.info(String.valueOf(question1.get()));
//            log.info(String.valueOf(question2.get()));
//            log.info(String.valueOf(question3.get()));
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).build());

        } catch(final Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }
}