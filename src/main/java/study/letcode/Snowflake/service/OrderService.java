package study.letcode.Snowflake.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    IDGeneratorSnowflake generatorSnowflake  ;

    public String getIDBySnowFlake() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 20; i++) {
            executorService.submit(
                    ()->{
                        System.out.println(generatorSnowflake.snowflakeId());
                    }
            );
        }
        return "hello snowflake";
    }
}
