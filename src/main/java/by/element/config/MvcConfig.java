package by.element.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan
@PropertySource("classpath:excelExport.properties")
@PropertySource("classpath:mailSender.properties")
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(@NotNull ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("security/login");
    }

    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/public/images/");
        registry.addResourceHandler("/styles/**")
                .addResourceLocations("classpath:/static/public/styles/");
        registry.addResourceHandler("/scripts/**")
                .addResourceLocations("classpath:/static/public/scripts/");
    }

    @Bean
    public ViewResolver beanNameViewResolver() {
        return new BeanNameViewResolver();
    }

    @Bean
    public ViewResolver jspViewResolver() {
        var viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates/list/");
        viewResolver.setSuffix(".ftlh");
        return viewResolver;
    }
}