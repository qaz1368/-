package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("角色信息实体类")
@TableName("role")  // 映射数据库表 'role'
public class Role {

    @ApiModelProperty("角色ID")
    @TableId("role_id")  // 映射到 'role_id' 字段，作为主键
    private Long roleId;

    @ApiModelProperty("角色名称")
    @TableField("role_name")  // 映射到 'role_name' 字段
    private String roleName;

    @ApiModelProperty("角色权限字符串")
    @TableField("role_key")  // 映射到 'role_key' 字段
    private String roleKey;

    @ApiModelProperty("显示顺序")
    @TableField("role_sort")  // 映射到 'role_sort' 字段
    private Integer roleSort;

    @ApiModelProperty("数据范围")
    @TableField("data_scope")  // 映射到 'data_scope' 字段
    private String dataScope;

    @ApiModelProperty("菜单树选择项是否关联显示")
    @TableField("menu_check_strictly")  // 映射到 'menu_check_strictly' 字段
    private Boolean menuCheckStrictly;

    @ApiModelProperty("部门树选择项是否关联显示")
    @TableField("dept_check_strictly")  // 映射到 'dept_check_strictly' 字段
    private Boolean deptCheckStrictly;

    @ApiModelProperty("角色状态")
    @TableField("status")  // 映射到 'status' 字段
    private String status;

    @ApiModelProperty("删除标志")
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

    @ApiModelProperty("备注")
    @TableField("remark")  // 映射到 'remark' 字段
    private String remark;
}
