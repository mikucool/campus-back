package com.hzz.campusback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzz.campusback.model.entity.Post;
import com.hzz.campusback.model.vo.PostVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicMapper extends BaseMapper<Post> {
    /**
     * 分页查询首页话题列表，由于传入了 Page 对象，mybatisPlus 自动完成分页查询
     * <p>
     *
     * @param page  Page 对象
     * @param tab   最新或最热
     * @return
     */
    Page<PostVO> selectListAndPage(@Param("page") Page<PostVO> page, @Param("tab") String tab);

    /**
     * 查询返回推荐的帖子
     * @param id
     * @return
     */
    List<Post> selectRecommend(@Param("id") String id);

    Page<PostVO> searchByKey(@Param("page") Page<PostVO> page, @Param("keyword") String keyword);

    /**
     * 根据标签封装 VO
     * @param page  page 对象
     * @return
     */
    Page<PostVO> selectPages(@Param("page") Page<PostVO> page);

    List<PostVO> selectListVO();

    List<PostVO> listEssence();
}
