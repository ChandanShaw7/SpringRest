package com.stackrest.springrest.services;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackrest.springrest.exception.ResourceNotFoundException;
import com.stackrest.springrest.model.Question;
import com.stackrest.springrest.model.User;
import com.stackrest.springrest.repository.QuestionRepository;
import com.stackrest.springrest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

@Service
public class QuestionsServices {

    private static Logger log = LoggerFactory.getLogger(QuestionsServices.class);

    @Autowired
    private QuestionRepository questionsRepository;
    @Autowired
    private UserRepository userRepository;

//
//    @Async("taskExecutor")
//    public CompletableFuture<Iterable<Question>> getAllQuestion(int u_id) throws InterruptedException{
//        log.info("questions by user id start");
//        Iterable<Question> allQuestions = null;
//        try{
//            allQuestions = questionsRepository.findByUserId(u_id);
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
//        Thread.sleep(1000L);
//        return CompletableFuture.completedFuture(allQuestions);
//    }

    @Async("taskExecutor")
    public CompletableFuture<Iterable<Question>> getAllQuestionAsync(int u_id) throws InterruptedException {
        log.info("questions by user id start in Async");
        return CompletableFuture.completedFuture(getQuestions(u_id));
    }

    public CompletableFuture<Iterable<Question>> getAllQuestion(int u_id){
        log.info("questions by user id start in sync");
        return CompletableFuture.completedFuture(getQuestions(u_id));
    }

    private Iterable<Question> getQuestions(int u_id){
        Iterable<Question> allQuestions = null;
        try {
            allQuestions = questionsRepository.findByUserId(u_id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return allQuestions;
    }


    public Optional<Question> getQuestion(int q_id){
        return questionsRepository.findById(q_id);
    }


    public ResponseEntity<?> insertQuestion(int u_id, Question question){

        System.out.println("Enter insert question");

        Optional<User> userDetails = userRepository.findById(u_id);
            if (userDetails.isPresent()) {
                User user = userDetails.get();
                question.setUser(user);
                questionsRepository.save(question);
                return ResponseEntity.ok().build();
             }
        throw new ResourceNotFoundException("User not found with user id: ", String.valueOf(u_id));

    }

    @Async
    public void saveAllData(MultipartFile file) throws Exception{
        log.info("saving file data:"+ file);
        List<Question> questions = null;
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Question>> typeReference = new TypeReference<List<Question>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream(file.getName());
//        log.info(String.valueOf(inputStream));
        try {
            questions = mapper.readValue(inputStream,typeReference);
            questionsRepository.saveAll(questions);
            System.out.println("Questions Saved!");
        } catch (IOException e){
            System.out.println("Unable to save questions: " + e.getMessage());
        }
    }


    public ResponseEntity<?> updateQuestion(Question question,int q_id, int u_id){

        if (!userRepository.existsById(u_id)) {
            throw new ResourceNotFoundException("user not found with user Id: ", String.valueOf(u_id));
        }
        Optional<Question> questionDetails = questionsRepository.findById(q_id);
            if (questionDetails.isPresent()){
                questionDetails.get().setDescription(question.getDescription());
                Question quest = questionsRepository.save(questionDetails.get());
                return ResponseEntity.ok().build();
            }
            throw new ResourceNotFoundException("Question not found with question",String.valueOf(q_id));
    }


    public ResponseEntity<?> deleteQuestion(int q_Id){

//        if (!userRepository.existsById(u_id)){
//            throw new ResourceNotFoundException("user not found with user Id: ",String String.valueOf(u_id));

        if(!questionsRepository.existsById(q_Id))
            throw new ResourceNotFoundException("Question not found with question",String.valueOf(q_Id));

        questionsRepository.deleteById(q_Id);
        return ResponseEntity.ok().build();
    }
}