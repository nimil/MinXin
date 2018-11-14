package xin.nimil.minxin.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import xin.nimil.minxin.mapper.UsersMapper;
import xin.nimil.minxin.pojo.Users;
import xin.nimil.minxin.service.UserService;

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
    private Sid sid;

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
        //TODO 为每个用户生成一个唯一的二维码
        users.setQrcode("");
        users.setId(sid.nextShort());
        usersMapper.insert(users);
        return users;
    }


}
