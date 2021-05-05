package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
