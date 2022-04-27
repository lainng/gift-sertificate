package com.piatnitsa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class contains Spring configuration for web subproject.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@EnableWebMvc
@Configuration
@ComponentScan("com.piatnitsa")
public class SpringConfig implements WebMvcConfigurer {
}
