package com.yeahmobi.vncctest.dao;

import com.google.common.collect.Maps;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * Created by steven.liu on 2016/6/23.
 */
public class RedisDao {


    private Map<String, String> redisHostMap = Maps.newHashMap();
    private Map<String, Integer> redisPortMap = Maps.newHashMap();
    private Map<String, String> dataNamespaceMap = Maps.newHashMap();

    private Map<String, JedisPool> jedisPoolMap = Maps.newHashMap();

//    public RedisDao(){
//        redisHostMap.put("qa","172.30.10.174");
//        redisHostMap.put("dev","172.30.10.146");
//        redisHostMap.put("ali","10.7.0.2");
//
//        redisPortMap.put("qa", 6379);
//        redisPortMap.put("dev", 6379);
//        redisPortMap.put("ali", 9006);
//
//        dataNamespaceMap.put("offer","o");
//        dataNamespaceMap.put("affiliate","a");
//        dataNamespaceMap.put("app","ap");
//        dataNamespaceMap.put("personalizedOffer","po");
//        dataNamespaceMap.put("AppOffer","ao");
//    }

    public JedisPool getJedisPool(String host, int port) {

        JedisPoolConfig config;
        config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(20);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(false);

        JedisPool pool = new JedisPool(config, host, port);
        return pool;
    }


    public String getData(String id, String type, String environment){
        JedisPool pool;

        if(jedisPoolMap.keySet().size()==0||!jedisPoolMap.keySet().contains(type)){
            pool = getJedisPool(redisHostMap.get(environment), redisPortMap.get(environment));
            jedisPoolMap.put(type, pool);
        }else{
            pool = jedisPoolMap.get(type);
        }

        Jedis jedis = pool.getResource();
        String data = jedis.get("CACHE:" + dataNamespaceMap.get(type) + ":" + id);

        return data;
    }


    public Map<String, String> getRedisHostMap() {
        return redisHostMap;
    }

    public void setRedisHostMap(Map<String, String> redisHostMap) {
        this.redisHostMap = redisHostMap;
    }

    public Map<String, Integer> getRedisPortMap() {
        return redisPortMap;
    }

    public void setRedisPortMap(Map<String, Integer> redisPortMap) {
        this.redisPortMap = redisPortMap;
    }

    public Map<String, String> getDataNamespaceMap() {
        return dataNamespaceMap;
    }

    public void setDataNamespaceMap(Map<String, String> dataNamespaceMap) {
        this.dataNamespaceMap = dataNamespaceMap;
    }
}
