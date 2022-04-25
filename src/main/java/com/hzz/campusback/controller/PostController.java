package com.hzz.campusback.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.model.dto.CreateTopicDTO;
import com.hzz.campusback.model.dto.FileDTO;
import com.hzz.campusback.model.entity.Post;
import com.hzz.campusback.model.entity.User;
import com.hzz.campusback.model.vo.PostVO;
import com.hzz.campusback.service.PostService;
import com.hzz.campusback.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hzz.campusback.jwt.JwtUtil.USER_NAME;


@RestController
@RequestMapping("/post")
public class PostController extends BaseController {

    @Resource
    private PostService postService;
    @Resource
    private UserService userService;
    public final static String UPLOAD_PATH_PREFIX = "static/post/";

    /**
     * @param tab      类型：最新或最热
     * @param pageNo   页码
     * @param pageSize 一页显示多少
     * @return Page<PostVO>，Page 为 mp 提供的类；PostVO 为自定义 bean，有帖子的各种信息
     */
    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list;
        if("最新".equals(tab)) tab = "latest";
        if("热门".equals(tab)) tab = "hot";

        if (tab.equals("latest") || tab.equals("hot")) {
            list = postService.getList(new Page<>(pageNo, pageSize), tab);
        } else {
            list = postService.getListByTag(new Page<>(pageNo, pageSize), tab);
        }
        return ApiResult.success(list);

    }

    /**
     * @param userName USER_NAME 从客户端返回的 token 中取出的用户名
     * @param dto
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<Post> create(@RequestHeader(value = USER_NAME) String userName
            , @RequestBody CreateTopicDTO dto) {
        User user = userService.getUserByUsername(userName); // 根据用户名查询用户
        Post topic = postService.create(dto, user); // 创建帖子并进行各种关系设置
        return ApiResult.success(topic);
    }

    // 帖子信息
    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        Map<String, Object> map = postService.viewTopic(id);
        return ApiResult.success(map);
    }

    // 推荐
    @GetMapping("/recommend")
    public ApiResult<List<Post>> getRecommend(@RequestParam("topicId") String id) {
        List<Post> topics = postService.getRecommend(id);
        return ApiResult.success(topics);
    }

    // 编辑
    @PostMapping("/update")
    public ApiResult<Post> update(@RequestHeader(value = USER_NAME) String userName, @Valid @RequestBody Post post) {
        User user = userService.getUserByUsername(userName);
        Assert.isTrue(user.getId().equals(post.getUserId()), "非本人无权修改");
        post.setModifyTime(new Date());
        post.setContent(EmojiParser.parseToAliases(post.getContent()));
        postService.updateById(post);
        return ApiResult.success(post);
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(@RequestHeader(value = USER_NAME) String userName, @PathVariable("id") String id) {
        User user = userService.getUserByUsername(userName);
        Post byId = postService.getById(id);
        Assert.notNull(byId, "来晚一步，话题已不存在");
        Assert.isTrue(byId.getUserId().equals(user.getId()), "你为什么可以删除别人的话题？？？");
        postService.removeById(id);
        return ApiResult.success(null, "删除成功");
    }

    @ResponseBody
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public FileDTO uploadImg(HttpServletRequest request) throws IOException {
        System.out.println("got request");
        MultipartRequest multipartRequest = (MultipartRequest) request;
        MultipartFile file = multipartRequest.getFile("file[]");
        String filename = null;
        if (file != null) {
            String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
            File fileDir = new File(realPath);
            if (!fileDir.exists()) {
                //生成文件夹
                fileDir.mkdirs();
            }
            // 获取文件名，对文件名还没有优化，存在中文名等文件名问题
            filename = file.getOriginalFilename().replace(" ", "");

            //构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            file.transferTo(newFile);
        }
        String url = "http://localhost:8081/" + UPLOAD_PATH_PREFIX + filename;
        FileDTO fileDTO = new FileDTO();
        fileDTO.setMessage("上传成功");
        fileDTO.setUrl(url);
        return fileDTO;
    }
    //
    @GetMapping("/getEssenceTopic")
    public ApiResult<List<PostVO>> getEssenceTopic() {
        List<PostVO> list = postService.listEssence();

        return ApiResult.success(list);
    }
}
