package com.moviebook.moviebook.payload;

import com.moviebook.moviebook.config.constants.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;
    private String name;
    private String email;
    private String phone;
    private UserRole role;
}
