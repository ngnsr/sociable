package com.rr.sociable.repo;

import com.rr.sociable.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

    Page<Message> findAll(Pageable pageable);
    Page<Message> findAllByGroupId(Pageable pageable, Long id);

    Optional<Message> findById(Long id);

    Optional<Message> save(Message message);

    void deleteById(Long id);
    void deleteMessageByGroupId(Long id);
}