package xin.nimil.minxin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/12/31
 * @Time:19:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFriendsVO {

    private String friendUserId;

    private String friendUserName;

    private String friendFaceImage;

    private String friendNickName;
}
