package pres.nc.maxwell.asynchttp.cache.impl;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import pres.nc.maxwell.asynchttp.cache.ICache;
import pres.nc.maxwell.asynchttp.io.IOUtils;

/**
 * 本地缓存
 */
public class DiskCache implements ICache {

    private final Context context;

    /**
     * 缓存时间
     */
    private final long cacheTime;

    public DiskCache(Context context, long cacheTime) {
        this.context = context;
        this.cacheTime = cacheTime;
    }

    @Override
    public String getCache(String key) {
        File file = new File(getLocalCachePath(), key);

        if (!file.exists()) {
            return null;
        }

        long modiTime = file.lastModified();

        //缓存经历时间
        long cacheSpend = System.currentTimeMillis() - modiTime;

        if (cacheSpend > cacheTime) {//失效
            return null;
        }

        return IOUtils.loadFileToString(file);
    }

    @Override
    public void setCache(String key, String cache) {
        IOUtils.writeStrToFile(getLocalCachePath() + "/" + key, cache);
    }

    /**
     * 获取本地缓存位置
     */
    public String getLocalCachePath() {

        // sdcard位置
        String cachePath = null;

        // SD存在则使用SD缓存
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {

            File externalCacheDir = context.getExternalCacheDir();
            if (externalCacheDir != null) {
                cachePath = externalCacheDir.getPath();
            }
        }

        if (cachePath == null) {
            cachePath = context.getCacheDir().getPath();
        }

        // 如果文件夹不存在, 创建文件夹
        File dirFile = new File(cachePath);

        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        return cachePath;
    }
}
