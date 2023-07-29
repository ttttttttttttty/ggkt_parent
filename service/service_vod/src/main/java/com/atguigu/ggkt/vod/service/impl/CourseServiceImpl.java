package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.mapper.CourseMapper;
import com.atguigu.ggkt.vod.service.CourseService;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-25
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    private TeacherService teacherService;
    private SubjectService subjectService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //课程列表
    @Override
    public Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        //获取条件值
        String title = courseQueryVo.getTitle(); // 名称
        Long subjectId = courseQueryVo.getSubjectId(); // 二级分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();//一级分类
        Long teacherId = courseQueryVo.getTeacherId(); // 讲师
        // 封装条件
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like(Course::getTitle, title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq(Course::getSubjectId, subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq(Course::getSubjectParentId, subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq(Course::getTeacherId, teacherId);
        }
        // 调用方法查询
        baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pageParam.getTotal();// 总记录数
        long totalPage = pageParam.getPages();// 总页数
        long currentPage = pageParam.getCurrent();// 当前页
        long size = pageParam.getSize();// 每页记录数
        //每页数据集合
        List<Course> records = pageParam.getRecords();
        // 遍历封装讲师和分类名称
        for (Course course : records) {
            this.getTeacherOrSubjectName(course);
        }
        // 封装返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("currentPage", currentPage);
        map.put("size", size);
        map.put("records", records);
        return map;
    }

    // 获取讲师和分类名称
    private Course getTeacherOrSubjectName(Course course) {
        // 查询讲师名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (teacher != null) {
            course
                    .getParam()
                    .put("teacherName", teacher.getName());
        }
        // 查询一级分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if (subjectOne != null) {
            course
                    .getParam()
                    .put("subjectParentTitle", subjectOne.getTitle());
        }
        // 查询二级分类名称
        Subject subjectTow = subjectService.getById(course.getSubjectId());
        if (subjectTow != null) {
            course
                    .getParam()
                    .put("subjectTitle", subjectTow.getTitle());
        }
        return course;
    }
}
