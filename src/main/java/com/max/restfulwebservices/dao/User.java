package com.max.restfulwebservices.dao;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"password"})
@JsonFilter("UserFilter") // กำหนด Filter Name
public class User {
    //@NotNull
    private  Integer id;

    @Size(min = 2)
    @Schema(description = "Full name of the user", example = "John Doe")
    private  String name;

    @Past
    private Date birthDate;

    //@JsonIgnore
    private String password;
}
