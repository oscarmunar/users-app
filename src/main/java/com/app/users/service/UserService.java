package com.app.users.service;

import com.app.users.dao.UserDAO;
import com.app.users.dto.UserDTO;
import com.app.users.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"; // de la forma "nombre@correo.com".

    private static final String PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"; // debe tener minúsculas, mayúsculas, números y carácteres especiales.


    public ResponseEntity save(UserDTO userDto) throws JsonProcessingException {

        try {
            String emailTemp =  userDto.getEmail();

            Optional<UserEntity> userOpt = userDAO.findByEmail(emailTemp);

            if(userOpt.isPresent()) {
                return new ResponseEntity<>("{ \"error\": \"El correo ya registrado.\" }", HttpStatus.NOT_FOUND);// ResponseEntity.notFound().build();
            }
            UserEntity user = new UserEntity();
            user.setName(userDto.getName());

            if(!validateStringEmail(userDto.getEmail())) {
                return new ResponseEntity<>("{ \"error\": \"El correo no tiene el formato.\" }", HttpStatus.NOT_FOUND);
            }

            if(!validateStringPassword(userDto.getPassword())) {
                return new ResponseEntity<>("{ \"error\": \"El password no tiene el formato.\" }", HttpStatus.NOT_FOUND);
            }

            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());

            user.setPhones(objectMapper.writeValueAsString(userDto.getPhones()));

            return ResponseEntity.status(HttpStatus.CREATED).body(userDAO.save(user));
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>("{ \"error\": \"Error.\" }", HttpStatus.NOT_FOUND);
        }

    }

    public Optional<UserEntity> getUserById(UUID id) {
        return userDAO.findById(id);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public Boolean validateStringEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public Boolean validateStringPassword(String pass) {
        Pattern pattern = Pattern.compile(PASS_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }
}
