package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.domain.entity.EnterpriseManagers;
import com.ruoyi.system.domain.vo.EnterpriseManagersVO;
import com.ruoyi.system.mapper.entrepreneurPark.EnterpriseManagerMapper;
import com.ruoyi.system.mapper.entrepreneurPark.EnterpriseMapper;
import com.ruoyi.system.service.entrepreneurPark.EnterpriseManagerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnterpriseManagerServiceImpl extends ServiceImpl<EnterpriseManagerMapper, EnterpriseManagers>
        implements EnterpriseManagerService {

    @Autowired
    private EnterpriseManagerMapper managerMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public boolean saveManager(EnterpriseManagers manager) {
        return save(manager);  // 使用 MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateManager(EnterpriseManagers manager) {
        return updateById(manager);  // 使用 MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteManager(Integer managerId) {
        return removeById(managerId);  // 使用 MyBatis-Plus 提供的删除方法
    }

    @Override
    public boolean deleteBatch(List<Integer> managerIds) {
        return removeByIds(managerIds);  // 批量删除
    }

    @Override
    public IPage<EnterpriseManagersVO> getManagersPage(int page,int  size,String enterpriseNam,String name) {
        // 创建分页请求
        Page<EnterpriseManagers> pageRequest = new Page<>(page, size);

        // 构建查询条件
        QueryWrapper<EnterpriseManagers> queryWrapper = new QueryWrapper<>();
        if (enterpriseNam != null && !enterpriseNam.isEmpty()) {
            QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("company_name", enterpriseNam);
            Enterprise enterpriseEntity = enterpriseMapper.selectOne(queryWrapper1);
            queryWrapper.eq("company_id", enterpriseEntity.getCompanyId());
        }
        if (name != null && !name.isEmpty()) {
            queryWrapper.like("name", name);
        }

        // 执行分页查询
        IPage<EnterpriseManagers> managersPageList = page(pageRequest, queryWrapper);

        // 定义起始位置和每页大小
        int start = (page - 1) * size; // 起始位置

        // 提取部分数据，类似于分页查询
        List<EnterpriseManagers> managersPage = managersPageList.getRecords().stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());

        // 转换为 VO 对象
        IPage<EnterpriseManagersVO> resultPage = new Page<>();
        List<EnterpriseManagersVO> records = new ArrayList<>();
        for (EnterpriseManagers manager : managersPage) {
            EnterpriseManagersVO vo = new EnterpriseManagersVO();
            // 根据企业 ID 查询企业名称
            Enterprise enterprise = enterpriseMapper.selectById(manager.getCompanyId());
            String enterpriseName = enterprise != null ? enterprise.getCompanyName() : null;
            BeanUtils.copyProperties(manager, vo);
            vo.setCompany(enterpriseName);
            records.add(vo);
        }
        //查询数据的数量
        Long count = lambdaQuery().select().count();
        resultPage.setRecords(records);
        resultPage.setTotal(count);
        resultPage.setSize(managersPageList.getSize());
        resultPage.setCurrent(managersPageList.getCurrent());

        return resultPage;
    }

}
