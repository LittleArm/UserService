package com.example.userservice.dtos;

import lombok.Data;

@Data
public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private String dateOfBirth;
    private String imageUrl;
}
