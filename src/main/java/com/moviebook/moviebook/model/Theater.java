package com.moviebook.moviebook.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="theaters")
@NoArgsConstructor
@AllArgsConstructor
public class Theater {

     @Id
     @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long theaterId;
    private String name;
    private String location;

    @OneToMany(mappedBy="theater",cascade=CascadeType.ALL,orphanRemoval=true)
    private List<Screen> screens = new ArrayList<>();

}
