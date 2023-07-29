package com.atguigu.ggkt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: GGKTExceptione
 * Package: com.atguigu.ggkt.exception
 * Description: 自定义异常
 *
 * @Author:天宇
 * @Create：2023/7/14-23:27
 * @Version: v1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GGKTExceptione extends RuntimeException {

    private Integer code;
    private String message;
}
