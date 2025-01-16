package com.max.restfulwebservices.model;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //@NotNull
    private  Integer id;

    @Size(min = 2)
    private  String name;

    @Past
    private Date birthDate;
}
