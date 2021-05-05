package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.UserInbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInboxRepository extends JpaRepository<UserInbox, Long> {
}
