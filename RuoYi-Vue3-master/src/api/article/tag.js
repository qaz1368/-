import request from '@/utils/request'

// 查询标签列表
export function listTag(query) {
  return request({
    url: '/policy-tags/list',
    method: 'get',
    params: query
  })
}


//根据tagId删除标签信息
export function delPost(tagId) {
  return request({
    url: `/policy-tags/del/${tagId}`,
    method: 'delete'
  })
}
//categoryIds
export function deletePolicyTags(tagIds) {
  return request({
    url: `/policy-tags/batch`,
    method: 'delete',
    data: tagIds
  })
}

// 新增标签信息
export function addPost(data) {
  return request({
    url: '/policy-tags/add',
    method: 'post',
    data: data
  })
}




//根据tagId查询文章信息
export function getTag(tagId) {
  return request({
    url: `/policy-tags/${tagId}`,
    method: 'get',
  })
}


//修改奖项名称信息
export function updateTag(data) {
  return request({
    url: '/policy-tags/update',
    method: 'put',
    data: data
  })
}

//查询数据库的全部奖项名称
export function getPrimaryTagOptions() {
  return request({
    url: '/policy-tags/getPrimaryTagOptions',
    method: 'get'
  })
}


