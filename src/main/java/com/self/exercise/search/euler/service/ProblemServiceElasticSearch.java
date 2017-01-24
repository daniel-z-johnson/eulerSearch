package com.self.exercise.search.euler.service;

import com.self.exercise.search.euler.dao.ProblemDao;
import com.self.exercise.search.euler.model.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by prime23 on 1/23/17.
 */
@Service
public class ProblemServiceElasticSearch implements ProblemService {

    private ProblemDao problemDao;

    public ProblemServiceElasticSearch(
            @Autowired ProblemDao problemDao
            ) {
        this.problemDao = problemDao;
    }

    @Override
    public void save(Problem problem) {
        problemDao.save(problem);
    }

    @Override
    public List<Problem> getAll() {
        return problemDao.getAll();
    }

    @Override
    public List<Problem> getAll(int from, int size) {
        return problemDao.getAll(from, size);
    }

    @Override
    public List<Problem> getProblemsByQuery(String query) {
        return problemDao.getProblemsByQuery(query);
    }

    @Override
    public List<Problem> getProblemsByQuery(String query, int from, int size) {
        return problemDao.getProblemsByQuery(query, from, size);
    }
}
