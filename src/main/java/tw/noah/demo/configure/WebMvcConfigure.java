package tw.noah.demo.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tw.noah.demo.interceptor.CommonInterceptor;


@Log4j2
@Configuration
@ComponentScan("tw.noah.demo")
public class WebMvcConfigure implements WebMvcConfigurer {

    private CommonInterceptor commonInterceptor;

    @Autowired
    public WebMvcConfigure(CommonInterceptor commonInterceptor) {
        this.commonInterceptor = commonInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor).addPathPatterns("/**");
    }

    /**
     * rewrite default header
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(true).defaultContentType(MediaType.APPLICATION_JSON).mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 如果有String類型的返回值，有可能遇到類型不匹配的問題，因為在所有的HttpMessageConverter實例集合中，
        // StringHttpMessageConverter要比其它的Converter排得靠前一些。我們需要嘗試將處理Object類型的HttpMessageConverter放得靠前一些

        converters.add(0, new MappingJackson2HttpMessageConverter(getObjectMapper()));
    }

    // 修正 JacksonUtils 不支援 jdk 1.8 日期型別轉型錯誤
    private ObjectMapper getObjectMapper() {
        // LocalDate, LocalDateTime 使用 ISO_LOCAL_DATE_TIME, ISO_LOCAL_DATE 格式序列化
        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateSerializer localDateSerializer = new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE);
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, localDateTimeSerializer);
        module.addSerializer(LocalDate.class, localDateSerializer);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        return mapper;
    }
}
