package tw.noah.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.noah.demo.model.HttpResult;
import tw.noah.demo.model.HttpResult.Status;

@RestController
@RequestMapping("/abc")
@Log4j2
public class TestController {

    @PostMapping(value = "/post")
    public void post(@RequestBody String body) {
        log.info("abc-post");
        log.info(body);
    }

    @GetMapping(value = "/aa")
    public void aa(HttpEntity<String> httpEntity) {
        log.info("abc-xyz");
        log.info(httpEntity.getBody());
    }

    @GetMapping(value = "/bb")
    public boolean bb(HttpEntity<String> httpEntity) {
        return true;
    }

    @GetMapping(value = "/cc")
    public String cc(HttpEntity<String> httpEntity) {
        return "Hi, This is true.";
    }

    @GetMapping(value = "/dd")
    public Map<String, String> dd(HttpEntity<String> httpEntity) {
        var ret = new HashMap<String, String>();
        ret.put("key1", "value1");
        return ret;
    }

    @GetMapping(value = "/ee")
    public Date ee(HttpEntity<String> httpEntity) {
        return new Date();
    }

    @GetMapping(value = "/ff")
    public HttpResult ff(HttpEntity<String> httpEntity) {
        return new HttpResult(Status.success, new Date());
    }

}
