package com.self.exercise.search.euler.dao;

import com.self.exercise.search.euler.model.Problem;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by prime23 on 1/17/17.
 */
@Repository
public class ProblemDaoElasticSearch implements ProblemDao {

    private static final Logger log = LoggerFactory.getLogger(ProblemDaoElasticSearch.class);

    private String index;
    private String type;
    private TransportClient es;

    public ProblemDaoElasticSearch(
            @Autowired @Qualifier("index") String index,
            @Autowired @Qualifier("type") String type,
            @Autowired TransportClient es) {
        this.index = index;
        this.type = type;
        this.es = es;
        log.info("Index: {}, Type: {}", this.index, this.type);
    }


    @Override
    public long numberOfProblems() {
        SearchResponse sr = es.prepareSearch(index)
                .setTypes(type)
                .setQuery(matchAllQuery())
                .setSize(0)
                .get();

        return sr.getHits().totalHits();
    }

    @Override
    public void save(Problem problem) {
        // create something JSON like from Map<String, Object> for the source
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("id", problem.getId());
        body.put("title", problem.getTitle());
        body.put("body", problem.getBody());

        // index the problem
        IndexResponse indexResponse = es.prepareIndex(index, type, String.valueOf(problem.getId()))
                .setSource(body)
                .get();
    }

    @Override
    public List<Problem> getAll() {
        return getAll(0, 1000);
    }

    @Override
    public List<Problem> getAll(int from, int size) {
        SearchResponse sr = es.prepareSearch(index)
                .setTypes(type)
                .setQuery(matchAllQuery())
                .setFrom(from)
                .setSize(size)
                .addSort(SortBuilders.fieldSort("id"))
                .get();
        return searchResponseToProblem(sr);

    }

    @Override
    public List<Problem> getProblemsByQuery(String query) {
        return getProblemsByQuery(query, 0, 100);
    }

    @Override
    public List<Problem> getProblemsByQuery(String query, int from, int size) {
        SearchResponse sr = es.prepareSearch(index)
                .setTypes(type)
                .setFrom(from)
                .setSize(size)
                .setQuery(matchQuery("_all", query))
                .get();
        return searchResponseToProblem(sr);
    }

    private List<Problem> searchResponseToProblem(SearchResponse sr) {
        List<SearchHit> hits = Arrays.asList(sr.getHits().getHits());
        return hits.stream().map(hit -> {
           Problem p = new Problem();
           p.setId(Integer.valueOf(hit.getSource().get("id").toString()));
           p.setTitle(hit.getSource().get("title").toString());
           p.setBody(hit.getSource().get("body").toString());
           return p;
        }).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ProblemDaoElasticSearch{" +
                "index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", es=" + es +
                '}';
    }

    @Override
    public int lastProblemNumber() {
        SearchResponse sr = es.prepareSearch(index)
                .setTypes(type)
                // no reason to have problems returned
                .setSize(0)
                .setQuery(matchAllQuery())
                .addAggregation(AggregationBuilders.max("lastProblem").field("id").missing(0))
                .get();
        log.debug("Aggregation results: {}", sr);
        // TODO find better solution, this isn't good
        return (int) (double) sr.getAggregations().get("lastProblem").getProperty("value");
    }
}
