import request from '@/utils/request'

// 查询部门列表
export function listArticle(query) {
  return request({
    url: '/policy-articles/list',
    method: 'get',
    params: query
  })
}


//添加文章
export function addArticle(data) {
  return request({
    url: '/policy-articles/add',
    method: 'post',
    data: data
  });
}

//根据articleId查询文章信息
export function getArticle(articleId) {
  return request({
    url: `/policy-articles/${articleId}`,
    method: 'get',
  })
}

//删除企业管理者
export function delArticle(articleId) {
  return request({
    url: `/policy-articles/${articleId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}

//修改奖项名称信息
export function updateArticle(data) {
  return request({
    url: '/policy-articles/update',
    method: 'put',
    data: data
  })
}