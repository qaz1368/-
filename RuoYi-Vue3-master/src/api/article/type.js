import request from '@/utils/request'

// 查询标签类型
export function listType(query) {
  return request({
    url: '/policy-categories/list',
    method: 'get',
    params: query
  })
}


//删除标签类型
export function delType(categoryId) {
  return request({
    url: `/policy-categories/delete/${categoryId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}

//categoryIds
export function delTypeList(categoryIds) {
  return request({
    url: `/policy-categories/batch`,
    method: 'delete',
    data: categoryIds
  })
}

//添加文章
export function addType(data) {
  return request({
    url: '/policy-categories/add',
    method: 'post',
    data: data
  });
}

//根据categoryId查询文章信息
export function getType(categoryId) {
  return request({
    url: `/policy-categories/${categoryId}`,
    method: 'get',
  })
}
//修改奖项名称信息
export function updateType(data) {
  return request({
    url: '/policy-categories/update',
    method: 'put',
    data: data
  })
}

//查询数据库的全部奖项名称
export function getCategoryOptions() {
  return request({
    url: '/policy-categories/getCategoryOptions',
    method: 'get'
  })
}