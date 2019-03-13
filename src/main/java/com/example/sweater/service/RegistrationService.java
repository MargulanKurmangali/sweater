package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import com.example.sweater.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class RegistrationService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(Objects.nonNull(user.getPassword()) && !Objects.equals(user.getPassword(),user.getPassword2())) {
            model.addAttribute("passwordError", "Passwords are different");
        }
        if(bindingResult.hasErrors()) {
          Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
          model.mergeAttributes(errors);

          return "registration";
        }
        if (userFromDb != null) {
            model.addAttribute("usernameError", "User exists");
            return "registration";
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sendActivationMessage(user);

        userRepo.save(user);
        return "redirect:/login";
    }

    public void sendActivationMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" + "Welcome to Sweater. \n" +
                            "Follow this link this link to activate your account: http://localhost:8080/activate/%s",
                    user.getEmail(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation code for Sweater", message);
        }
    }
}
