package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.UserDto;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.util.PasswordUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public class UserMapper {

    public static User mapDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setStreet(userDto.getStreet());
        user.setCity(userDto.getCity());
        user.setCountry(userDto.getCountry());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setGender(userDto.getGender());
        user.setAge(userDto.getAge());
        user.setPassword(PasswordUtils.hashPassword(userDto.getPassword())); // Call hashPassword from PasswordUtils
        return user;
    }
}
