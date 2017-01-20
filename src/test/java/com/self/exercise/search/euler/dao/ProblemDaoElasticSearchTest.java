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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Created by daniel on 1/17/17.
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

    @Value("${es.index}")
    private String index;

    @Value("${es.type}")
    private String type;

    @Before
    public void setUp() throws Exception {
        log.info("Creating test index for testing");
        URL url = Resources.getResource("elasticsearchSetUp/euler.json");
        String mapping = Resources.toString(url, StandardCharsets.UTF_8);
        log.debug("{}", mapping);
        es.admin().indices().prepareCreate(index).setSource(mapping).execute().actionGet();
    }

    @After
    public void tearDown() throws Exception {
        log.info("Deleting test index");
        es.admin().indices().prepareDelete(index).execute().actionGet();
    }

    @Test
    public void save() throws Exception {
        long totalProblemsBefore = problemDao.numberOfProblems();
        problemDao.save(new Problem(1, "One Title", "One body"));
        es.admin().indices().prepareRefresh(index).execute().actionGet();
        long totalProblemsAfter = problemDao.numberOfProblems();
        assertNotEquals("After saving a problem count should change", totalProblemsBefore, totalProblemsAfter);
    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void getAll1() throws Exception {

    }

    @Test
    public void getProblemsByQuery() throws Exception {

    }

    @Test
    public void getProblemsByQuery1() throws Exception {

    }

}