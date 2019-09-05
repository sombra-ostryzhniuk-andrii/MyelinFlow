package com.ifc.populationorderdeterminant.repository;

import com.ifc.populationorderdeterminant.entity.View;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import static com.ifc.populationorderdeterminant.providers.JdbcProvider.getJdbcTemplate;

public class ViewDAO {

    public static View getViewByName(String viewName, String schema) {
        return getJdbcTemplate()
                .queryForObject("select viewname as name, " +
                           "       definition as definition, " +
                           "       schemaname as schema " +
                           "from pg_catalog.pg_views " +
                           "where viewname = ? " +
                           "  and schemaname = ?",
                        BeanPropertyRowMapper.newInstance(View.class),
                        viewName, schema);
    }

}
