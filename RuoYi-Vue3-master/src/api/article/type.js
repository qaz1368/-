import request from '@/utils/request'

// 查询部门列表
export function listType(query) {
  return request({
    url: '/policy-categories/list',
    method: 'get',
    params: query
  })
}

// 删除类型
export function delType(categoryId) {
  return request({
    url: `/policy-categories/${categoryId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}


//新增类型
export function addType(data) {
  return request({
    url: '/policy-categories/add',
    method: 'post',
    data: data
  });
}

//根据typeId查询类型信息
export function getTypeById(categoryId) {
  return request({
    url: `/policy-categories/${categoryId}`,
    method: 'get'
  })
}


// 修改奖项
export function updateType(data) {
  return request({
    url: '/policy-categories/update',
    method: 'put',
    data: data
  })
}


