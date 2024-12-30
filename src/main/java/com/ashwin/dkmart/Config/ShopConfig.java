package com.ashwin.dkmart.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfig {
    
    // private final ModelMapper modelMapper;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    // Constructor injection to initialize the final field
    // public ShopConfig(ModelMapper modelMapper) {
    //     this.modelMapper = modelMapper;
    // }
}
