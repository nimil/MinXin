package xin.nimil.minxin.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import xin.nimil.minxin.mapper.UsersMapper;
import xin.nimil.minxin.pojo.Users;
import xin.nimil.minxin.service.UserService;
import xin.nimil.minxin.utils.FastDFSClient;
import xin.nimil.minxin.utils.FileUtils;
import xin.nimil.minxin.utils.QRCodeUtils;

import java.io.IOException;
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
