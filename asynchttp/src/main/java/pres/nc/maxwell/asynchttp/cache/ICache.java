package pres.nc.maxwell.asynchttp.cache;

/**
 * 缓存实现接口
 */
public interface ICache {

    /**
     * 获取缓存
     */
    String getCache(String key);

    /**
     * 保存缓存
     */
    void setCache(String key, String cache);

}
