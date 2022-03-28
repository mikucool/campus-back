package com.hzz.campusback.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@TableName("promotion") // 指明当前类为数据库中的 billboard 表
@NoArgsConstructor
@AllArgsConstructor
public class Promotion implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)    // 表示表的 id，类型是自增长
    private Integer id;

    @TableField("title")      // 表示表的 title 字段
    private String title;

    @TableField("link")      // 表示表的 link 字段
    private String link;

    @TableField("description")      // 表示表的 description 字段
    private String description;

}
