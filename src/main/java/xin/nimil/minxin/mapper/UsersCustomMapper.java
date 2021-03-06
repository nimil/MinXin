package xin.nimil.minxin.mapper;

import xin.nimil.minxin.pojo.Users;
import xin.nimil.minxin.utils.MyMapper;
import xin.nimil.minxin.vo.FriendRequestVO;
import xin.nimil.minxin.vo.MyFriendsVO;

import java.util.List;

public interface UsersCustomMapper extends MyMapper<Users> {

    List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    List<MyFriendsVO> queryMyFriends(String userId);
}