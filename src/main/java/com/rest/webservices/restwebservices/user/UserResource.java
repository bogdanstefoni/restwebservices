package com.rest.webservices.restwebservices.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDAOService userDAOService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userDAOService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable  int id) {
        User user = userDAOService.getUser(id);

        if(user == null) {
            throw new UserNotFoundException("id: " + id);
        }

        return user;
    }


    //input - details of the user
    //output - CREATED & Return the created URI
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userDAOService.save(user);

        //Created
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUse(@PathVariable  int id) {
        User user = userDAOService.deleteById(id);

        if(user == null) {
            throw new UserNotFoundException("id: " + id);
        }

    }

}
