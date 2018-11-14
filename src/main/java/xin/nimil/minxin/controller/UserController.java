package xin.nimil.minxin.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.nimil.minxin.pojo.Users;
import xin.nimil.minxin.service.UserService;
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

}
