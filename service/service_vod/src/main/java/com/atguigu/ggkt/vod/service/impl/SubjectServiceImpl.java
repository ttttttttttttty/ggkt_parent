package com.atguigu.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.ggkt.exception.GGKTExceptione;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.atguigu.ggkt.vod.listener.SubjectListener;
import com.atguigu.ggkt.vod.mapper.SubjectMapper;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-22
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    private SubjectListener subjectListener;

    @Autowired
    public void setSubjectListener(SubjectListener subjectListener) {
        this.subjectListener = subjectListener;
    }

    //查询下一层课程分类
    @Override
    public List<Subject> findChildSubject(Long id) {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getParentId, id);
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        // 向list集合每个Subject对象中设置hasChildren
        for (Subject subject : subjectList) {
            Long subjectId = subject.getId();
            boolean isChild = this.isChildren(subjectId);
            subject.setHasChildren(isChild);
        }
        return subjectList;
    }

    // 课程分类导出
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            // 设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 查询课程分类表所有数据
            List<Subject> subjectList = baseMapper.selectList(null);

            // 对象处理 List<Subject> --> List<SubjectEeVo>
            List<SubjectEeVo> subjectEeVoList = new ArrayList<>(subjectList.size());
            for (Subject subject : subjectList) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtils.copyProperties(subject, subjectEeVo);
//                subjectEeVo.setId(subject.getId());
//                subjectEeVo.setTitle(subject.getTitle());
//                subjectEeVo.setParentId(subject.getParentId());
//                subjectEeVo.setSort(subject.getSort());
                subjectEeVoList.add(subjectEeVo);
            }

            // EasyExcel写操作
            EasyExcel.write(
                            response.getOutputStream(),
                            SubjectEeVo.class)
                    .sheet("课程分类")
                    .doWrite(subjectEeVoList);
        } catch (IOException e) {
            throw new GGKTExceptione(20001, "导出失败");
        }

    }

    // 课程分类导入
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(
                            file.getInputStream(),
                            SubjectEeVo.class,
                            subjectListener)
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            throw new GGKTExceptione(20001, "导入失败");
        }
    }

    // 判断id下面是否有子节点
    private boolean isChildren(Long subjectId) {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getParentId, subjectId);
        Integer integer = baseMapper.selectCount(wrapper);
        return integer > 0;
    }
}
