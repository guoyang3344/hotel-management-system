package com.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.Post;
import com.hotel.mapper.PostMapper;
import com.hotel.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public Page<Post> pageList(Page<Post> page, Post post) {
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        if (post != null) {
            if (StringUtils.hasText(post.getPostCode())) {
                queryWrapper.like(Post::getPostCode, post.getPostCode());
            }
            if (StringUtils.hasText(post.getPostName())) {
                queryWrapper.like(Post::getPostName, post.getPostName());
            }
            if (StringUtils.hasText(post.getStatus())) {
                queryWrapper.eq(Post::getStatus, post.getStatus());
            }
        }
        queryWrapper.orderByAsc(Post::getSort);
        return this.page(page, queryWrapper);
    }
}
