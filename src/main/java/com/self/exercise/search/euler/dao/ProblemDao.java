package com.self.exercise.search.euler.dao;

import com.self.exercise.search.euler.model.Problem;

import java.util.List;

/**
 * Created by daniel on 1/12/17.
 */
public interface ProblemDao {

    /**
     *
     * @return total number of problems
     */
    long numberOfProblems();

    /**
     *
     * @param problem save the problem
     */
    void save(Problem problem);

    /**
     * get all problems, order by id
     *
     * @return List of all problems - empty list if nothing is found
     */
    List<Problem> getAll();


    /**
     *
     *
     * @param from - the number of problems to skip
     * @param size - the maximum of problems to return
     * @return List of problems should be empty list if skip is greater than the number of problems
     */
    List<Problem> getAll(int from, int size);


    /**
     * get a list of problems based from the query, will be sorted by score how well
     * problem matches query. For example in ElasticSearch it will be sorted by score
     *
     * @param query
     * @return
     */
    List<Problem> getProblemsByQuery(String query);

    /**
     * get a list of problems based from the query, will be sorted by score how well
     * problem matches query. For example in ElasticSearch it will be sorted by score
     *
     *
     * @param query - the string query
     * @param from - number of problems to skip
     * @param size - max number of problems to return
     * @return
     */
    List<Problem> getProblemsByQuery(String query, int from, int size);

    int lastProblemNumber();
}
