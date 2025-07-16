package com.witboot.infrastructure.gateway.impl.database.mapper;

import com.witboot.domain.department.query.DepartmentListByParamQuerySpec;
import com.witboot.infrastructure.gateway.impl.database.dataobject.DepartmentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * Department Mapper
 *
 * @author sunxiaoizhi
 */
@Mapper
public interface DepartmentMapper {
    /**
     * 新增
     *
     * @param departmentDO 用户实体
     * @return 结果
     */
    int insert(DepartmentDO departmentDO);

    /**
     * 更新
     *
     * @param departmentDO 用户实体
     * @return 结果
     */
    int update(DepartmentDO departmentDO);

    /**
     * 通过id查询
     *
     * @param id id
     * @return 结果
     */
    Optional<DepartmentDO> selectById(Long id);

    /**
     * 通过用户名称查询
     * @param departmentname 用户名称
     * @return 结果
     */
//    Optional<DepartmentDO> selectByDepartmentname(String departmentname);

    /**
     * 可带参数数据查询
     *
     * @param departmentListByParamQuerySpec 查询条件
     * @return 结果
     */
    List<DepartmentDO> selectByParam(DepartmentListByParamQuerySpec departmentListByParamQuerySpec);

    /**
     * 判断用户名是否存在
     *
     * @param departmentId 用户id
     * @param departmentname 用户名称
     * @return 是否存在
     */
//    Boolean existByDepartmentname(@Param("departmentId") Long departmentId, @Param("departmentname") String departmentname);

    /**
     * 删除数据
     *
     * @param ids ids
     * @return 结果
     */
    int deleteByIds(String[] ids);
}
