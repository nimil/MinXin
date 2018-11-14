package xin.nimil.minxin.vo;

import javax.persistence.Column;
import javax.persistence.Id;

public class UsersVO {

    private String id;

    private String username;

    private String faceImage;


    private String faceImageBig;


    private String nickname;


    private String qrcode;




    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * 获取我的头像，如果没有默认给一张
     *
     * @return face_image - 我的头像，如果没有默认给一张
     */
    public String getFaceImage() {
        return faceImage;
    }

    /**
     * 设置我的头像，如果没有默认给一张
     *
     * @param faceImage 我的头像，如果没有默认给一张
     */
    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    /**
     * @return face_image_big
     */
    public String getFaceImageBig() {
        return faceImageBig;
    }

    /**
     * @param faceImageBig
     */
    public void setFaceImageBig(String faceImageBig) {
        this.faceImageBig = faceImageBig;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取新用户注册后默认后台生成二维码，并且上传到fastdfs
     *
     * @return qrcode - 新用户注册后默认后台生成二维码，并且上传到fastdfs
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 设置新用户注册后默认后台生成二维码，并且上传到fastdfs
     *
     * @param qrcode 新用户注册后默认后台生成二维码，并且上传到fastdfs
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }


}