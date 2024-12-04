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
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}

//根据articleId查询文章信息
export function getArticle(articleId) {
  return request({
    url: `/policy-articles/${articleId}`,
    method: 'get',
  })
}

export function delArticle(articleId) {
  return request({
    url: `/policy-articles/${articleId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}

//categoryIds
export function deletePolicyArticles(articleIds) {
  return request({
    url: `/policy-articles/batch`,
    method: 'delete',
    data: articleIds
  })
}

//修改奖项名称信息

export function updatePolicyArticle(data) {
  return request({
    url: '/policy-articles/updatePolicyArticle',
    method: 'put',
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    // 添加以下配置以确保正确处理 FormData
    transformRequest: [function (data) {
      return data;
    }]
  })
}

//更新文章
// 更新文章视频的 API 函数
export function updateArticle(data) {
  return request({
    url: '/policy-articles/updateVideo',
    method: 'post',
    data: data,
    // 重要：当使用 FormData 时，不要手动设置 Content-Type
    // axios 会自动设置正确的 Content-Type 和 boundary
  });
}