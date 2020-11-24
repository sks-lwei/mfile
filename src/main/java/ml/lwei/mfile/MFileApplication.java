package ml.lwei.mfile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author lwei
 */
@EnableAsync
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("ml.lwei.mfile.mapper")
public class MFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(MFileApplication.class, args);
    }

}
