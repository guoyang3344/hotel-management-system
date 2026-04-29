import request from '@/utils/request'

export function getDictTypes(params) {
  return request({
    url: '/dict/types',
    method: 'get',
    params
  })
}

export function getDictType(id) {
  return request({
    url: `/dict/types/${id}`,
    method: 'get'
  })
}

export function createDictType(data) {
  return request({
    url: '/dict/types',
    method: 'post',
    data
  })
}

export function updateDictType(data) {
  return request({
    url: '/dict/types',
    method: 'put',
    data
  })
}

export function deleteDictType(id) {
  return request({
    url: `/dict/types/${id}`,
    method: 'delete'
  })
}

export function deleteDictTypes(ids) {
  return request({
    url: '/dict/types/batch',
    method: 'delete',
    data: ids
  })
}

export function getAllDictTypes() {
  return request({
    url: '/dict/types/listAll',
    method: 'get'
  })
}

export function getDictDatas(params) {
  return request({
    url: '/dict/data',
    method: 'get',
    params
  })
}

export function getDictData(id) {
  return request({
    url: `/dict/data/${id}`,
    method: 'get'
  })
}

export function createDictData(data) {
  return request({
    url: '/dict/data',
    method: 'post',
    data
  })
}

export function updateDictData(data) {
  return request({
    url: '/dict/data',
    method: 'put',
    data
  })
}

export function deleteDictData(id) {
  return request({
    url: `/dict/data/${id}`,
    method: 'delete'
  })
}

export function deleteDictDatas(ids) {
  return request({
    url: '/dict/data/batch',
    method: 'delete',
    data: ids
  })
}

export function getDictDataByType(dictType) {
  return request({
    url: `/dict/data/type/${dictType}`,
    method: 'get'
  })
}
