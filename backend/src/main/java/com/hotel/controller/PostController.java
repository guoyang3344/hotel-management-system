package com.hotel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.common.Result;
import com.hotel.entity.Post;
import com.hotel.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    @PreAuthorize("hasAuthority('system:post:list')")
    public Result<Page<Post>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            Post post) {
        Page<Post> page = new Page<>(current, size);
        Page<Post> result = postService.pageList(page, post);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:post:query')")
    public Result<Post> getById(@PathVariable Long id) {
        Post post = postService.getById(id);
        return Result.success(post);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:post:add')")
    public Result<Post> save(@RequestBody Post post) {
        postService.save(post);
        return Result.success("新增成功", post);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('system:post:edit')")
    public Result<Post> update(@RequestBody Post post) {
        postService.updateById(post);
        return Result.success("修改成功", post);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:post:remove')")
    public Result<Void> delete(@PathVariable Long id) {
        postService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:post:remove')")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        postService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/listAll")
    public Result<List<Post>> listAll() {
        List<Post> posts = postService.list();
        return Result.success(posts);
    }
}
