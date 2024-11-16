package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("部门信息实体类")
@TableName("department")  // 映射数据库表 'department'
public class Department {

    @ApiModelProperty("部门ID")
    @TableId("department_id")  // 映射到 'department_id' 字段，作为主键
    private Long departmentId;

    @ApiModelProperty("父部门ID")
    @TableField("parent_id")  // 映射到 'parent_id' 字段
    private Long parentId;

    @ApiModelProperty("祖级列表")
    @TableField("ancestors")  // 映射到 'ancestors' 字段
    private String ancestors;

    @ApiModelProperty("部门名称")
    @TableField("dept_name")  // 映射到 'dept_name' 字段
    private String deptName;

    @ApiModelProperty("显示顺序")
    @TableField("order_num")  // 映射到 'order_num' 字段
    private Integer orderNum;

    @ApiModelProperty("负责人")
    @TableField("leader")  // 映射到 'leader' 字段
    private String leader;

    @ApiModelProperty("联系电话")
    @TableField("phone")  // 映射到 'phone' 字段
    private String phone;

    @ApiModelProperty("邮箱")
    @TableField("email")  // 映射到 'email' 字段
    private String email;

    @ApiModelProperty("部门状态（0正常，1停用）")
    @TableField("status")  // 映射到 'status' 字段
    private String status;

    @ApiModelProperty("删除标志（0代表存在，2代表删除）")
    @TableField("del_flag")  // 映射到 'del_flag' 字段
    private String delFlag;

    @ApiModelProperty("创建者")
    @TableField("create_by")  // 映射到 'create_by' 字段
    private String createBy;

    @ApiModelProperty("创建时间")
    @TableField("create_time")  // 映射到 'create_time' 字段
    private Date createTime;

    @ApiModelProperty("更新者")
    @TableField("update_by")  // 映射到 'update_by' 字段
    private String updateBy;

    @ApiModelProperty("更新时间")
    @TableField("update_time")  // 映射到 'update_time' 字段
    private Date updateTime;
}
