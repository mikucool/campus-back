package com.hzz.campusback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzz.campusback.model.dto.CommentDTO;
import com.hzz.campusback.model.entity.Comment;
import com.hzz.campusback.model.entity.User;
import com.hzz.campusback.model.vo.CommentVO;

import java.util.List;


public interface CommentService extends IService<Comment> {
    /**
     *
     *
     * @param topicid
     * @return {@link Comment}
     */
    List<CommentVO> getCommentsByTopicID(String topicid);

    Comment create(CommentDTO dto, User user);
}
