import request from '@/utils/request'

// 查询招聘信息列表
export function listRecruitment(query) {
    return request({
        url: '/api/job-enterprise-positions/list',
        method: 'get',
        params: query
    })
}


//根据企业enterpriseJobId查询招聘信息
export function getRecruitment(enterpriseJobId) {
    return request({
        url: `/api/job-enterprise-positions/${enterpriseJobId}`,
        method: 'get'
    })
}

//根据enterpriseJobId删除招聘信息
export function delRecruitment(enterpriseJobId) {
    return request({
        url: `/api/job-enterprise-positions/del/${enterpriseJobId}`,
        method: 'delete'
    })
}
//deleteJobEnterprisePositions
export function deleteJobEnterprisePositions(enterpriseJobIds) {
    return request({
        url: `/api/job-enterprise-positions/batch`,
        method: 'delete',
        data: enterpriseJobIds
    })
}

//新增招聘信息
export function addRecruitment(data) {
    return request({
        url: '/api/job-enterprise-positions/add',
        method: 'post',
        data: data
    })
}

//更新招聘信息
export function updateRecruitment(data) {
    return request({
        url: '/api/job-enterprise-positions/update',
        method: 'put',
        data: data
    })
}