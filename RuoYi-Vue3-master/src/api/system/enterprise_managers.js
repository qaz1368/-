import request from '@/utils/request'

// 查询申请信息列表
export function listEnterprise_managers(query) {
  return request({
    url: '/enterprise-managers/page',
    method: 'get',
    params: query
  })
}

//新增企业管理者
export function addEnterprise_managers(data) {
  return request({
    url: '/enterprise-managers/add',
    method: 'post',
    data: data
  })
}

//删除企业管理者
export function delEnterprise_managers(manager_id) {
  return request({
    url: `/enterprise-managers/delete/${manager_id}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}

//修改企业管理者信息
export function updateEnterprise_managers(data) {
  return request({
    url: '/enterprise-managers/update',
    method: 'put',
    data: data
  })
}


//根据manager_id查询企业管理者信息
export function getEnterprise_managers(manager_id) {
  return request({
    url: `/enterprise-managers/getManager/${manager_id}`,
    method: 'get'
  })
}
//查询数据库的全部管理者
export function getEnterpriseManagersOptions() {
  return request({
    url: '/enterprise-managers/getEnterpriseManagersOptions',
    method: 'get'
  })
}