package com.ifc.populatefunctionssorter.repository;

import com.ifc.populatefunctionssorter.entity.Function;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

import static com.ifc.populatefunctionssorter.repository.providers.JdbcProvider.getJdbcTemplate;

public class FunctionDAO {

    public static List<Function> getAllPopulateFunctionsInSchema(String schema) {
        return getJdbcTemplate()
                .query("select routine_name as name, " +
                           "       routine_definition as definition, " +
                           "       specific_schema as schema " +
                           "from information_schema.routines " +
                           "where specific_schema = ? " +
                           "  and routine_name like 'populate%'",
                        BeanPropertyRowMapper.newInstance(Function.class),
                        schema);
    }

}
