package com.atguigu.ggkt.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.atguigu.ggkt.vod.mapper.SubjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName: SubjectListener
 * Package: com.atguigu.ggkt.vod.listener
 * Description:
 *
 * @Author:天宇
 * @Create：2023/7/22-20:07
 * @Version: v1.0
 */
@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {

    private SubjectMapper subjectMapper;

    @Autowired
    public void setSubjectMapper(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    @Override
    public void invoke(SubjectEeVo data, AnalysisContext context) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(data, subject);
        subjectMapper.insert(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
