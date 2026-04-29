import request from '@/utils/request'

export function getRoles(params) {
  return request({
    url: '/roles',
    method: 'get',
    params
  })
}

export function getRole(id) {
  return request({
    url: `/roles/${id}`,
    method: 'get'
  })
}

export function createRole(data) {
  return request({
    url: '/roles',
    method: 'post',
    data
  })
}

export function updateRole(data) {
  return request({
    url: '/roles',
    method: 'put',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: `/roles/${id}`,
    method: 'delete'
  })
}

export function deleteRoles(ids) {
  return request({
    url: '/roles/batch',
    method: 'delete',
    data: ids
  })
}

export function changeRoleStatus(data) {
  return request({
    url: '/roles/changeStatus',
    method: 'put',
    data
  })
}

export function getAllRoles() {
  return request({
    url: '/roles/listAll',
    method: 'get'
  })
}
