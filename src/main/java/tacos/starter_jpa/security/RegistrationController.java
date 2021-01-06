package tacos.starter_jpa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.starter_jpa.data.UserRepo;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepo userRepo;

    private final PasswordEncoder encoder;

    @Autowired
    RegistrationController(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processForm(RegisterForm form) {

        userRepo.save(form.toUser(encoder));

        return "redirect:/login";
    }

}
