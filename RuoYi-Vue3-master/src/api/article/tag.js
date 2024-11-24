import request from '@/utils/request'

// 查询标签列表
export function listTag(query) {
  return request({
    url: '/policy-tags/list',
    method: 'get',
    params: query
  })
}

//删除标签类型
export function delTag(tagId) {
  return request({
    url: `/policy-tags/del/${tagId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}

//添加文章
export function addTag(data) {
  return request({
    url: '/policy-tags/add',
    method: 'post',
    data: data
  });
}

//根据categoryId查询文章信息
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


