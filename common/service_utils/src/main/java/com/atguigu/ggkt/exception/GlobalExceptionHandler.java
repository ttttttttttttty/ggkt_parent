package com.atguigu.ggkt.exception;

import com.atguigu.ggkt.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.atguigu.ggkt.exception
 * Description: 统一异常处理类
 *
 * @Author:天宇
 * @Create：2023/7/14-23:17
 * @Version: v1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail().message("全局异常:".concat(e.getMessage()));
    }

    // 特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        return Result.fail().message("特定异常:".concat(e.getMessage()));
    }

    // 自定义异常处理
    @ExceptionHandler(GGKTExceptione.class)
    @ResponseBody
    public Result error(GGKTExceptione e) {
        return Result.fail().message("自定义异常:".concat(e.getMessage()));
    }
}
