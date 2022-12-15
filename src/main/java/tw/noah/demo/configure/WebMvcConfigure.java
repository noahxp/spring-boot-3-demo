package tw.noah.demo.configure;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tw.noah.demo.interceptor.CommonInterceptor;
import tw.noah.demo.interceptor.CorsInterceptor;


@Log4j2
@Configuration
@ComponentScan("tw.noah.demo")
public class WebMvcConfigure implements WebMvcConfigurer {

    private CommonInterceptor commonInterceptor;
    private CorsInterceptor corsInterceptor;

    public WebMvcConfigure(CommonInterceptor commonInterceptor, CorsInterceptor corsInterceptor) {
        this.commonInterceptor = commonInterceptor;
        this.corsInterceptor = corsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor).addPathPatterns("/**");
        registry.addInterceptor(corsInterceptor).addPathPatterns("/apis/**");
    }

    /**
     * rewrite default header
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.APPLICATION_JSON);
    }
}
