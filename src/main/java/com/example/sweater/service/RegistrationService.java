package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
public class RegistrationService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    MailSenderService mailSenderService;

    public String addNewUser(User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists");
            return "registration";
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

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
