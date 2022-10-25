package brrf;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DatabaseConnector {
	@Bean
	public DataSource postgresDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/betastations");
		dataSource.setUsername("postgres");
		dataSource.setPassword("0000");

		return dataSource;
	}

	@Bean
	public DataSource stationNamesDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/stationnames");
		dataSource.setUsername("postgres");
		dataSource.setPassword("0000");

		return dataSource;
	}

	public static List<Map<String, Object>> allTrainsOnStationWithDeptTime(String station,String deptTime) {
		DataSource dataSource = new DatabaseConnector().postgresDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);

		String sql = "SELECT * FROM " + station + " WHERE dept_time >= '" + deptTime + "' ORDER BY dept_time";
		System.out.println(sql);

		return jdbcTemplate.queryForList(sql);
	}

	public static List<Map<String, Object>> allTrainsOnStationWithArrTime(String station, String arrTime) {
		DataSource dataSource = new DatabaseConnector().postgresDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);

		String sql = "SELECT * FROM " + station + " WHERE arr_time <= '" + arrTime + "'";
		System.out.println(sql);

		return jdbcTemplate.queryForList(sql);
	}

}
