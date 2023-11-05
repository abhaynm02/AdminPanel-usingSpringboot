package com.SpingLoginApplication.LoginApplication.Service;


import com.SpingLoginApplication.LoginApplication.Model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void save(UserEntity user);

    void deleteUser(Long id);

}
