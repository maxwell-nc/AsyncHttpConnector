package pres.nc.maxwell.asynchttp.callback.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件回调
 */
public abstract class FileCallback extends AbsCallback<File> {

    private final String fullPath;

    public FileCallback(String fullPath) {
        this.fullPath = fullPath;
    }

    @Override
    public File parseResponseStream(InputStream is) throws Exception {

        File file = new File(fullPath);
        FileOutputStream outputStream = new FileOutputStream(file);

        byte[] buffer = new byte[1024];
        int len;

        while ((len = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();

        return file;
    }

    @Override
    public String toCache(File data) {
        return null;//不缓存文件
    }
}
