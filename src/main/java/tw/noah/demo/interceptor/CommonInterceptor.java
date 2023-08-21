package tw.noah.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Component
public class CommonInterceptor implements HandlerInterceptor {

    @Override
    @SneakyThrows
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /**
         * mdc for logback logger
         */
        String remoteAddr = request.getHeader("HTTP_X_FORWARDED_FOR") != null ? request.getHeader("HTTP_X_FORWARDED_FOR")
            : (request.getHeader("X-Forwarded-For") != null ? request.getHeader("X-Forwarded-For") : request.getRemoteAddr());
        MDC.put("ip", remoteAddr);

        log.info("request.preHandle({})={}", request.getMethod(), request.getRequestURL().toString());
        if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.addHeader("Cache-Control", "no-store"); // no-cache response , https://developer.mozilla.org/zh-TW/docs/Web/HTTP/Caching
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("request.postHandle({})={}", request.getMethod(), request.getRequestURL().toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove("ip");
    }

}
