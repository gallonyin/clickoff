package org.caworks.library.util.imageLoader;

import android.content.Context;

import com.aegis.mobile.library.util.util.GLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 *  1.Glide设置本地缓存图片所占空间限制
 *  2.修改Glide图片的内存缓存大小 增加流畅性和降低崩溃率
 * Created by gallon on 2017/5/19
 */
public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 自定义缓存目录 磁盘缓存给300M (因为不止图片缓存 实际cache目录会略微大于该值)  PS:实测300MB比较平衡(建议值)
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "image_catch", 300 * 1024 * 1024));

        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        // 修改为Glide自适应值的30%  PS:实测内存范围比较平衡(建议值)(建议配合Glide加载图片的本地缓存ALL)
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (0.3 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (0.3 * defaultBitmapPoolSize);

        GLog.e("GlideConfiguration", "customMemoryCacheSize : " + customMemoryCacheSize);
        GLog.e("GlideConfiguration", "customBitmapPoolSize : " + customBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

}