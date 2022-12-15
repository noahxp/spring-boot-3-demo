package tw.noah.demo.interceptor;

import com.google.common.net.InternetDomainName;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URL;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 處理　cors 可授權的網域(Access-Control-Allow-Origin)、request method (Access-Control-Allow-Methods)、及 request 送進來的 headers (Access-Control-Allow-Headers)
 */
@Log4j2
@Component
public class CorsInterceptor implements HandlerInterceptor {

    @SneakyThrows
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String origin = request.getHeader("Origin");
        if (log.isDebugEnabled()) {
            log.debug(request.getRequestURL().toString());
            log.debug(request.getMethod());
            log.debug("CorsInterceptor.preHandle:" + origin);
        }
        if (!StringUtils.isEmpty(origin)) {
            // header 有帶 origin 過來，才需加上 cors 需要的 header
            InternetDomainName parent = InternetDomainName.from(new URL(origin).getHost()).parent();

            if (!StringUtils.isEmpty(parent)) {
                setAccessControlHeaders(response, origin);
            }
        }

        return true;
    }

    private void setAccessControlHeaders(HttpServletResponse response, String origin) {
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "HEAD, OPTIONS, GET, POST, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("vary", "Accept-Encoding,Origin,Access-Control-Request-Headers,Access-Control-Request-Method");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // nothing
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // nothing
    }
}
