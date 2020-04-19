package com.dx.test.framework.base.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置 Json 解析器
 */
@Configuration
public class JsonConfig {

    /**
     * 自定义 Json 解析器
     * Tips 在 SpringMVC 中需要重写 WebMvcConfigurer 的 configureMessageConverters 方法来注册自定义的 Json 解析器
     *
     * @return FastJsonHttpMessageConverter
     */
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {

        // --------------- 定义配置 ---------------
        FastJsonConfig config = new FastJsonConfig();
        // 编码格式
        config.setCharset(StandardCharsets.UTF_8);
        // 序列化规则
        config.setSerializerFeatures(
                // 值为 null 的属性也会输出
                SerializerFeature.WriteMapNullValue,
                // 将空的集合输出为 []
                SerializerFeature.WriteNullListAsEmpty,
                // 将空的字符串输出为 ""
                SerializerFeature.WriteNullStringAsEmpty,
                // 将 null 的 Boolean 类型输出为 false
                SerializerFeature.WriteNullBooleanAsFalse
        );

        // --------------- 初始化解析器 ---------------
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(config);

        /*
         * Tips 关于 java.lang.IllegalArgumentException: Content-Type cannot contain wildcard type '*'
         *  FastJson 的 1.1.41 版本中, FastJsonHttpMessageConverter 初始化时设置了 MediaType:
         *      super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
         *  而在升级到 1.2.28 以后, 设置的 MediaType 为变为 '/':
         *      super(MediaType.ALL);
         *  后续在 org.springframework.http.converter.AbstractHttpMessageConverter.write 过程中
         *  又要判断 Content-Type 不能含有通配符(这应该是一种保护机制, 并强制用户自己配置 MediaType)
         *  所以此时需要手动配置 MediaType
         */
        // --------------- 升级到 1.2.28 以后需加 start ---------------
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        // --------------- 升级到 1.2.28 以后需加 end ---------------

        return converter;
    }

    /*
     *  Tips SerializerFeature 各个属性含义:
     *      QuoteFieldNames	输出key时是否使用双引号,默认为true
     *      UseSingleQuotes	使用单引号而不是双引号,默认为false
     *      WriteMapNullValue	是否输出值为null的字段,默认为false
     *      WriteEnumUsingToString	Enum输出name()或者original,默认为false
     *      UseISO8601DateFormat	Date使用ISO8601格式输出，默认为false
     *      WriteNullListAsEmpty	List字段如果为null,输出为[],而非null
     *      WriteNullStringAsEmpty	字符类型字段如果为null,输出为”“,而非null
     *      WriteNullNumberAsZero	数值字段如果为null,输出为0,而非null
     *      WriteNullBooleanAsFalse	Boolean字段如果为null,输出为false,而非null
     *      SkipTransientField	如果是true，类中的Get方法对应的Field是transient，序列化时将会被忽略。默认为true
     *      SortField	按字段名称排序后输出。默认为false
     *      WriteTabAsSpecial	把\t做转义输出，默认为false	不推荐
     *      PrettyFormat	结果是否格式化,默认为false
     *      WriteClassName	序列化时写入类型信息，默认为false。反序列化是需用到
     *      DisableCircularReferenceDetect	消除对同一对象循环引用的问题，默认为false
     *      WriteSlashAsSpecial	对斜杠’/’进行转义
     *      BrowserCompatible	将中文都会序列化为 Unicode 格式，字节数会多一些，但是能兼容IE 6，默认为false
     *      WriteDateUseDateFormat	全局修改日期格式,默认为false。JSON.DEFFAULT_DATE_FORMAT = “yyyy-MM-dd”;JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
     *      DisableCheckSpecialChar	一个对象的字符串属性中如果有特殊字符如双引号，将会在转成json时带有反斜杠转移符。如果不需要转义，可以使用这个属性。默认为false
     *      NotWriteRootClassName	含义
     *      BeanToArray	将对象转为array输出
     *      WriteNonStringKeyAsString	含义
     *      NotWriteDefaultValue	含义
     *      BrowserSecure	含义
     *      IgnoreNonFieldGetter	含义
     *      WriteEnumUsingName	含义
     */
}
