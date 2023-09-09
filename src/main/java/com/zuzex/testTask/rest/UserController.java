package com.zuzex.testTask.rest;


import com.zuzex.testTask.models.User;
import com.zuzex.testTask.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {this.userRepository = userRepository;}
    @GetMapping()
    public ResponseEntity<List<User>> getUserList(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<User> getOneUser(@PathVariable long user_id){
        Optional<User> house = userRepository.findById(user_id);
        return house.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/{user_id}")
    public ResponseEntity<User> updateUser(@PathVariable long user_id, @RequestBody User user){
        Optional<User> userOld = userRepository.findById(user_id);
        if (userOld.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User userPatched = userOld.get();
        if (user.getLogin() != null){userPatched.setLogin(user.getLogin());}
        if (user.getPassword() != null){userPatched.setPassword(user.getPassword());}
        if (user.getFirstName() != null){userPatched.setFirstName(user.getFirstName());}
        if (user.getLastName() != null){userPatched.setLastName(user.getLastName());}
        if (user.getPatronymic() != null){userPatched.setPatronymic(user.getPatronymic());}
        if (user.getAge() != null){userPatched.setAge(user.getAge());}
        return new ResponseEntity<>(userPatched, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Void> createUser(@RequestBody User user){
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable long user_id){
        userRepository.deleteById(user_id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
