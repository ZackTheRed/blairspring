package com.blair.blairspring.model.rowmappers;

import com.blair.blairspring.model.ibatisschema.Job;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JobMapper implements RowMapper<Job> {

    @Override
    public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
        Job job = new Job();
        job.setId(rs.getLong("id"));
        job.setDescription(rs.getString("dscr"));
        return job;
    }
}