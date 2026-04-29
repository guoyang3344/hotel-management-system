package com.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.Dept;
import com.hotel.mapper.DeptMapper;
import com.hotel.service.DeptService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public List<Dept> selectDeptList(Dept dept) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        if (dept != null) {
            if (StringUtils.hasText(dept.getDeptName())) {
                queryWrapper.like(Dept::getDeptName, dept.getDeptName());
            }
            if (StringUtils.hasText(dept.getStatus())) {
                queryWrapper.eq(Dept::getStatus, dept.getStatus());
            }
        }
        queryWrapper.orderByAsc(Dept::getSort);
        return this.list(queryWrapper);
    }

    @Override
    public List<Dept> buildDeptTree(List<Dept> depts) {
        Map<Long, List<Dept>> parentMap = depts.stream()
                .collect(Collectors.groupingBy(Dept::getParentId));
        
        depts.forEach(dept -> {
            dept.setChildren(parentMap.getOrDefault(dept.getId(), new ArrayList<>()));
        });
        
        return depts.stream()
                .filter(dept -> dept.getParentId() == 0)
                .collect(Collectors.toList());
    }
}
