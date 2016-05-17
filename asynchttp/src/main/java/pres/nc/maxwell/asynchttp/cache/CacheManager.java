package pres.nc.maxwell.asynchttp.cache;

import android.content.Context;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pres.nc.maxwell.asynchttp.cache.impl.DiskCache;
import pres.nc.maxwell.asynchttp.cache.impl.MemoryCache;

/**
 * 缓存管理
 */
public class CacheManager {

    private final Context context;

    /**
     * 缓存时间
     */
    private final long cacheTime;

    public CacheManager(Context context, long cacheTime) {
        this.context = context;
        this.cacheTime = cacheTime;
    }

    /**
     * 获得32位的MD5摘要值
     *
     * @param content 要计算的文本内容
     * @return MD5值
     */
    private static String getMD5String(String content) {
        byte[] digestBytes = null;
        try {
            digestBytes = MessageDigest.getInstance("md5").digest(
                    content.getBytes());
        } catch (NoSuchAlgorithmException e) {
            //can not reach
        }
        String md5Code = new BigInteger(1, digestBytes).toString(16);
        //补全不足位数
        for (int i = 0; i < 32 - md5Code.length(); i++) {
            md5Code = "0" + md5Code;
        }
        return md5Code;
    }

    public String getCache(String key) {
        key = getMD5String(key);
        String cache;

        //读取内存缓存
        String memCache = MemoryCache.getInstance().getCache(key);
        if (memCache==null){//读取本地缓存
            String diskCache = new DiskCache(context,cacheTime).getCache(key);
            cache = diskCache;
        }else {
            cache = memCache;
        }

        return cache;
    }

    public void setCache(String key, String cache) {
        key = getMD5String(key);
        MemoryCache.getInstance().setCache(key,cache);
        new DiskCache(context,cacheTime).setCache(key,cache);
    }

}
