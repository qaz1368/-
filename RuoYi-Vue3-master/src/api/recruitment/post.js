import request from '@/utils/request'

// 查询岗位列表
export function listPost(query) {
    return request({
        url: '/api/job-positions/list',
        method: 'get',
        params: query
    })
}

//根据positionId查询岗位信息
export function getPost(positionId) {
    return request({
        url: `/api/job-positions/${positionId}`,
        method: 'get'
    })
}

//根据positionId删除岗位信息
export function delPost(positionId) {
    return request({
        url: `/api/job-positions/${positionId}`,
        method: 'delete'
    })
}
//deleteJobPositions
export function deleteJobPositions(positionIds) {
    return request({
        url: `/api/job-positions/batch`,
        method: 'delete',
        data: positionIds
    })
}


// 新增岗位
export function addPost(data) {
    return request({
        url: '/api/job-positions/add',
        method: 'post',
        data: data
    })
}

// 修改岗位
export function updatePost(data) {
    return request({
        url: '/api/job-positions/update',
        method: 'put',
        data: data
    })
}

//查询数据库的全部岗位名称
export function getJobPositionOptions() {
    return request({
        url: '/api/job-positions/getJobPositionOptions',
        method: 'get'
    })
}