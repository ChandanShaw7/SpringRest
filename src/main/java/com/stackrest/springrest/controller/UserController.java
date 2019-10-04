package com.stackrest.springrest.controller;


import com.stackrest.springrest.model.User;
import com.stackrest.springrest.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserServices userServices;
		
	@RequestMapping(produces = "application/json")
    public ResponseEntity<Iterable<User>> allUsers() {
        return new ResponseEntity<Iterable<User>>(userServices.getAllUser(), HttpStatus.OK);
    }

	@RequestMapping(value = "/{city}/{zip}",produces = "application/json")
	public ResponseEntity<Iterable<User>> userDetails(@PathVariable String city,@PathVariable int zip){
		return new ResponseEntity<Iterable<User>>(userServices.getUserDetails(city, zip), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST,consumes = "application/json")
	public String userRegistration(@Valid @RequestBody User userDetails) {
		return userServices.insertUser(userDetails);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST,consumes = "application/json")
	public String userLogin(@Valid @RequestBody User loginDetails) {
		return userServices.loginUser(loginDetails);
	}
	
	@RequestMapping(value = "/{username}",method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String username) {
		userServices.deleteUser(username);
	}

}
