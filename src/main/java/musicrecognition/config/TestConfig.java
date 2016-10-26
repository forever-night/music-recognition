package musicrecognition.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.MailSender;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@ComponentScan("musicrecognition")
@Import({JdbcConfig.class})
@EnableTransactionManagement
public class TestConfig {
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/music_recognition");
        dataSource.setUsername("user");
        dataSource.setPassword("user");
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }
    
    @Bean
    MailSender mailSender() {
        return null;
    }
}
