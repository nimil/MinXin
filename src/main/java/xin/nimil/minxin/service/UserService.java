package xin.nimil.minxin.service;

import xin.nimil.minxin.pojo.Users;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/11/14
 * @Time:21:58
 */
public interface UserService {

    /**
     * 判断用户是否存在
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 查询用户登录功能
     * @param username
     * @param pwd
     * @return
     */
    Users queryUserForLogin(String username,String pwd);

    /**
     * 用户注册
     * @param users
     * @return
     */
    Users saveUser(Users users);


    Users updateUserInfo(Users users);

}
