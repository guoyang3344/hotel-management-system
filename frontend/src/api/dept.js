import request from '@/utils/request'

export function getDepts(params) {
  return request({
    url: '/depts',
    method: 'get',
    params
  })
}

export function getDept(id) {
  return request({
    url: `/depts/${id}`,
    method: 'get'
  })
}

export function createDept(data) {
  return request({
    url: '/depts',
    method: 'post',
    data
  })
}

export function updateDept(data) {
  return request({
    url: '/depts',
    method: 'put',
    data
  })
}

export function deleteDept(id) {
  return request({
    url: `/depts/${id}`,
    method: 'delete'
  })
}

export function getDeptTree() {
  return request({
    url: '/depts/tree',
    method: 'get'
  })
}
