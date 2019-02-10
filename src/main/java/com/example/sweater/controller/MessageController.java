package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {

        model.addAttribute("messages", messageService.getMessage(filter));
        model.addAttribute("filter", filter);

        return "main";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String filter) {
        messageService.deleteMessage(filter);
        return "redirect:/main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag,
                      @RequestParam("file") MultipartFile file,
                      Map<String, Object> model) throws IOException {

        Message message = new Message(text, tag, user);

        model.put("messages", messageService.addMessage(message, file, uploadPath));

        return "main";
    }

}
