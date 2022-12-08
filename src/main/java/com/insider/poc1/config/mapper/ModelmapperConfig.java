package com.insider.poc1.config.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelmapperConfig{

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

}
