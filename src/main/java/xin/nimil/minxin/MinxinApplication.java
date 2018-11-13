package xin.nimil.minxin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "xin.nimil.minxin.mapper")
public class MinxinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinxinApplication.class, args);
    }
}
