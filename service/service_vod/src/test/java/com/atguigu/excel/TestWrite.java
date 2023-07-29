package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: TestWrite
 * Package: com.atguigu.excel
 * Description: 测试Excel写操作
 *
 * @Author:天宇
 * @Create：2023/7/22-15:43
 * @Version: v1.0
 */
public class TestWrite {
    public static void main(String[] args) {
        // 设置文件名称和路径
        String fileName = "D:\\Download\\atguigu.xlsx";
        EasyExcel.write(fileName, User.class)
                .sheet("写操作")
                .doWrite(data());
    }

    //循环设置要添加的数据，最终封装到list集合中
    private static List<User> data() {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User data = new User();
            data.setId(i);
            data.setName("张三" + i);
            list.add(data);
        }
        return list;
    }
}
