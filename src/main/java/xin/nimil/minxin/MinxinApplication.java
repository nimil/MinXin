package xin.nimil.minxin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "xin.nimil.minxin.mapper")
@ComponentScan(basePackages = {"xin.nimil","org.n3r.idworker"})
public class MinxinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinxinApplication.class, args);
    }
}
