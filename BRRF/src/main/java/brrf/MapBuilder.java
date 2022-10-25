package brrf;


import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapBuilder {
    private final List<String> graph = new ArrayList<>();

    public MapBuilder() {
        // ------------ Main DB connection --------------
        DataSource dataSource = new DatabaseConnector().postgresDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // ------------ List of all tables and names--------------
        List<Map<String, Object>> graphList = jdbcTemplate.queryForList("SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname != 'pg_catalog' " +
                "AND schemaname != 'information_schema'");

        // ------------ Main part -------------
        for (Map<String, Object> stringObjectMap : graphList) {
            /*
             [{tablename=gliwice}, {tablename=katowice},
            */
            graph.add(stringObjectMap.get("tablename").toString());
        }
    }

    public List<String> getMap() {
        return graph;
    }
}
