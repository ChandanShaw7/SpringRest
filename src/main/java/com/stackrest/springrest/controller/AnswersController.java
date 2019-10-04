package com.stackrest.springrest.controller;

import com.stackrest.springrest.model.Answers;
import com.stackrest.springrest.services.AnswersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/login/{u_id}")
public class AnswersController {

    @Autowired
    private AnswersServices answersServices;

    @RequestMapping(value = "/{q_id}/answers",produces = "application/json")
    public CompletableFuture<ResponseEntity<Iterable<Answers>>> getAnswers(@PathVariable int  q_id) throws InterruptedException{
//        return new ResponseEntity<Iterable<Answers>> (answersServices.getAnswersByQuestionId(q_id), HttpStatus.OK);
        return answersServices.getAnswersByQuestionId(q_id).thenApply(ResponseEntity::ok);

    }

    @RequestMapping(value = "/answers",produces = "application/json")
    public CompletableFuture<ResponseEntity<Iterable<Answers>>> getAnswersbyUser(@PathVariable int u_id) throws InterruptedException{
//        return new ResponseEntity<Iterable<Answers>> (answersServices.getAnswersByUserId(u_id), HttpStatus.OK);
        return answersServices.getAnswersByUserId(u_id).thenApply(ResponseEntity::ok);

    }

    @RequestMapping(value = "/answers/{a_id}",produces = "application/json")
    public ResponseEntity<Optional<Answers>> getAnswer(@PathVariable int a_id){
        return new ResponseEntity<Optional<Answers>> (answersServices.getAnswerById(a_id), HttpStatus.OK);
    }

    @RequestMapping(value = "/answers/{a_id}",method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity modifyQuestion(@PathVariable int a_id, @PathVariable int u_id, @RequestBody Answers answer){
        return answersServices.updateAnswer(u_id,a_id, answer);
    }

    @RequestMapping(value = "/{q_id}/answers",method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity addAnswer(@PathVariable int u_id, @PathVariable int q_id, @RequestBody Answers answer){
        return answersServices.insertAnswer(u_id, q_id, answer);
    }

    @RequestMapping(value = "/answers/{a_id}",method = RequestMethod.DELETE)
    public ResponseEntity dropAnswer(@PathVariable int a_id){
        return answersServices.deleteAnswers(a_id);
    }

}
