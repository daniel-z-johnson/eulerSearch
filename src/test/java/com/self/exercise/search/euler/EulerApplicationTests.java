package com.self.exercise.search.euler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EulerApplicationTests {

    private static Logger log = LoggerFactory.getLogger(EulerApplicationTests.class);

    @Value("${spring.profiles}")
    private String profile;

	@Test
	public void contextLoads() {
	}

    /**
     * Trivial sanity test to make sure the right profile is loaded
     */
	@Test
    public void profileIsTest() {
        Assert.assertEquals("Profile should be test for testing but was " + profile, "test", profile);
    }

}
