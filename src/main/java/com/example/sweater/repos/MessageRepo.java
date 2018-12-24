package com.example.sweater.repos;

import com.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

import javax.jdo.annotations.Transactional;
import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {

    void deleteByTag(String tag);
    List<Message> findByTag(String tag);
}
