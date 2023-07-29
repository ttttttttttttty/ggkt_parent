package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;

/**
 * ClassName: TestRead
 * Package: com.atguigu.excel
 * Description: 测试Excel读操作
 *
 * @Author:天宇
 * @Create：2023/7/22-16:53
 * @Version: v1.0
 */
public class TestRead {
    public static void main(String[] args) {
        // 设置文件名称和路径
        String fileName = "D:\\Download\\atguigu.xlsx";
        // 调用方法读操作
        EasyExcel.read(fileName,User.class,new ExcelListener())
                .sheet()// 默认读取第一个sheet
                .doRead();
    }
}
