package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.EnterpriseManagers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EnterpriseManagerMapper extends BaseMapper<EnterpriseManagers> {


    @Select("select * from enterprise_managers where manager_id=#{managerId}")
    EnterpriseManagers selectByManageId(Integer managerId);
}
