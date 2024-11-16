package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "exam_submit")
public class ExamSubmit {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 编程语言
     */
    private String language;
    /**
     * 用户代码
     */
    private String code;
    /**
     * 考试 id
     */
    private Long examId;
    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 题目 id
     */
    private Double score;
    /**
     * 最终提交时间
     */
    private Date updateTime;
}
