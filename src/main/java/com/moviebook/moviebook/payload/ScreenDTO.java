package com.moviebook.moviebook.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDTO {

    private Long screenId;
    private String name;
    private Long theaterId; 

}
