package com.self.exercise.search.euler.schedule;

import ch.qos.logback.core.util.TimeUtil;
import com.self.exercise.search.euler.dao.ProblemDao;
import com.self.exercise.search.euler.model.Problem;
import com.self.exercise.search.euler.service.ProblemService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by prime23 on 1/25/17.
 */
@Component
public class RetrieveProblems {

    private static final Logger log = LoggerFactory.getLogger(RetrieveProblems.class);

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    @Qualifier("problemURL")
    private String problemUrl;

    @Autowired
    @Qualifier("problemCssBody")
    private String probleCssBody;

    @Autowired
    private ProblemService problemService;

    private Problem getProblemWithNumber(int n) {
        Document doc;
        try {
            doc = Jsoup.connect(problemUrl + n).get();
            String problemBody = doc.select(probleCssBody).text();
            log.info("problem body: {}", problemBody);
            if (problemBody != null && !problemBody.trim().isEmpty()) {
                String title = doc.select("h2").text();
                return new Problem(n, title, problemBody);
            } else {
                return new Problem(0, "", "");
            }
        } catch (IOException e) {
            log.warn("Unable to retrieve Project Euler problem {} due to possible connection errors.", n);
            return new Problem(0, "", "");
        }
    }

    @Scheduled(fixedDelay = 300000)
    public void restrieveAndStore() {
        // 3600000
        log.info("Starting to retrieve problems");
        // start with the problem we haven't retrieve, it will be one greater than the last problem
        int problemNumber = problemDao.lastProblemNumber() + 1;
        Problem problem = getProblemWithNumber(problemNumber);
        while (problem.getId() != 0) {
            log.info("Problem {} exists", problemNumber);
            problemService.save(problem);
            problem = getProblemWithNumber(problemNumber);
            ++problemNumber;
        }
        log.info("Finished retrieving problems");
    }
}
