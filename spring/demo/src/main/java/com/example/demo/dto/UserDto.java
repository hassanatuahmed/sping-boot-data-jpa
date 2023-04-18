package com.example.demo.dto;

import com.example.demo.model.Department;
import com.example.demo.model.Tasks;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    public Long id;
    public String email;
    public String lastNAme;
    public String firstName;

    private Department department;
    private List<TaskDto> tasksList;

    public userDto(){
        UserDto userDto = new UserDto();
    }
}
