package com.witboot.user.service;

import com.witboot.api.UserService;
import com.witboot.api.dto.UserRequestDTO;
import com.witboot.dao.mybatis.dataobject.UserDO;
import com.witboot.dao.mybatis.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户接口实现类
 *
 * @author sunxiaozhi
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    private static final BeanCopier COPIER = BeanCopier.create(UserRequestDTO.class, UserDO.class, false);

    @Override
    public List<UserDO> getUserList() {
        return  userMapper.getUserList();
    }

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
