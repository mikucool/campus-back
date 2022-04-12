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
import java.util.Date;


@Data
@Builder
@TableName("message")
@Accessors(chain = true)
@AllArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = 3257790983905872243L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("content")
    private String content;

    @TableField("send_id")
    private String sendId;

    @TableField("send_name")
    private String sendName;

    @TableField("receive_id")
    private String receiveId;

    @TableField("receive_name")
    private String receiveName;

    @TableField("send_avatar")
    private String sendAvatar;

    @TableField("receive_avatar")
    private String receiveAvatar;

    @TableField("is_read")
    @Builder.Default
    private boolean isRead = false;


    @TableField("create_time")
    private Date createTime;
}
