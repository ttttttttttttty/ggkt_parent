package com.atguigu.ggkt.vod.service.impl;


import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.mapper.TeacherMapper;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 讲师 服务实现类
 *
 * @since 2023-07-13
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {


    @Override
    public void findPage(Page<Teacher> page, TeacherQueryVo teacherQueryVo) {
        // 条件对象
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        // 条件参数
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();
        // 向条件对象添加条件参数
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(Teacher::getName, name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq(Teacher::getLevel, level);
        }
        if (!StringUtils.isEmpty(joinDateBegin)) {
            // 大于等于
            wrapper.ge(Teacher::getJoinDate, joinDateBegin);
        }
        if (!StringUtils.isEmpty(joinDateEnd)) {
            // 小于等于
            wrapper.le(Teacher::getJoinDate, joinDateEnd);
        }
        baseMapper.selectPage(page, wrapper);
    }
}
