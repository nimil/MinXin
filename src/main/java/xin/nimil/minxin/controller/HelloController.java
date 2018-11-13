package xin.nimil.minxin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/11/13
 * @Time:22:19
 */
@RestController
public class HelloController {

    @GetMapping("/testhello")
    public String hellow(){
        return "hello World";
    }

}
