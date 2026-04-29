package com.hotel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hotel.entity.Post;

public interface PostService extends IService<Post> {
    Page<Post> pageList(Page<Post> page, Post post);
}
