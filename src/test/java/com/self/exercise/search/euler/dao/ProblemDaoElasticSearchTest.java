package com.self.exercise.search.euler.dao;

import com.google.common.io.Resources;
import com.self.exercise.search.euler.model.Problem;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by prime23 on 1/17/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProblemDaoElasticSearchTest {
    private static final Logger log = LoggerFactory.getLogger(ProblemDaoElasticSearchTest.class);

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private TransportClient es;

    @Autowired
    @Qualifier("index")
    private String index;

    @Autowired
    @Qualifier("type")
    private String type;

    private static final Problem PROBLEM_1 =
            new Problem(1, "Prime Numbers", "Prime Numbers and the Pythagorean theorem");
    private static final Problem PROBLEM_2 =
            new Problem(2, "Euler totient function", "co-primes");
    private static final Problem PROBLEM_3 =
            new Problem(3, "Triangle numbers", "Triangle and square");
    private static final Problem PROBLEM_4 =
            new Problem(4, "One Title", "One body");

    @Before
    public void setUp() throws Exception {
        log.info("Creating test index for testing");
        URL url = Resources.getResource("elasticsearchSetUp/euler.json");
        String mapping = Resources.toString(url, StandardCharsets.UTF_8);
        log.debug("{}", mapping);
        es.admin().indices().prepareCreate(index).setSource(mapping).execute().actionGet();
        problemDao.save(PROBLEM_1);
        problemDao.save(PROBLEM_2);
        problemDao.save(PROBLEM_3);
        es.admin().indices().prepareRefresh(index).execute().actionGet();
    }

    @After
    public void tearDown() throws Exception {
        log.info("Deleting test index");
        es.admin().indices().prepareDelete(index).execute().actionGet();
    }

    @Test
    public void save() throws Exception {
        long totalProblemsBefore = problemDao.numberOfProblems();
        problemDao.save(PROBLEM_4);
        es.admin().indices().prepareRefresh(index).execute().actionGet();
        long totalProblemsAfter = problemDao.numberOfProblems();
        assertNotEquals("After saving a problem count should change", totalProblemsBefore, totalProblemsAfter);
    }

    @Test
    public void getAll() throws Exception {
        int numOfResults = problemDao.getAll().size();
        assertEquals("Three results should have been returned", 3, numOfResults);
    }

    @Test
    public void getAllWithSizeAndFrom() throws Exception {
        List<Problem> results = problemDao.getAll(1,2);
        int numResults = results.size();
        assertEquals("Only two results should have been returned", 2, numResults);
        assertThat(results, hasItems(PROBLEM_2, PROBLEM_3));

    }

    @Test
    public void getProblemsByQuery() throws Exception {

    }

    @Test
    public void getProblemsByQueryWithLimit() throws Exception {

    }

}