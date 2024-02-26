package sh.alex.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class userController {

    private final sh.alex.UserService.userRepository userRepository;

    public userController(sh.alex.UserService.userRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<user> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public user getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public user createUser(@RequestBody user user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public user updateUser(@PathVariable Long id, @RequestBody user user) {
        sh.alex.UserService.user existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}

