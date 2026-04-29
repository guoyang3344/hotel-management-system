import request from '@/utils/request'

export function getUsers(params) {
  return request({
    url: '/users',
    method: 'get',
    params
  })
}

export function getUser(id) {
  return request({
    url: `/users/${id}`,
    method: 'get'
  })
}

export function createUser(data) {
  return request({
    url: '/users',
    method: 'post',
    data
  })
}

export function updateUser(data) {
  return request({
    url: '/users',
    method: 'put',
    data
  })
}

export function deleteUser(id) {
  return request({
    url: `/users/${id}`,
    method: 'delete'
  })
}

export function deleteUsers(ids) {
  return request({
    url: '/users/batch',
    method: 'delete',
    data: ids
  })
}

export function resetPwd(data) {
  return request({
    url: '/users/resetPwd',
    method: 'put',
    data
  })
}

export function changeStatus(data) {
  return request({
    url: '/users/changeStatus',
    method: 'put',
    data
  })
}
