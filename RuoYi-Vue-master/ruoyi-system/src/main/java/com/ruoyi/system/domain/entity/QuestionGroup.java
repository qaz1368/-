package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (question_group)实体类
 *
 * @author makejava
 * @since 2024-03-30 19:33:35
 */
@Data
@TableName(value = "question_group")
public class QuestionGroup implements Serializable {
    private static final long serialVersionUID = -98627008474424087L;
    /**
     * 用戶組id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 组名
     */
    private String groupName;
    /**
     * 分组中的用户
     */
    private String questionIdList;
    /**
     * 创建分组用户id
     */
    private Long userId;
    /**
     * 分数集合
     */
    private String score;
    /**
     * 公告
     */
    private String announcement;
    /**
     * 说明
     */
    private String explanation;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 是否逻辑删除
     */
    private Integer isDelete;
    /**
     * 用户分组id(text类型）可为多值
     */
    private String userGroupIdList;
    /**
     *  开始时间
     */
    private Date actionTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 开放状态(0未开放，1已开放）
     */
    private Integer status;
    /**
     * 限制考试时间
     */
    private Long limitTime;

    /**
     * 最后访问时间
     */
    private Date latestTime;

}

