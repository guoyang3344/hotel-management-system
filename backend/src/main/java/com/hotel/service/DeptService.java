package com.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hotel.entity.Dept;

import java.util.List;

public interface DeptService extends IService<Dept> {
    List<Dept> selectDeptList(Dept dept);
    List<Dept> buildDeptTree(List<Dept> depts);
}
