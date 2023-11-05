package com.SpingLoginApplication.LoginApplication.Service;


import com.SpingLoginApplication.LoginApplication.Model.UserEntity;
import com.SpingLoginApplication.LoginApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceI implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void save(UserEntity user) {

        userRepository.save(user);
    }


    public Page<UserEntity> userList(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }
    @Transactional
 public  void updateRole(Long id,String newRole){
        userRepository.updateUserRole(id,newRole);
 }
 public boolean isEmailExists(String email){
        return userRepository.existsByEmail(email);
 }
 public Page<UserEntity> searchByName(String name,Pageable page){
     return  userRepository.findUsersByNameStartingWith(name,page);

 }
 public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email);
 }

}

