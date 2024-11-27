import request from '@/utils/request'

// 分页查询企业列表
export function listEnterprise(query) {
  return request({
    url: '/api/enterprise/list',
    method: 'get',
    params: query
  })
}

// 新增企业列表
export function addEnterprise(data) {
  return request({
    url: '/api/enterprise/add',
    method: 'post',
    data: data
  })
}
//修改奖项名称信息
export function updateEnterprise(data) {
  return request({
    url: '/api/enterprise/update',
    method: 'put',
    data: data
  })
}

//根据Enterpriseid查询Enterprise信息
export function getEnterprise(companyId) {
  return request({
    url: `/api/enterprise/${companyId}`,
    method: 'get',
  })
}
//删除企业管理者
export function deleteEnterprise(companyId) {
  return request({
    url: `/api/enterprise/delete/${companyId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}
//查询数据库的全部企业名称
export function getEnterpriseOptions() {
  return request({
    url: '/api/enterprise/getEnterpriseOptions',
    method: 'get'
  })
}