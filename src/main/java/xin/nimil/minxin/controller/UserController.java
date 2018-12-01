package xin.nimil.minxin.controller;


import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.nimil.minxin.bo.UsersBo;
import xin.nimil.minxin.pojo.Users;
import xin.nimil.minxin.service.UserService;
import xin.nimil.minxin.utils.FastDFSClient;
import xin.nimil.minxin.utils.FileUtils;
import xin.nimil.minxin.utils.MD5Utils;
import xin.nimil.minxin.utils.MinXinResult;
import xin.nimil.minxin.vo.UsersVO;

import javax.validation.constraints.Min;
import java.util.Objects;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/11/14
 * @Time:21:26
 */
@RequestMapping("u")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FastDFSClient fastDFSClient;

    @PostMapping("/registOrLogin")
    public MinXinResult  registOrLogin (@RequestBody Users users) throws Exception{
        if (StringUtils.isEmpty(users.getUsername())||StringUtils.isEmpty(users.getPassword())){
            return MinXinResult.errorMsg("用户名或者密码不能为空");
        }

        boolean userNameIsExsist = userService.queryUsernameIsExist(users.getUsername());

        Users userResult = null;
        if (userNameIsExsist){
            //登录
            userResult = userService.queryUserForLogin(users.getUsername(), MD5Utils.getMD5Str(users.getPassword()));

            if (Objects.isNull(userResult)){
                return MinXinResult.errorMsg("用户名或者密码不正确");
            }

        }else {
            //注册
            users.setNickname(users.getUsername());
            users.setFaceImage("");
            users.setFaceImageBig("");
            users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
            userResult = userService.saveUser(users);

        }
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userResult,usersVO);
        return MinXinResult.ok(usersVO);
    }

    @PostMapping("/uploadFaceBase64")
    public MinXinResult uploadBase64(@RequestBody UsersBo usersBo) throws Exception{
        //获取前端传来的字符串 然后转化为文件对象再上传
        String base64Data = usersBo.getFaceData();
        String userFacePath = "D:\\testfile"+usersBo.getUserId()+"userface64.png";
        FileUtils.base64ToFile(userFacePath,base64Data);
        MultipartFile multipartFile = FileUtils.fileToMultipart(userFacePath);

        String path = fastDFSClient.uploadBase64(multipartFile);
        System.err.println(path);

        //获取缩略图的url
        String thump = "_80x80.";
        String[] split = path.split("\\.");
        String thumpImgUrl = split[0]+thump+split[1];

        //更新用户头像
        Users user = new Users();

        user.setId(usersBo.getUserId());
        user.setFaceImageBig(path);
        user.setFaceImage(thumpImgUrl);

       Users users = userService.updateUserInfo(user);

        return MinXinResult.ok(users);
    }

    @GetMapping("/setNickName")
    public MinXinResult setNickName(UsersBo usersBo){
        Users user = new Users();
        user.setId(usersBo.getUserId());
        user.setNickname(usersBo.getNickName());

        Users result = userService.updateUserInfo(user);

        return MinXinResult.ok(result);
    }

}
