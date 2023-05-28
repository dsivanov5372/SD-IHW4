package com.example.usermicroservice.repository;

import com.example.usermicroservice.model.Session;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findBySessionToken(String token);
}
