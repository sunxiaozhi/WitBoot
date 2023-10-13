package com.witboot.infrastructure.gateway.impl;

import com.witboot.client.user.dto.data.ErrorCode;
import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.domain.user.model.UserPassword;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.infrastructure.convertor.UserConvertor;
import com.witboot.infrastructure.gateway.impl.database.dataobject.UserDO;
import com.witboot.infrastructure.gateway.impl.database.mapper.UserMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * UserGatewayImpl
 *
 * @author sunxiaoizhi
 */
@Component("userGateway")
public class UserGatewayImpl implements UserGateway {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserEntity save(UserEntity userEntity) {
        // 新增
        if (Objects.isNull(userEntity.getId())) {
            return addUser(userEntity);
        }

        // 修改
        return modifyUser(userEntity);
    }

    @Override
    public UserEntity findById(Long id) {
        Optional<UserDO> findById = userMapper.selectById(id);
        if (findById.isEmpty()) {
            throw new WitBootBizException(ErrorCode.B_USER_UNDEFINED);
        }

        UserDO userDO = findById.get();

        return UserConvertor.toEntity(userDO);
    }

    @Override
    public List<UserEntity> findByParam(UserListByParamQuery query) {
        List<UserEntity> entities = new ArrayList<>();
        userMapper.selectByParam(query).forEach(userDO -> {
            entities.add(UserConvertor.toEntity(userDO));
        });
        return entities;
    }

    @Override
    public UserEntity findPasswordInfo(String username) {
        String password = userMapper.selectPassword(username);
        if (Objects.isNull(password)) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(new UserPassword(new UserPassword.EncryptPassword(password)));
        return userEntity;
    }

    @Override
    public Boolean checkByUsername(Long userId, String username) {
        return userMapper.existByUsername(userId, username);
    }

    /**
     * 新增用户
     */
    private UserEntity addUser(UserEntity userEntity) {
        // 初始化用户信息
        UserDO userDO = UserConvertor.toAddUserDO(userEntity);

        int insert = userMapper.insert(userDO);
        if (insert < 1) {
            throw new PersistenceException("保存用户异常");
        }

        return UserConvertor.toEntity(userDO);
    }

    /**
     * 修改用户
     */
    private UserEntity modifyUser(UserEntity userEntity) {
        Optional<UserDO> findById = userMapper.selectById(userEntity.getId());
        if (findById.isEmpty()) {
            throw new WitBootBizException(ErrorCode.B_USER_UNDEFINED);
        }

        UserDO userDO = findById.get();

        // 更新用户信息
        UserConvertor.toModifyUserDO(userEntity, userDO);

        // 保存userDO
        int update = userMapper.update(userDO);
        if (update < 1) {
            throw new PersistenceException("更新用户异常");
        }

        return UserConvertor.toEntity(userDO);
    }
}
