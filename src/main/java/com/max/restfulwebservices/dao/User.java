package com.max.restfulwebservices.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //@NotNull
    private  Integer id;

    @NotEmpty
    private  String name;

    @NotNull
    private Date birthDate;
}
