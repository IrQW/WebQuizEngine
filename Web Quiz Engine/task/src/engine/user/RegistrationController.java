package engine.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RegistrationController {
    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder getEncoder;

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody MyUser user) {
        if (userRepo.findUserByUsername(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Has Already");
        }

        user.setPassword(getEncoder.encode(user.getPassword()));

        userRepo.save(user);
    }

    @PostMapping("/actuator/shutdown")
    public void shutdown() {

    }



}
