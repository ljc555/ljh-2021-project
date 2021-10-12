package com.ljc555.entity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljc555.SearchHouseNetApplicationTests;
import com.ljc555.repository.UserRepository;

/**
 * Created by 瓦力.
 */
public class UserRepositoryTest extends SearchHouseNetApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindOne() {
        User user = userRepository.findOne(1L);
        Assert.assertEquals("wali", user.getName());
    }
}
