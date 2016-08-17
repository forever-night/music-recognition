package musicrecognition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Configuration
@Import({DataConfig.class, JdbcConfig.class, WebConfig.class})
@ComponentScan("musicrecognition")
public class AppConfig {
    @Bean
    CommonsMultipartResolver filterMultipartResolver() {
        return new CommonsMultipartResolver();
    }
}
