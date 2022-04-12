package com.hzz.campusback.model.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class MessageDTO implements Serializable {
    private static final long serialVersionUID = -5957433707110390852L;
    private String sender_Id;
    private String receiver_Id;
}
