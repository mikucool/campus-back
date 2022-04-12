package com.hzz.campusback.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.model.entity.Follow;
import com.hzz.campusback.model.entity.Message;
import com.hzz.campusback.model.entity.User;
import com.hzz.campusback.service.FollowService;
import com.hzz.campusback.service.MessageService;
import com.hzz.campusback.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.hzz.campusback.jwt.JwtUtil.USER_NAME;

@RestController   // 将返回的 java 对象封装成 json 格式的数据
@RequestMapping("/message")
public class MessageController extends BaseController {
    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String UPLOAD_PATH_PREFIX = "static/message/";


    @Resource   // 注入
    private MessageService messageService;

    @Resource   // 注入
    private UserService userService;

    @Resource   // 注入
    private FollowService followService;

    @RequestMapping("/list")
    public ApiResult<List<Message>> getMessageList(@RequestHeader(value = USER_NAME) String username,
                                                   @RequestParam(value = "friendName") String friendName) {
        // 问题：先查发送方是自己，后查朋友，导致自己的消息将展示在前面而朋友的消息展示在后面
        List<Message> list1 = messageService.list(
                new LambdaQueryWrapper<Message>().eq(Message::getReceiveName, friendName).eq(Message::getSendName, username)
        );
        List<Message> list2 = messageService.list(
                new LambdaQueryWrapper<Message>().eq(Message::getReceiveName, username).eq(Message::getSendName, friendName)
        );

        // 解决问题：冒泡排序
        // 每一轮循环都将把最大大的数往最后一位移动
        list1.addAll(list2);
        int size = list1.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                Message message1 = list1.get(j);
                Message message2 = list1.get(j + 1);
                if (message1.getId() > message2.getId()) {
                    list1.set(j, message2);
                    list1.set(j + 1, message1);
                }
            }
        }

        return ApiResult.success(list1);
    }

    @PostMapping("/addMessage")
    public ApiResult<Boolean> addMessage(@RequestBody Message message) {

        Message addMessage = Message.builder()
                .sendName(message.getSendName())
                .sendId(message.getSendId())
                .sendAvatar(message.getSendAvatar())
                .receiveName(message.getReceiveName())
                .receiveId(message.getReceiveId())
                .receiveAvatar(message.getReceiveAvatar())
                .content(message.getContent())
                .build();
        boolean save = messageService.save(addMessage);
        return ApiResult.success(save);
    }

    @GetMapping("/listContact")
    public ApiResult<List<Message>> listContact(@RequestHeader(value = USER_NAME) String username) { // 获取联系人

        List<Message> listContact = new ArrayList<>();
        // 获取当前用户 id
        List<User> currentUsers = userService.list(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        User currentUser = currentUsers.get(0);
        String id = currentUser.getId();
        // 查询当前用户的关注的用户
        List<Follow> list = followService.list(new LambdaQueryWrapper<Follow>().eq(Follow::getFollowerId, id));
        String parentId = "";
        for (Follow follow : list) {
            // 查询与每个关注的用户聊天信息
            parentId = follow.getParentId();
            Message message = messageService.selectByParentId(parentId);
            // 如果消息不为空则添加到集合
            if (message != null) {
                listContact.add(message);
            }
            // 如果消息表为空说明该用户没有和该用户互相发过消息，需要构建消息
            if (message == null) {
                List<User> users = userService.list(new LambdaQueryWrapper<User>().eq(User::getId, parentId));
                User followed = users.get(0);   // 只存在这一个对象
                Message addMessage = Message.builder()
                        .sendName(followed.getUsername())
                        .sendId(followed.getId())
                        .sendAvatar(followed.getAvatar())
                        .receiveName(currentUser.getUsername())
                        .receiveId(currentUser.getId())
                        .receiveAvatar(currentUser.getAvatar())
                        .content("你已关注我，开始聊天吧")
                        .build();
                listContact.add(addMessage);
                messageService.save(addMessage); // 保存到记录中
            }
        }
        return ApiResult.success(listContact);
    }

    @PostMapping(value = "/uploadFile")
    public ApiResult<Boolean> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (file != null) {
            String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
            File fileDir = new File(realPath);
            if (!fileDir.exists()) {
                //生成文件夹
                fileDir.mkdirs();
            }
            // 获取文件名，对文件名还没有优化，存在中文名等文件名问题
            String filename = file.getOriginalFilename().replace(" ", "");

            //构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            file.transferTo(newFile);

            return ApiResult.success(true);
        }
        return ApiResult.success(false);
    }
}
