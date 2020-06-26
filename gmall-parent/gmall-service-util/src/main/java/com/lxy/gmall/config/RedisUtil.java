package com.lxy.gmall.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-15 11:21
 */

public class RedisUtil {
    //创建连接池
    private JedisPool jedisPool;
    //host port等参数可以配置在application.propperties中

    //初始化连接池
    //默认为0号库
    public void initJedisPool(String host,int port,int database){
        //直接创建一个连接池配置类
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //设置连接池的最大连接数
        jedisPoolConfig.setMaxTotal(200);
        //最大等待时间
        jedisPoolConfig.setMaxWaitMillis(10*1000);
        //最小剩余数量
        jedisPoolConfig.setMinIdle(10);
        //当用户获取到一个连接池之后，自检是否可以使用！
        jedisPoolConfig.setTestOnBorrow(true);
        //开启获取连接池的缓冲池
        jedisPoolConfig.setBlockWhenExhausted(true);
        //连接池配置类 host port timeout password
        jedisPool=new JedisPool(jedisPoolConfig,host,port,20*1000);
    }

    //获取jedis
    public Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

}
