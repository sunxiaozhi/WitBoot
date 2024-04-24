package com.witboot.infrastructure.gateway.impl;

import com.github.pagehelper.PageInfo;
import com.witboot.client.user.dto.data.ErrorCode;
import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.domain.user.model.UserName;
import com.witboot.domain.user.model.UserPassword;
import com.witboot.infrastructure.common.Constants;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.infrastructure.convertor.UserConvertor;
import com.witboot.infrastructure.gateway.impl.database.dataobject.UserDO;
import com.witboot.infrastructure.gateway.impl.database.mapper.UserMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        List<UserEntity> userEntityList = new ArrayList<>();

        List<UserDO> userDOList = userMapper.selectByParam(query);

        new PageInfo<>(userDOList).getTotal();
        userDOList.forEach(userDO -> {
            userEntityList.add(UserConvertor.toEntity(userDO));
        });
        return userEntityList;
    }

    @Override
    public UserEntity findPasswordInfo(String username) {
        Optional<UserDO> userDO = userMapper.selectByUsername(username);

        if (userDO.isEmpty()) {
            throw new WitBootBizException(ErrorCode.B_USER_UNDEFINED);
        }

        String password = userDO.get().getPassword();

        if (Objects.isNull(password)) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDO.get().getId());
        userEntity.setUsername((new UserName(userDO.get().getUsername()).name()));
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
        if (insert < Constants.ONE) {
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
        if (update < Constants.ONE) {
            throw new PersistenceException("更新用户异常");
        }

        return UserConvertor.toEntity(userDO);
    }

    /**
     * 获取登陆用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDO> findById = userMapper.selectByUsername(username);
        if (findById.isEmpty()) {
            throw new WitBootBizException(ErrorCode.B_USER_UNDEFINED);
        }

        return findById.get();
    }

    /**
     * 删除用户
     *
     * @param ids ids
     * @return 删除
     */
    @Override
    public int deleteByIds(String[] ids){
        return userMapper.deleteByIds(ids);
    }
}
