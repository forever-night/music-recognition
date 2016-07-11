package musicrecognition.config;

import com.googlecode.flyway.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;


@Configuration
public class DataConfig {
    @Bean
    DataSource dataSource() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        dataSourceLookup.setResourceRef(true);

        return dataSourceLookup.getDataSource("java:comp/env/jdbc/music_recognitionDS");
    }

    @Bean
    Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource());

        return flyway;
    }
}
