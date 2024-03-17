package com.darmokhval.CarManagementService.repository;

import com.darmokhval.CarManagementService.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
