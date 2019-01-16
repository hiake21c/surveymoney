package com.surveymoney.repository;

import com.surveymoney.model.Tests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestsRepositoryTests {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TestRepository testRepository;

    @Test
    public void testJPAInsert(){

        Tests test = new Tests();
        test.setId(1L);
        test.setName("park min jung");
        test.setDescription("JPA TEST");

        testRepository.save(test);

        assertNull("NULL Exception !!",testRepository.getOne(1L));
        assertNotNull("NOT NULL Exception !!",testRepository.getOne(1L));

        System.out.println(testRepository.getOne(1L));
    }

    @Test
    public void testJPAselect(){



    }
}
