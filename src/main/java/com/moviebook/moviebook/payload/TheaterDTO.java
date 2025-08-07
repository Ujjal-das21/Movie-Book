package com.moviebook.moviebook.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDTO {

    private Long theaterId;
    private String name;
    private String location;
}
