package xin.nimil.minxin.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import xin.nimil.minxin.enums.SerarchFriendsStatusEnums;
import xin.nimil.minxin.mapper.FriendsRequestMapper;
import xin.nimil.minxin.mapper.MyFriendsMapper;
import xin.nimil.minxin.mapper.UsersCustomMapper;
import xin.nimil.minxin.mapper.UsersMapper;
import xin.nimil.minxin.pojo.FriendsRequest;
import xin.nimil.minxin.pojo.MyFriends;
import xin.nimil.minxin.pojo.Users;
import xin.nimil.minxin.service.UserService;
import xin.nimil.minxin.utils.FastDFSClient;
import xin.nimil.minxin.utils.FileUtils;
import xin.nimil.minxin.utils.QRCodeUtils;
import xin.nimil.minxin.vo.FriendRequestVO;
import xin.nimil.minxin.vo.MyFriendsVO;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/11/14
 * @Time:21:59
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;

    @Autowired
    private UsersCustomMapper usersCustomMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Users updateUserInfo(Users users) {
        usersMapper.updateByPrimaryKeySelective(users);
        return queryUserByid(users.getId());
    }

    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUsername) {
        Users users = queryUserInfobyUsername(friendUsername);
        if (Objects.isNull(users)){
            return SerarchFriendsStatusEnums.USER_NOT_EXIST.getStatus();
        }

        if (Objects.equals(users.getId(),myUserId)){
            return SerarchFriendsStatusEnums.NOT_YOURSELF.getStatus();
        }

        Example example = new Example(MyFriends.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("myUserId",myUserId);
        criteria.andEqualTo("myFriendUserId", users.getId());
        MyFriends myFriends = myFriendsMapper.selectOneByExample(example);
        if (!Objects.isNull(myFriends)){
            return SerarchFriendsStatusEnums.ALREADY_FRIENDS.getStatus();
        }

        return SerarchFriendsStatusEnums.SUCCESS.getStatus();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendFriendRequest(String myUserId, String friendUsername) {
        Users friend = queryUserInfobyUsername(friendUsername);

        //1.查询响应的好友请求记录表
        Example example = new Example(FriendsRequest.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sendUserId",myUserId);
        criteria.andEqualTo("acceptUserId",friend.getId());
        FriendsRequest friendsRequest = friendsRequestMapper.selectOneByExample(example);
        if (Objects.isNull(friendsRequest)){
            String requestId = sid.nextShort();
            FriendsRequest friendsRequest1= new FriendsRequest();
            friendsRequest1.setId(requestId);
            friendsRequest1.setAcceptUserId(friend.getId());
            friendsRequest1.setSendUserId(myUserId);
            friendsRequest1.setRequestDateTime(new Date());
            friendsRequestMapper.insert(friendsRequest1);
        }
    }

    @Override
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId) {
        return usersCustomMapper.queryFriendRequestList(acceptUserId);
    }

    @Override
    public void removeFriendsRequest(String sendUserId, String acceptUserId) {
        Example example = new Example(FriendsRequest.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sendUserId",sendUserId);
        criteria.andEqualTo("acceptUserId",acceptUserId);
        friendsRequestMapper.deleteByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void passFriendsRequest(String sendUserId, String acceptUserId) {
        saveFriends(sendUserId,acceptUserId);
        saveFriends(acceptUserId,sendUserId);
        removeFriendsRequest(sendUserId,acceptUserId);
    }

    @Override
    public List<MyFriendsVO> queryMyFriends(String userId) {
        List<MyFriendsVO> myFriendsVOS = usersCustomMapper.queryMyFriends(userId);
        return myFriendsVOS;
    }


    private void saveFriends(String sendUserId,String acceptUserId){
        MyFriends myFriends = new MyFriends();
        myFriends.setId(sid.nextShort());
        myFriends.setMyFriendUserId(acceptUserId);
        myFriends.setMyUserId(sendUserId);
        myFriendsMapper.insert(myFriends);
    }


    @Override
    public Users queryUserInfobyUsername(String userName){
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",userName);
        Users users = usersMapper.selectOneByExample(example);
        return users;
    }


    private Users queryUserByid(String userid){
      return   usersMapper.selectByPrimaryKey(userid);
    }

    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setUsername(username);

        Users users = usersMapper.selectOne(user);

        if (Objects.isNull(users)){
        return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Users queryUserForLogin(String username, String pwd) {

        Example userExample = new Example(Users.class);

        Example.Criteria criteria = userExample.createCriteria();

        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",pwd);

        Users users = usersMapper.selectOneByExample(userExample);

        return users;
    }

    @Override
    public Users saveUser(Users users) {
        String userId = sid.nextShort();
        String qrCodePath =  "D://testfile"+userId+"qrcode.png";
        qrCodeUtils.createQRCode(qrCodePath,"minxin_qrcode"+ users.getUsername());
        MultipartFile multipartFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodePaht = "";
        try {
           qrCodePaht = fastDFSClient.uploadQRCode(multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        users.setQrcode(qrCodePaht);
        users.setId(userId);
        usersMapper.insert(users);
        return users;
    }


}
