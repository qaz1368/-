package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.Approval;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalMapper extends BaseMapper<Approval> {
    List<Approval> selectPage(@Param("offset") int offset, @Param("limit") int limit);
    int count();
    List<Approval> selectPageByProcessIds(@Param("offset") int offset, @Param("limit") int limit, @Param("processIds") List processIds);
    int countByProcessIds(@Param("processIds") List processIds);
}
