package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-25
 */
public interface CourseService extends IService<Course> {

    // 课程列表
    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);
}
