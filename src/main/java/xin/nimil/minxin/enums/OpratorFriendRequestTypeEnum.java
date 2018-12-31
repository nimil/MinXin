package xin.nimil.minxin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/12/23
 * @Time:15:09
 */
@Getter
@AllArgsConstructor
public enum OpratorFriendRequestTypeEnum {

    IGNORE(0,"忽略"),
    PASS(1,"通过");

    private final Integer type;
    private final String msg;


    public static String getMsgByKey(Integer status) {
        for (OpratorFriendRequestTypeEnum type : OpratorFriendRequestTypeEnum.values()) {
            if (Objects.equals(type.getType(),status)) {
                return type.msg;
            }
        }
        return null;
    }

}
