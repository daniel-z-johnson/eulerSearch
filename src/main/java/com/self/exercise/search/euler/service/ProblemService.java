package com.self.exercise.search.euler.service;

import com.self.exercise.search.euler.model.Problem;

import java.util.List;

/**
 * Created by prime23 on 1/21/17.
 */
public interface ProblemService {
    void save(Problem problem);
    List<Problem> getAll();
    List<Problem> getAll(int from, int size);
    List<Problem> getProblemsByQuery(String query);
    List<Problem> getProblemsByQuery(String query, int from, int size);
}
