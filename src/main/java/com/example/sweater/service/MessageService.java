package com.example.sweater.service;

import com.example.sweater.domain.Message;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
public class MessageService {

  @Autowired
  MessageRepo messageRepo;

  public Iterable<Message> getMessage(String filter) {
    Iterable<Message> messages;

    if (filter != null && !filter.isEmpty()) {
      messages = messageRepo.findByTag(filter);
    } else {
      messages = messageRepo.findAll();
    }

    return messages;
  }

  public void deleteMessage(String filter) {
    if (filter != null && !filter.isEmpty()) {
      messageRepo.deleteByTag(filter);
    }
  }

  public void addMessage(Message message, MultipartFile file, String uploadPath) throws IOException {
    if (file != null && !file.getOriginalFilename().isEmpty()) {
      File uploadDir = new File(uploadPath);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      String uuidFile = UUID.randomUUID().toString();
      String resultFilename = uuidFile + "." + file.getOriginalFilename();
      file.transferTo(new File(uploadPath + "/" + resultFilename));
      message.setFilename(resultFilename);
    }

    messageRepo.save(message);
  }
}
