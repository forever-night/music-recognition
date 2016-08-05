package musicrecognition.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({DataConfig.class, JdbcConfig.class, WebConfig.class})
@ComponentScan("musicrecognition")
public class AppConfig {
    
}
