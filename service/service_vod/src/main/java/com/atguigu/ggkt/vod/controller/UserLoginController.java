package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: UserLoginController
 * Package: com.atguigu.ggkt.vod.controller
 * Description: 登录 前端控制器
 *
 * @Author:天宇
 * @Create：2023/7/15-22:55
 * @Version: v1.0
 */
@Api(tags = "系统登录接口")
@RestController
@RequestMapping(value = "/admin/vod/user")
@CrossOrigin // 跨域
public class UserLoginController {

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login() {
        // {"code":20000,"data":{"token":"admin-token"}}
        Map<String, Object> map = new HashMap<>();
        map.put("token", "admin-token");
        return Result.ok(map);
    }

    @ApiOperation("获取登录人信息接口")
    @GetMapping("/info")
    public Result info() {
        // {"code":20000,
        // "data":{"roles":["admin"],
        // "introduction":"I am a super administrator",
        // "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
        // "name":"Super Admin"}}
        Map<String, Object> map = new HashMap<>();
        map.put("roles", "admin");
        map.put("introduction", "I am a super administrator");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", "Super Admin");
        return Result.ok(map);
    }
}
