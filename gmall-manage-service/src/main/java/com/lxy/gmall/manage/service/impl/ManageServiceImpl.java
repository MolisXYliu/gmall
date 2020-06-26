package com.lxy.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.lxy.gmall.bean.*;
import com.lxy.gmall.config.RedisUtil;
import com.lxy.gmall.manage.constant.ManageConst;
import com.lxy.gmall.manage.mapper.*;
import com.lxy.gmall.service.ManageService;
import lombok.experimental.PackagePrivate;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-11 21:31
 */

@Service
public class ManageServiceImpl implements ManageService {

    //调用mapper
    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;


    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
     private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private RedisUtil redisUtil;




    @Override
    public List<BaseCatalog1> getCatalog1() {
        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        //select * from baseCatalog2 where catalog1Id=?
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        return baseCatalog3Mapper.select(baseCatalog3);
    }

    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {
       /* BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        return baseAttrInfoMapper.select(baseAttrInfo);*/
        //做多表关联查询
        return baseAttrInfoMapper.getBaseAttrInfoListByCatalog3Id(catalog3Id);
    }

    @Transactional
    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //修改的操作
        if(baseAttrInfo.getId()!=null || baseAttrInfo.getId().length()>0){
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else{
            //保存数据baseAttrInfo
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        //baseAttrValue操作
        //清空数据的条件 attrId为依据
        //删除语句
        BaseAttrValue baseAttrValueDel = new BaseAttrValue();
        baseAttrValueDel.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValueDel);
        //保存数据baseAttrValue
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if(attrValueList!=null && attrValueList.size()>0){
            //循环判断
            for (BaseAttrValue baseAttrValue : attrValueList) {
                //private String valueName 前台传递
                //privaye String attrId=baseAttrInfo.getId()
                //前提条件baseAttrInfo对象种的主键必须能获取到自增的值
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insertSelective(baseAttrValue);
            }
        }


    }

    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        //
        List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.select(baseAttrValue);
        return baseAttrValues;
    }

    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        //baseAttrInfo.id=attrId=baseAttrValue.getAttrId()
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);
        //需要将平台属性值集合放入平台属性中
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        //
        List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.select(baseAttrValue);

        baseAttrInfo.setAttrValueList(baseAttrValues);
        return baseAttrInfo;
    }

    @Override
    public List<SpuInfo> getSpuList(SpuInfo spuInfo) {
        List<SpuInfo> spuInfos = spuInfoMapper.select(spuInfo);

        return spuInfos;
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    @Override
    @Transactional
    public void saveSpuInfo(SpuInfo spuInfo) {
        //保存数据
        //spuInfo
        //spuImage
        //spuSaleAttr
        //spuSaleAttrValue
        spuInfoMapper.insertSelective(spuInfo);
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if(spuImageList!=null && spuImageList.size()>0){
            for (SpuImage spuImage : spuImageList) {
                //这种spuId
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insertSelective(spuImage);
            }
        }

        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if(spuSaleAttrList!=null && spuSaleAttrList.size()>0){
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insertSelective(spuSaleAttr);

                //spuSaleAttrValue
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                if(spuSaleAttrValueList!=null && spuSaleAttrValueList.size()>0){
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                    }
                }

            }
        }


    }

    @Override
    public List<SpuImage> getSpuImageList(SpuImage spuImage) {
        return spuImageMapper.select(spuImage);
    }

    @Override
    public List<SpuSaleAttr> getSpuAttrList(String spuId) {
        //mapper
        //涉及两张表关联查询
        List<SpuSaleAttr>spuSaleAttrList=spuSaleAttrMapper.selectSpuSaleAttrList(spuId);
        return spuSaleAttrList;
    }

    @Override
    @Transactional
    public void saveSkuInfo(SkuInfo skuInfo) {
        //skuInfo
        skuInfoMapper.insertSelective(skuInfo);
        //skuImage
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if(skuImageList!=null && skuImageList.size()>0){
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insertSelective(skuImage);
            }
        }
        //skuAttrValue
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if(skuAttrValueList!=null && skuAttrValueList.size()>0 ){
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());

                skuAttrValueMapper.insertSelective(skuAttrValue);
            }
        }

        //skuSaleAttrValue
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        if(skuSaleAttrValueList!=null && skuSaleAttrValueList.size()>0){
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                skuSaleAttrValue.setSkuId(skuInfo.getId());
                skuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
            }
        }
    }

    @Override
    public SkuInfo getSkuInfo(String skuId) {
  /*      Jedis jedis = redisUtil.getJedis();
        jedis.set("ok", "没毛病");
        jedis.close();*/

        //1获取jedis
/*        Jedis jedis = null;
        SkuInfo skuInfo=null;*/
/*        try {
            jedis = redisUtil.getJedis();
            //涉及redis，必须注意使用哪种数据类型来存储数据
            *//**
             * redis的五种数据类型使用场景！
             * string  短信验证码 ，存储一个变量
             * hash    json字符串(对象转换的字符串)
             *   hset(key,field,value)
             *   hget(key,field)
             * list    lpush pop队列hi用
             * set     去重 交集，并集。。。不重复！
             * zset    评分 排序
             *//*
            //获取缓存中的数据
            //定义key sku:skuId:skuinfo
            String skuKey= ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKUKEY_SUFFIX;
            //判断redis中是否有key
            if(jedis.exists(skuKey)){
                //取得key中的value
                String skuJson = jedis.get(skuKey);
                //将字符串转换为对象
                skuInfo = JSON.parseObject(skuJson, SkuInfo.class);
                return skuInfo;
            }else{
                skuInfo = getSkuInfoDb(skuId);
                //放入redis
                jedis.setex(skuKey, ManageConst.SKUKEY_TIMEOUT, JSON.toJSONString(skuInfo));
                return skuInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return getSkuInfoDb(skuId);*/
        //判断缓存中是否有数据，如果有，从缓存中获取，没有从db中获取并将数据放入缓存！
        //代码优化
       /* 问题：1.如果redis宕机了如何处理 高可用 集群
          问题：2.如果在高并发的情况如何防止。。
                缓存击穿
                      缓存中的某一个key失效此时会导致大量用户访问数据库，会造成缓存击穿
                      加锁
                          ：分布式锁：
                            redis setnx setex。
                                  set(key,value,nx,px,timeout)
                                  set k1 v1 px 10000 nx
                                  使用key1当作锁！
                            redission：分布式锁

                缓存雪崩
                      缓存中所有key全部失效！此时会给数据库造成压力
                      将缓存中的key设置的过期时间不一致
                缓存穿透：
                      用户查询一个根本不存在的数据，此时会造成穿透
                      set(key,null)*/
        //return getSkuInfoJedis(skuId);

        return getSkuInfoRedission(skuId);


    }

    private SkuInfo getSkuInfoRedission(String skuId) {
        Config config = new Config();
        /*config.useClusterServers()
                .addNodeAddress("redis://192.168.119.133:6379");*/
        config.useSingleServer().setAddress("redis://192.168.119.133:6379");
        RedissonClient redissonClient = Redisson.create(config);
        //使用redission调用getLock
        RLock lock = redissonClient.getLock("myLock");
        //加锁
        lock.lock(10, TimeUnit.SECONDS);
        //放入业务逻辑代码
        Jedis jedis = null;
        SkuInfo skuInfo=null;
        try {
            jedis = redisUtil.getJedis();

            String skuKey= ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKUKEY_SUFFIX;
            //判断redis中是否有key
            if(jedis.exists(skuKey)){
                //取得key中的value
                String skuJson = jedis.get(skuKey);
                //将字符串转换为对象
                skuInfo = JSON.parseObject(skuJson, SkuInfo.class);
                return skuInfo;
            }else{
                skuInfo = getSkuInfoDb(skuId);
                //放入redis
                jedis.setex(skuKey, ManageConst.SKUKEY_TIMEOUT, JSON.toJSONString(skuInfo));
                return skuInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis!=null){
                jedis.close();
            }
            //解锁
            lock.unlock();
        }
        return getSkuInfoDb(skuId);
    }

    private SkuInfo getSkuInfoJedis(String skuId) {
        Jedis jedis = null;
        SkuInfo skuInfo=null;

        try {
            jedis = redisUtil.getJedis();
            String skuKey= ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKUKEY_SUFFIX;
            //获取数据
            String skuJson = jedis.get(skuKey);
            if (skuJson==null || skuJson.length()==0) {//没有数据
                //试着加锁
                System.out.println("缓存中没有数据！");
                //执行set命令
                //定义上锁的key sku:skuId:lock
                String skuLockKey=ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKULOCK_SUFFIX;
                String lockKey = jedis.set(skuLockKey, "good", "NX", "PX", ManageConst.SKULOCK_EXPIRE_PX);
                if("OK".equals(lockKey)){
                    //此时加锁成功
                    skuInfo = getSkuInfoDb(skuId);
                    //放入redis
                    jedis.setex(skuKey, ManageConst.SKUKEY_TIMEOUT, JSON.toJSONString(skuInfo));
                    //删除锁
                    jedis.del(skuLockKey);
                    return skuInfo;
                }else{
                    //等待
                    Thread.sleep(1000);
                    //调用
                    return getSkuInfo(skuId);
                }

            }else {
                skuInfo = JSON.parseObject(skuJson, SkuInfo.class);
                return skuInfo;
            }
        } catch (Exception e) {
                    e.printStackTrace();
                } finally {
            if(jedis!=null){
                jedis.close();
            }
                }
        return getSkuInfoDb(skuId);
    }

    private SkuInfo getSkuInfoDb(String skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);

        skuInfo.setSkuImageList(getSkuImageBySkuId(skuId));
        //查询平台属性值
        SkuAttrValue skuAttrValue = new SkuAttrValue();
        skuAttrValue.setSkuId(skuId);

        skuInfo.setSkuAttrValueList(skuAttrValueMapper.select(skuAttrValue));
        return skuInfo;
    }

    @Override
    public List<SkuImage> getSkuImageBySkuId(String skuId) {
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuId);
        List<SkuImage> skuImageList = skuImageMapper.select(skuImage);
        return skuImageList;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(SkuInfo skuInfo) {
        //
        return spuSaleAttrMapper.selectSpuSaleAttrListCheckBySku(skuInfo.getId(),skuInfo.getSpuId());

    }

    @Override
    public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId) {
        //根据spuId查询数据
        return skuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);
    }

    @Override
    public List<BaseAttrInfo> getAttrList(List<String> attrValueIdList) {
        //先把集合转成字符串
        String valuIds = StringUtils.join(attrValueIdList.toArray(), ",");
        System.out.println("valuIds= "+valuIds);
        return baseAttrInfoMapper.selectAttrInfoListByIds(valuIds);

    }
}
