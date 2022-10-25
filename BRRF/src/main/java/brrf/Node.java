package brrf;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Node {
    private final String name;
    private final Logger logger = Logger.getLogger(Node.class.getName());
    private int id;

    public Node(String nameParam) {
        name = nameParam;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // ------------------ Method that may be used to print out all records in table -------------
    public List<Map<String, Object>> allTrainsOnStation(String station) {
        DataSource dataSource = new DatabaseConnector().postgresDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "SELECT * FROM " + station + " ORDER BY COALESCE(arr_time, dept_time)";

        List<Map<String, Object>> trains = jdbcTemplate.queryForList(sql);

        if (!trains.isEmpty()) {
            for (Map<String, Object> train : trains) {
                String output = train.toString();
                logger.log(Level.FINE, output);
            }
        } else {
            String message = "I see no trains on this station. Most likely I don't have any data on this station :(";
            logger.log(Level.SEVERE, message);
        }
        return trains;
    }
}
