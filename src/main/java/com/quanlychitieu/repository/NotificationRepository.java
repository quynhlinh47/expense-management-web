package com.quanlychitieu.repository;

import com.quanlychitieu.model.Notification;

import com.quanlychitieu.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification>
    findTop5ByUserOrderByCreatedAtDesc(User user);

    long countByUserAndIsReadFalse(User user);
}