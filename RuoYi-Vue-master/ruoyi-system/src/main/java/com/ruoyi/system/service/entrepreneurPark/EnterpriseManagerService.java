package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.DTO.EnterpriseManagersDTO;
import com.ruoyi.system.domain.entity.EnterpriseManagers;
import com.ruoyi.system.domain.vo.EnterpriseManagersVO;

import java.util.List;

public interface EnterpriseManagerService extends IService<EnterpriseManagers> {

    boolean saveManager(EnterpriseManagers manager);    // 新增管理者

    boolean updateManager(EnterpriseManagers manager);  // 更新管理者信息

    boolean deleteManager(Integer managerId);         // 删除管理者

    boolean deleteBatch(List<Integer> managerIds);    // 批量删除

    IPage<EnterpriseManagersVO> getManagersPage(int page,int  size,String enterpriseNam,String name);  // 分页查询管理者列表

    boolean addManager(EnterpriseManagersDTO enterpriseManagersDTO);

    boolean updateManagers(EnterpriseManagersDTO enterpriseManagersDTO);

    EnterpriseManagersVO getByIdManagerId(Integer managerId);
}
