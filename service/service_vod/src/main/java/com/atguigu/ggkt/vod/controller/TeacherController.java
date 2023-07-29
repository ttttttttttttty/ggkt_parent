package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.atguigu.ggkt.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 讲师 前端控制器
 *
 * @since 2023-07-13
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping(value = "/admin/vod/teacher")
@CrossOrigin // 跨域
public class TeacherController {

    private TeacherService teacherService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // 查询所有讲师接口
    @ApiOperation("查询所有讲师")
    @GetMapping("/findAll")
    public Result findAllTeacher() {
        // 调用Servier
        List<Teacher> list = teacherService.list();
        if (CollectionUtils.isEmpty(list)) {
            return Result.fail().message("查询数据失败");
        }
        return Result.ok(list).message("查询数据成功");
    }

    // 逻辑删除讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id) {
        boolean result = teacherService.removeById(id);
        if (result) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 条件查询分页
    @ApiOperation("条件查询分页")
    @GetMapping("/findQueryPage/{pageNo}/{pageSize}")
    public Result findPage(
            @ApiParam("分页查询页码数") @PathVariable(value = "pageNo") Integer pageNo,
            @ApiParam("分页查询页大小") @PathVariable(value = "pageSize") Integer pageSize,
            @ApiParam("分页查询的查询条件") TeacherQueryVo teacherQueryVo) {
        //创建page对象
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        //判断teacherQueryVo对象是否为空
        if (teacherQueryVo == null) {
            //查询全部
            teacherService.page(page, null);
            return Result.ok(page);
        } else {
            teacherService.findPage(page, teacherQueryVo);
            return Result.ok(page);
        }
    }

    // 添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("/saveTeacher")
    public Result saveTeacher(
            @ApiParam("讲师信息") @RequestBody Teacher teacher
    ) {
        boolean result = teacherService.save(teacher);
        if (result) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 修改-根据id查询
    @ApiOperation("修改-根据id查询")
    @GetMapping("/getTeacher/{id}")
    public Result getTeacher(
            @ApiParam("id值") @PathVariable Long id
    ) {
        Teacher teacher = teacherService.getById(id);
        if (null != teacher) {
            return Result.ok(teacher);
        } else {
            return Result.fail().message("id有误，查询失败");
        }
    }

    // 修改-最终实现
    @ApiOperation("修改-最终实现")
    @PutMapping("/updateTeacher")
    public Result updateTeacher(
            @ApiParam("讲师信息") @RequestBody Teacher teacher
    ) {
        boolean result = teacherService.updateById(teacher);
        if (result) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 批量删除讲师
    @ApiOperation("批量删除讲师")
    @DeleteMapping("/removeBatch")
    public Result removeBatch(
            @ApiParam("讲师id") @RequestBody List<Long> ids
    ) {
        boolean result = teacherService.removeByIds(ids);
        if (result) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }
}

