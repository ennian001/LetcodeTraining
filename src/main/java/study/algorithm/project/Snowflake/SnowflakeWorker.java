package study.algorithm.project.Snowflake;

import lombok.Data;


@Data
public class SnowflakeWorker {

    public SnowflakeWorker(long workerId, long datacenterId) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**工作机器ID（0-31）**/
    private long workerId ;
    /**数据中心ID(0 - 31)**/
    private long datacenterId ;

    /**毫秒内存序列（0 - 4095）**/
    private long sequence = 0L ;

    /**开始时间戳 2015-01-01**/
    private final long twepoch  = 1420041600000L;

    /**datacenter+workerid  = 10 ，可以一个2位一个8位**/
    /**机器id所占的位数**/
    private final long workerIdBits  = 5L ;

    /**数据标识id所占的位数**/
    private final long datacenterIdBits = 5L ;

    /**支持的最大机器id，结果是31（这个位移算法可以很快的计算出几位二进制数所能表示的最大十进制数）**/
    private final long maxworkerId = -1L^( -1L << workerIdBits) ;
    /**支持的最大数据表示id ， 结果是31**/
    private final long maxDatacenterId = -1L^( -1L << datacenterIdBits ) ;
    //序列号id长度
    private long sequenceBits = 12L;
    //序列号最大值
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
    //工作id需要左移的位数，12位
    private long workerIdShift = sequenceBits;
    //数据id需要左移位数 12+5=17位
    private long datacenterIdShift = sequenceBits + workerIdBits;
    //时间戳需要左移位数 12+5+5=22位
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**上次生成的时间戳**/
    private long lastTimestamp = -1L;

    //下一个ID生成算法
    public synchronized long nextId() {
        long timestamp = timeGen();

        //获取当前时间戳如果小于上次时间戳，则表示时间戳获取出现异常
        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        //获取当前时间戳如果等于上次时间戳（同一毫秒内），则在序列号加一；否则序列号赋值为0，从0开始。
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        //将上次时间戳值刷新
        lastTimestamp = timestamp;

        /**
         * 返回结果：
         * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
         * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
         * 因为个部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
         */
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    //获取时间戳，并与上次时间戳比较
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    //获取系统时间戳
    private long timeGen(){
        return System.currentTimeMillis();
    }

    //---------------测试---------------
    public static void main(String[] args) {
        SnowflakeWorker worker = new SnowflakeWorker(1,1);
        for (int i = 0; i < 10; i++) {
            long nextId = worker.nextId();
            System.out.println(nextId);
            System.out.println(String.valueOf(nextId).length());
        }
    }
}
