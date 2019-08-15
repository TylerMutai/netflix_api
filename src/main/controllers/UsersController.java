package main.controllers;


import main.models.Users;
import main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api",produces = "application/json;charset=UTF-8") //All our api request URLs will start with /api and return Json
public class UsersController {

    private UserRepository userRepository;

    @Autowired
    public UsersController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @GetMapping(path = "/addUser")
        public String addUser(@RequestParam(name = "id")Long id, @RequestParam(name="name") String name) {
        Users users = new Users();
        users.setId(id);
        users.setName(name);

        users = userRepository.save(users);
        return users.toString();

    }


}
