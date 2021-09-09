package com.ivanboyukliev.schoolgradingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivanboyukliev.schoolgradingapp.domain.Mark;

public interface MarkRepository extends JpaRepository<Mark, Long> {

}
