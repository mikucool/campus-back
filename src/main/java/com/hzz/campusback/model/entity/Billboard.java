package com.hzz.campusback.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@TableName("billboard") // 指明当前类为数据库中的 billboard 表
@NoArgsConstructor
@AllArgsConstructor
public class Billboard implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)    // 表示表的 id，类型是自增长
    private Integer id;

    @TableField("description")      // 表示表的 description 字段
    private String description;

    @TableField(value = "create_time", fill = FieldFill.INSERT) // 创建时自动使用当前的系统时间
    private Date createTime;

    @Builder.Default
    @TableField("`show`")
    private boolean show = false;   // 默认是 0

}
