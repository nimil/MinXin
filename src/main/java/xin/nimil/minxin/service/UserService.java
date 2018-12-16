package xin.nimil.minxin.service;

import xin.nimil.minxin.pojo.Users;
import xin.nimil.minxin.vo.FriendRequestVO;

import java.util.List;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/11/14
 * @Time:21:58
 */
public interface UserService {

    Users queryUserInfobyUsername(String userName);

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

    /**
     * 搜索朋友的前置条件
     * @param myUserId
     * @param friendUsername
     * @return
     */
    Integer preconditionSearchFriends(String myUserId,String friendUsername);

    void sendFriendRequest(String myUserId, String friendUsername);

    List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

}
