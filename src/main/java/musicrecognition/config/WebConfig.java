package musicrecognition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Configuration
@EnableWebMvc
@ComponentScan("musicrecognition.controllers")
@Import({WebSecurityConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter{
    @Bean
    InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        viewResolver.setContentType("text/html;charset=UTF-8");
        
        return viewResolver;
    }
    
    @Bean
    StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter();
    }
    
    @Bean
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
    
    @Bean
    HandlerInterceptor currentUserHandlerInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                   Object o, ModelAndView modelAndView) throws Exception {
                if (modelAndView != null) {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    Object user = auth.getPrincipal();
                    String username = null;
                    
                    if (user instanceof User)
                        username = ((User) user).getUsername();
                    
                    modelAndView.getModelMap()
                            .addAttribute("username", username);
                }
            }
        };
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("/WEB-INF/views/**").addResourceLocations("/views/");
    }
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(currentUserHandlerInterceptor());
        super.addInterceptors(registry);
    }
}
