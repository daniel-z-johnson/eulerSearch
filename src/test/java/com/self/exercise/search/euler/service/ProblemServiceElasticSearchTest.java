package com.self.exercise.search.euler.service;

import com.google.common.io.Resources;
import com.self.exercise.search.euler.dao.ProblemDaoElasticSearchTest;
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
 * Created by prime23 on 1/23/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProblemServiceElasticSearchTest {

    private static final Logger log = LoggerFactory.getLogger(ProblemDaoElasticSearchTest.class);

    @Autowired
    private ProblemService problemService;

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
        problemService.save(PROBLEM_3);
        problemService.save(PROBLEM_1);
        problemService.save(PROBLEM_2);
        es.admin().indices().prepareRefresh(index).execute().actionGet();
    }

    @After
    public void tearDown() throws Exception {
        log.info("Deleting test index");
        es.admin().indices().prepareDelete(index).execute().actionGet();
    }

    @Test
    public void save() throws Exception {
        List<Problem> problemsBefore = problemService.getAll();
        problemService.save(PROBLEM_4);
        es.admin().indices().prepareRefresh(index).execute().actionGet();
        List<Problem> problemsAfter = problemService.getAll();
        assertNotEquals("Problems returned should have changed after saving a new problem",
                problemsBefore, problemsAfter);
    }

    @Test
    public void getAll() throws Exception {
        List<Problem> problems = problemService.getAll();
        assertThat(problems, contains(PROBLEM_1, PROBLEM_2, PROBLEM_3));
    }

    @Test
    public void getAllWithFromAndSkip() throws Exception {
        List<Problem> problems = problemService.getAll(1, 2);
        assertThat(problems, contains(PROBLEM_2, PROBLEM_3));

    }

    @Test
    public void getProblemsByQuery() throws Exception {
        List<Problem> problems = problemService.getProblemsByQuery("prime");
        assertThat(problems, not(empty()));
    }

    @Test
    public void getProblemsBuQueryNoResults() throws Exception {
        List<Problem> problems = problemService.getProblemsByQuery("zzyzx");
        assertThat(problems, empty());
    }

}