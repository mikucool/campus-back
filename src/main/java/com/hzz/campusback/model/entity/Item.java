package com.hzz.campusback.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@TableName("items") // 指明当前类为数据库中的 items 表
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)    // 表示表的 id，类型是自增长
    private Integer id;

    @TableField("content")      // 表示表的 content 字段
    private String content;

    @TableField("poster")      // 表示表的 poster 字段
    private String poster;

    // 0 表示正在展示， 1 表示已经解决， 2 表示过期
    @Builder.Default
    @TableField("status")
    private int status = 0;   // 默认是 0

}
