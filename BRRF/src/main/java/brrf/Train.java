package brrf;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static brrf.Utilities.nameRegexBuilder;


/**
 *
 * Class responsible for creating trains
 * @author Krzysztof Żerebecki
 */
public class Train implements Transportation{
    private final String name;
    private final String carrier;
    private final String regex;
    private final ArrayList<String> stations = new ArrayList<>();
    private static final String SQLCONSTRAINT = " WHERE name";
    private static final String TABLENAME = "tablename";
    private static final String DEPTTIME = "dept_time";

    public Train(String nameParam, String carrierParam) {
        name = nameParam;
        carrier = carrierParam;
        regex = nameRegexBuilder(nameParam, carrierParam);
    }

    @Override
    public String getArrTime(String stationParam) {
        return "SELECT arr_time FROM " + stationParam + SQLCONSTRAINT + nameRegexBuilder(name, carrier);
    }

    @Override
    public String getDeptTime(String stationParam) {
        return "SELECT dept_time FROM " + stationParam + SQLCONSTRAINT + nameRegexBuilder(name, carrier);
    }

    public String getRegex() {
        return regex;
    }

    public String getName() {
        return name;
    }

    public String getCarrierName() {
        return carrier;
    }

    @Override
    public String getDistanceFromStart(String stationParam) {
        return "SELECT kilometers FROM " + stationParam + SQLCONSTRAINT + nameRegexBuilder(name, carrier);
    }

    public List<String> getStations() {
        return stations;
    }

    public void readStations() {
        // ------------ Main DB connection --------------
        DataSource dataSource = new DatabaseConnector().postgresDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> allStations = jdbcTemplate.queryForList("SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname != 'pg_catalog' " +
                "AND schemaname != 'information_schema'");

        // ------------ Sort by dept_time ---------------
        Map<String, String> temporaryStations = new TreeMap<>();

        for (Map<String, Object> names : allStations) {
            String query = "SELECT name, arr_time, dept_time FROM " + names.get(TABLENAME).toString() +
                    SQLCONSTRAINT + nameRegexBuilder(name, carrier);
            List<Map<String, Object>> askNode = jdbcTemplate.queryForList(query);

            if (!askNode.isEmpty()) {
                if (askNode.get(0).get(DEPTTIME) == null) {
                    temporaryStations.put(askNode.get(0).get("arr_time").toString(), names.get(TABLENAME).toString());
                } else {
                    temporaryStations.put(askNode.get(0).get(DEPTTIME).toString(), names.get(TABLENAME).toString());
                }
            }
        }

        // ---------- Return sorted stations -----------
        for (String element : temporaryStations.keySet()) {
            stations.add(temporaryStations.get(element));
        }
    }

    public static void main(String[] args) {
        Train test = new Train("PILECKI (1423)", "IC");
        String query = test.getDeptTime("katowice");
        DataSource dataSource = new DatabaseConnector().postgresDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> askNode = jdbcTemplate.queryForList(query);
        System.out.println(askNode.get(0).get(DEPTTIME));
    }
}