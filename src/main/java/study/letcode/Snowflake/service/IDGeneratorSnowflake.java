package study.letcode.Snowflake.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class IDGeneratorSnowflake {

    private long workId = 0 ;

    private long datacenterId = 1 ;

    private Snowflake snowflake  = IdUtil.createSnowflake(workId , datacenterId) ;

    /**
     *  构造器完成后开始执行 ， 执行一些初始化操作
     */
    @PostConstruct
    public void init(){
        try {
            workId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器ip:{}",NetUtil.getLocalhostStr());
            log.info("当前机器的workerId:{}",workId);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("当前机器的workerId获取失败{}",e);
            workId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    public synchronized  long snowflakeId(){
        return snowflake.nextId() ;
    }

    public synchronized long snowflakeId(long workId , long datacenterId){
        Snowflake snowflake = IdUtil.createSnowflake(workId, datacenterId) ;

        return snowflake.nextId() ;
    }

    public static void main(String[] args) {
        //1380751668588707840
        //1380751728126853120
        //1380751728131047424
        System.out.println(new IDGeneratorSnowflake().snowflakeId());
        System.out.println(new IDGeneratorSnowflake().snowflakeId());
        System.out.println(new IDGeneratorSnowflake().snowflakeId());
        System.out.println(new IDGeneratorSnowflake().snowflakeId());
        System.out.println(new IDGeneratorSnowflake().snowflakeId());
        System.out.println(new IDGeneratorSnowflake().snowflakeId());
    }
}
