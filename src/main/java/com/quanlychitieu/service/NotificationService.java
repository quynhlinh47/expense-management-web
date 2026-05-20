package com.quanlychitieu.service;

import com.quanlychitieu.model.Notification;

import com.quanlychitieu.model.User;

import com.quanlychitieu.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getLatest(User user){

        return notificationRepository
                .findTop5ByUserOrderByCreatedAtDesc(user);
    }

    public long countUnread(User user){

        return notificationRepository
                .countByUserAndIsReadFalse(user);
    }

    public void save(Notification notification){

        notificationRepository.save(notification);
    }
}