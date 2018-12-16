package xin.nimil.minxin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestVO {

   private String sendUserId;

   private String sendUserName;

   private String sendFaceImage;

   private String sendNickName;

}