package com.witboot.infrastructure.gateway.impl.database.mapper;

import com.witboot.domain.user.query.UserQuerySpec;
import com.witboot.infrastructure.gateway.impl.database.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * User Mapper
 *
 * @author sunxiaoizhi
 */
@Mapper
public interface UserMapper {
    /**
     * 新增
     *
     * @param userDO 用户实体
     * @return 结果
     */
    int insert(UserDO userDO);

    /**
     * 更新
     *
     * @param userDO 用户实体
     * @return 结果
     */
    int update(UserDO userDO);

    /**
     * 通过id查询
     *
     * @param id id
     * @return 结果
     */
    Optional<UserDO> selectById(Long id);

    /**
     * 通过用户名称查询
     * @param username 用户名称
     * @return 结果
     */
    Optional<UserDO> selectByUsername(String username);

    /**
     * 可带参数数据查询
     *
     * @param userListByParamQuerySpec 查询条件
     * @return 结果
     */
    List<UserDO> selectByParam(UserQuerySpec userListByParamQuerySpec);

    /**
     * 判断用户名是否存在
     *
     * @param userId 用户id
     * @param username 用户名称
     * @return 是否存在
     */
    Boolean existByUsername(@Param("userId") Long userId, @Param("username") String username);

    /**
     * 删除数据
     *
     * @param ids ids
     * @return 结果
     */
    int deleteByIds(String[] ids);
}
