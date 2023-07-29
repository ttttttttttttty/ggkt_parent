package com.atguigu.ggkt.vod.service;


import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 讲师 服务类
 *
 * @since 2023-07-13
 */
public interface TeacherService extends IService<Teacher> {

    void findPage(Page<Teacher> page, TeacherQueryVo teacherQueryVo);

}
