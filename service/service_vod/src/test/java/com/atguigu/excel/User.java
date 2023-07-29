package com.atguigu.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * ClassName: User
 * Package: com.atguigu.excel
 * Description: Excel实体类
 *
 * @Author:天宇
 * @Create：2023/7/22-15:41
 * @Version: v1.0
 */
@Data
public class User {

    @ExcelProperty(value = "用户编号", index = 0)
    private int id;

    @ExcelProperty(value = "用户名称", index = 1)
    private String name;
}
