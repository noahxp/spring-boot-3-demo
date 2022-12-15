package tw.noah.demo.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tw.noah.demo.model.HttpPageResult;
import tw.noah.demo.model.HttpResult;
import tw.noah.demo.model.HttpResult.Status;


/**
 * rewrite http-body when data type is not Result.
 */
@ControllerAdvice(basePackages = "tw.noah.demo")
@Log4j2
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @SneakyThrows
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
        ServerHttpResponse response) {

        // result type 直接回傳
        if (body instanceof HttpResult) {
            return body;
        }

        // String type 重新包裝後回傳原型別
        if (body instanceof String) {
            return new ObjectMapper().writeValueAsString(new HttpResult(Status.success, body));
        }

        // 簡化 page 輸出
        if (body instanceof PageImpl<?> pages) {
            HttpPageResult.Page page = new HttpPageResult.Page(pages.getTotalPages(), pages.getNumber(), pages.getSize(), pages.getNumberOfElements(), (int) pages.getTotalElements());
            return new HttpPageResult(Status.success, pages.getContent(), page);

        }

        // 其他型別，包裝進 Result 裡
        return new HttpResult(Status.success, body);
    }

}
