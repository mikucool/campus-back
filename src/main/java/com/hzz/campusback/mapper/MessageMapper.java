package com.hzz.campusback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzz.campusback.model.entity.Billboard;
import com.hzz.campusback.model.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper extends BaseMapper<Message> {
    Message selectByParentId(@Param("ParentId") String ParentId);
}
