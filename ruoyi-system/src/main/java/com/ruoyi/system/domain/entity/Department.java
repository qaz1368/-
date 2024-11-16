package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 部门表（department）对应的实体类
 * 记录系统中各个部门的信息，包括部门名称、负责人、联系方式等。
 */
@Data
@TableName("department")  // 映射到数据库中的"department"表
public class Department {

    @TableId(value = "department_id", type = IdType.AUTO)  // 主键，自动增长
    private Long departmentId;  // 部门ID，自增

    @TableField("parent_id")
    private Long parentId;  // 父部门ID，默认为0，允许null

    @TableField("ancestors")
    private String ancestors;  // 祖级列表，默认为空字符串，允许null

    @TableField("dept_name")
    private String deptName;  // 部门名称，不能为空

    @TableField("order_num")
    private Integer orderNum;  // 显示顺序，默认为0，允许null

    @TableField("leader")
    private String leader;  // 部门负责人，允许null

    @TableField("phone")
    private String phone;  // 联系电话，允许null

    @TableField("email")
    private String email;  // 部门邮箱，允许null

    @TableField("status")
    private String status;  // 部门状态（'0' 正常，'1' 停用），默认为'0'

    @TableField("del_flag")
    private String delFlag;  // 删除标志（'0' 代表存在，'2' 代表删除），默认为'0'

    @TableField("create_by")
    private String createBy;  // 创建者，默认为空字符串，允许null

    @TableField("create_time")
    private Date createTime;  // 创建时间，允许null

    @TableField("update_by")
    private String updateBy;  // 更新者，默认为空字符串，允许null

    @TableField("update_time")
    private Date updateTime;  // 更新时间，允许null
}
