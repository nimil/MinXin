package xin.nimil.minxin.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/12/1
 * @Time:13:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersBo {
    private String userId;
    private String faceData;
    private String nickName;
}
