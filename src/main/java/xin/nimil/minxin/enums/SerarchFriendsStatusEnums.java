package xin.nimil.minxin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/12/16
 * @Time:10:44
 */
@Getter
@AllArgsConstructor
public enum SerarchFriendsStatusEnums {

    SUCCESS(0,"OK"),
    USER_NOT_EXIST(1,"无此用户"),
    NOT_YOURSELF(2,"不能添加你自己"),
    ALREADY_FRIENDS(3,"该用户已经是你的好友。。。");


    private final Integer status;
    private final String msg;

    public static String getMsgByKey(Integer status) {
        for (SerarchFriendsStatusEnums type : SerarchFriendsStatusEnums.values()) {
            if (Objects.equals(type.getStatus(),status)) {
                return type.msg;
            }
        }
        return null;
    }

}
