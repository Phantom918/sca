package com.phantom.sca.order.config;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 自定义 StringRedisSerializer, 必须重写序列化器，否则@Cacheable注解的key会报类型转换错误
 * <p>
 * 原因: 使用StringRedisSerializer做key的序列化时，StringRedisSerializer的泛型指定的是String，传其他对象就会报类型转换错误，
 * 在使用@Cacheable注解是key属性就只能传String进来。把这个序列化方式重写了，将泛型改成Object
 * </p>
 *
 * @Author lei.tan
 * @Date 2022/9/6 17:23
 * @Version 1.0
 */
public class StringRedisSerializer implements RedisSerializer<Object> {

    /**
     * 字符集
     */
    private final Charset charset;

    private final String target = "\"";

    private final String replacement = "";

    public StringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public StringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "自定义 StringRedisSerializer 初始化字符集不能为空！");
        this.charset = charset;
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        String result = JSON.toJSONString(o);
        if ("".equals(result)) {
            return null;
        }
        result = result.replace(target, replacement);
        return result.getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return bytes == null ? null : new String(bytes, charset);
    }

}
