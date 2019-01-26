package com.surveymoney.tests.repository;

import com.surveymoney.tests.model.Tests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Tests, Long> {
}
