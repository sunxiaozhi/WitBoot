package com.witboot.service;

import com.witboot.api.UserService;
import com.witboot.api.dto.UserRequestDTO;
import com.witboot.dao.mybatis.dataobject.UserDO;
import com.witboot.dao.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Component;

/**
 * 用户接口实现类
 *
 * @author sunxiaozhi
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private static final BeanCopier COPIER = BeanCopier.create(UserRequestDTO.class, UserDO.class, false);

    @Override
    public UserDO getUser(Long id) {
        return  userMapper.getById(id);
    }

    @Override
    public String getUserName(Long id) {
        UserDO userDO = userMapper.getById(id);
        return userDO != null ? userDO.getName() : null;
    }

    @Override
    public UserDO addUser(UserRequestDTO userRequestDTO) {
        UserDO userDO = new UserDO();
        COPIER.copy(userRequestDTO, userDO, null);

        Long id = userMapper.insert(userDO);
        userDO.setId(id);
        return userDO;
    }
}
