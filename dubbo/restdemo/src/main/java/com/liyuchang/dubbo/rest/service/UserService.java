package com.liyuchang.dubbo.rest.service;

import com.liyuchang.dubbo.rest.entity.User;

public interface UserService {
    void registerUser(User user);

    User get(Long id);
}
