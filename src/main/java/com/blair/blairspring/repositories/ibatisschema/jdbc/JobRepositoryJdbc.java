package com.blair.blairspring.repositories.ibatisschema.jdbc;

import com.blair.blairspring.model.ibatisschema.Job;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("jdbc")
@Repository
public class JobRepositoryJdbc {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JobRepositoryJdbc(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert.withTableName("jobs").usingGeneratedKeyColumns("id");
    }

    public Job findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT id, dscr AS description FROM jobs WHERE ID = ?",
                new BeanPropertyRowMapper<>(Job.class),
                new Object[]{id});
    }

    /*public List<Job> findAll() {
        return jdbcTemplate.query("SELECT id, dscr AS description FROM jobs", new BeanPropertyRowMapper<>(Job.class));
    }*/

    // RowCallbackHandler
    /*public List<Job> findAll() {
        List<Job> jobs = new ArrayList<>();
        jdbcTemplate.query("SELECT id, dscr AS description FROM jobs", rs -> {
            Job job = new Job();
            job.setId(rs.getLong("id"));
            job.setDescription(rs.getString("dscr"));
            jobs.add(job);
        });
        return jobs;
    }*/

    // ResultSetExtractor
    public List<Job> findAll() {
        return jdbcTemplate.query("SELECT id, dscr AS description FROM jobs", rs -> {
            List<Job> jobs = new ArrayList<>();
            while (rs.next()) {
                Job job = new Job();
                job.setId(rs.getLong("id"));
                job.setDescription(rs.getString("dscr"));
                jobs.add(job);
            }
            return jobs;
        });
    }

    public Job create(Job job) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dscr", job.getDescription());
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        return findById(id.longValue());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM jobs WHERE ID = ?", id);
    }

}
