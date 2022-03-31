package com.hzz.campusback.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Builder
@TableName("tag")
@Accessors(chain = true)
@AllArgsConstructor
public class Tag implements Serializable {
    private static final long serialVersionUID = 3257790983905872243L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("name")
    private String name;
    /**
     * 当前标签下的话题个数
     */
    @TableField("topic_count")
    @Builder.Default
    private Integer topicCount = 1;
}
