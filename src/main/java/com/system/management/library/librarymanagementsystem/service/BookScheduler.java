package com.system.management.library.librarymanagementsystem.service;

import com.system.management.library.librarymanagementsystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@RequiredArgsConstructor
@Slf4j
public class BookScheduler {

    private final BookRepository bookRepository;

    @Scheduled(fixedRate = 10800000, initialDelay = 1000)
    public void deleteBooks() {
        bookRepository.deleteAll();

        log.info("deleted all Books.");
    }
}
