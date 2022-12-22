package com.witboot.api;


import com.witboot.api.model.UserModel;


public interface UserService {

    String getUserName(Long id);

    UserModel addUser(UserModel user);
}
