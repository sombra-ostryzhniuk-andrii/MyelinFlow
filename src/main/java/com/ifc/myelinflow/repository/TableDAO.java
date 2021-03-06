package com.ifc.myelinflow.repository;

import static com.ifc.myelinflow.providers.JdbcProvider.getJdbcTemplate;

public class TableDAO {

    public static Boolean isTableExist(String tableName, String schema) {
        return getJdbcTemplate()
                .queryForObject("select exists( " +
                                "       select 1 " +
                                "       from pg_catalog.pg_tables " +
                                "       where tablename = ? " +
                                "         and schemaname = ?)",
                        Boolean.class,
                        tableName, schema);
    }

}
