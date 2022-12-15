package tw.noah.demo.schedule;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class TestSchedule {

    //    @Scheduled(cron = "*/1 * * * * *")
    //    @SneakyThrows
    //    public void cron() {
    //        InetAddress address = InetAddress.getByName("billing.104-dev.com.tw");
    //        String ip = address.getHostAddress();
    //        log.info(ip);
    //    }
}
