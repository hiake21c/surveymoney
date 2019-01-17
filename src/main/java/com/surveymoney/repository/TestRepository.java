package com.surveymoney.repository;

import com.surveymoney.model.Tests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Tests, Long> {
}
