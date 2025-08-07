package com.moviebook.moviebook.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.moviebook.moviebook.model.ShowSeat;
import com.moviebook.moviebook.payload.ShowSeatDTO;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {

       return  new ModelMapper();
    }

}
