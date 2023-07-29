package com.atguigu.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * ClassName: ExcelListener
 * Package: com.atguigu.excel
 * Description:
 *
 * @Author:天宇
 * @Create：2023/7/22-16:03
 * @Version: v1.0
 */
public class ExcelListener extends AnalysisEventListener<User> {

    // 一行一行读取excel内容，把每行内容封装到user对象
    // 从excel第二行开始读取
    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        System.out.println(user);
    }

    // 读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头" + headMap);
    }

    // 在所有操作之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
