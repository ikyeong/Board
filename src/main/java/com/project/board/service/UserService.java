package com.project.board.service;

import com.project.board.domain.User;
import com.project.board.domain.dto.UserDto;
import com.project.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(UserDto.Request dto){
        User user = dto.toEntity();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User loginValidationCheck(UserDto.Request dto){
        User user = userRepository.findByUsername(dto.getUsername()).orElse(null);
        if (user != null){
            if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public Map<String, String> errorHandling(BindingResult bindingResult){
        Map<String, String> errorResult = new HashMap<>();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors){
            errorResult.put(error.getField()+"_error",error.getDefaultMessage());
        }
        return errorResult;
    }

    public Boolean duplicationCheck(UserDto.Request dto){
        User user = userRepository.findByUsername(dto.getUsername()).orElse(null);
        if (user == null) return false;
        return true;
    }
}
