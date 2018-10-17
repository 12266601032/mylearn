package com.example.algorithm.id;



public class SnowFlakeGenerator{


    private final long twepoch = 1288834974657L;
    //workerIdBits + datacenterIdBits 为工作机器的ID
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    //负数是采用二进制的补码表示 补码 = 反码 + 1 = （原码 - 1）再取反码
    //-1的二进制码为1111111111-1111111111-1111111111-1111111111-1111111111-1111111111-1111
    //因此 -1的二进制码 进行左移workerIdBits位 结果为11...1100000 然后再与-1的二进制码已经异或
    //异或的算法是相同为0 不同为1,这步操作等于是将上面的结果高位的1 全部变回0 这样就得到了11111也就是workerIdBits（<64时）个bit（<64时）能表示的最大二进制数
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    //与maxWorkerId相同，计算datacenterIdBits个位能表示的最大数
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceBits = 12L;
    //workerId在64位二进制数中的偏移位数
    private final long workerIdShift = sequenceBits;
    //datacenterId在64位二进制数中的偏移位数
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    //时间戳的偏移位数
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    //序列只有sequenceBits个位数 因此这个表示的是序列的最大值
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowFlakeGenerator(long workerId, long datacenterId) {
        /*
         * Twitter的SnowFlake算法生成id的结果是一个64bit大小的整数，它的结构为
         *      0-0000000000-0000000000-0000000000-0000000000-0000000000-0000000000-000
         *      | |                                           ||         ||           |
         *      | -—————————时间戳——————————|         | -毫秒内序列-
         *  符号位                                             ---机器ID--
         * 1位，不用。二进制中最高位为1的都是负数，但是我们生成的id一般都使用整数，所以这个最高位固定是0
         * 1~42位，41位用来记录时间戳（毫秒）。
         *      41位可以表示2^41−1个数字，
         *      如果只用来表示正整数（计算机中正数包含0），可以表示的数值范围是：0 至 241−1，减1是因为可表示的数值范围是从0开始算的，而不是1。
         *      也就是说41位可以表示241−1个毫秒的值，转化成单位年则是(241−1)/(1000∗60∗60∗24∗365)=69年
         * 43~53位，10位用来记录工作机器id。
         *      可以部署在210=1024个节点，包括5位datacenterId和5位workerId
         *      5位（bit）可以表示的最大正整数是25−1=31，即可以用0、1、2、3、....31这32个数字，来表示不同的datecenterId或workerId
         * 54~64位，12位序列号，用来记录同毫秒内产生的不同id。
         *      12位（bit）可以表示的最大正整数是212−1=4096，即可以用0、1、2、3、....4095这4096个数字，来表示同一机器同一时间截（毫秒)内产生的4096个ID序号
         */
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            //将序列值与最大序列值按照位取 & ，如果当前序列值超过了sequenceMask结果为0
            //因为sequenceMask的二进制值为0000000000-0000000000-0000000000-0000000000-0000000000-0011111111-1111
            //如果上一个序列为0000000000-0000000000-0000000000-0000000000-0000000000-0011111111-1111那么 + 1 =
            //0000000000-0000000000-0000000000-0000000000-0000000000-0100000000-0000 & 0000000000-0000000000-0000000000-0000000000-0000000000-0011111111-1111
            //结果为 0 ，这样就能控制序列永远在0~sequenceMask之间循环递增
            sequence = (sequence + 1) & sequenceMask;
            //当超出sequenceMask值后运算的sequence结果为0 说明当前毫秒内的序列用完了，等下个毫秒再取
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //如果是新的毫秒值 重新从0开始
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        //这一步是作用是将毫秒值移位到对应的位置，datacenterId、workerId移位到相应位置然后与序列三者进行或运算 合并为一个二进制数字。实现算法目的
        //|或运算为有1得1，这其中的值都限制在相应的长度，因此不属于自己的区间的对应位上的值都为0能达到合并成一个包含三者有效位的二进制数字
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowFlakeGenerator idWorker = new SnowFlakeGenerator(0, 0);
        for (int i = 0; i < 100; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }
    }

}
