import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listAccounts(query) {
    return request({
        url: '/system/accounts/list',
        method: 'get',
        params: query
    })
}

// 查询【请填写功能名称】详细
export function getAccounts(id) {
    return request({
        url: '/system/accounts/' + id,
        method: 'get'
    })
}

// 新增【请填写功能名称】
export function addAccounts(data) {
    return request({
        url: '/system/accounts',
        method: 'post',
        data: data
    })
}

// 修改【请填写功能名称】
export function updateAccounts(data) {
    return request({
        url: '/system/accounts',
        method: 'put',
        data: data
    })
}

// 删除【请填写功能名称】
export function delAccounts(id) {
    return request({
        url: '/system/accounts/' + id,
        method: 'delete'
    })
}
