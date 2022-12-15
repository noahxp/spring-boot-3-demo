package tw.noah.demo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

/**
 * example for jdk8~17 new release.
 */
@Log4j2
public class NewJDK {

    public void lambda() {
        log.info("----- lambda()-start -----");

        //        var map = new HashMap<String, Integer>();
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 30);
        map.put("b", 50);
        map.put("c", 500);

        log.info(map.size());

        //        map.entrySet().stream().forEach(k -> log.info(k.getValue()));
        //        map.forEach((k, v) -> log.info(v));

        List<Person> personList = new ArrayList<>();
        personList.add(new Person("a", "taipei", 100));
        personList.add(new Person("b", "new-taipei", 200));
        personList.add(new Person("c", "taiwan", 400));
        personList.add(new Person("c", "ilan", 300));

        int totalValue = personList.stream().parallel().map(p -> p.price).reduce(0, Integer::sum);  // sum for price
        int totalValue2 = personList.stream().parallel().mapToInt(p -> p.price).sum();  // sum for price
        long totalValue3 = personList.stream().parallel().map(p -> p.price).count(); // count for item

        log.info("totalValue = {}\t{}\t{}", totalValue, totalValue2, totalValue3);

        personList.stream().parallel().map(p -> p.price).max(Comparator.naturalOrder()).ifPresent(r -> log.info(r)); // max

        //@formatter:off
        var newPersonList = personList.stream().parallel()
            .collect(Collectors.groupingBy(o -> o.name)).entrySet().stream()
            .map(o -> o.getValue().stream().reduce((e1,e2)-> new Person(e1.name(), null, e1.price()+e2.price())))
            .map(o -> o.get())
            .collect(Collectors.toList());
        log.info(newPersonList);  // group by sum
        //@formatter:on

        log.info("----- lambda()-end -----");
    }

    public void Jdk8() {
        log.info("----- jdk8()-start -----");

        byte[] sources = "這是用來測試Base64 Encoder的原始字串".getBytes(StandardCharsets.UTF_8);
        String s10 = Base64.getEncoder().encodeToString(sources);
        String s11 = Base64.getEncoder().withoutPadding().encodeToString(sources);
        String s12 = new String(Base64.getDecoder().decode(s11.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        log.info(s10);
        log.info(s11);
        log.info(s12);

        String s20 = Base64.getMimeEncoder().encodeToString(sources);
        String s21 = Base64.getMimeEncoder().withoutPadding().encodeToString(sources);
        String s22 = new String(Base64.getMimeDecoder().decode(s21.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        log.info(s20);
        log.info(s21);
        log.info(s22);

        String s30 = Base64.getUrlEncoder().encodeToString(sources);
        String s31 = Base64.getUrlEncoder().withoutPadding().encodeToString(sources);
        String s32 = new String(Base64.getUrlDecoder().decode(s31.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        log.info(s30);
        log.info(s31);
        log.info(s32);

        log.info("----- jdk8()-end -----");
    }

    public void Jdk12() {
        log.info("----- jdk12()-start -----");

        int c = 3;
        int ret = 0;

        /* old statement */
        switch (c) {
            case 1:
                ret = c * 1;
                break;
            case 2:
                ret = c * 2;
                break;
            default:
                ret = c * 3;
        }
        log.info("old swatch case - ret={}", ret);

        /* new statement on jdk 12 */
        var ret12 = switch (c) {
            case 1 -> c * 1;
            case 2 -> c * 2;
            default -> c * 3;
        };
        log.info("jdk12 swatch-case - ret={}", ret12);

        log.info("----- jdk12()-end -----");
    }

    public void Jdk14() {
        log.info("----- jdk14()-start -----");

        Person p1 = new Person("john", "taipei", 0);
        Person p2 = new Person("john", "taipei", 0);

        log.info("p1 equal p2 = {}", p1.equals(p2));
        log.info("p1 = {}, p2 = {}", p1.toString(), p2.toString());

        log.info("----- jdk14()-end -----");
    }


    public void Jdk16() {
        log.info("----- jdk16()-start -----");

        Object obj = "this is a string.";
        if (obj instanceof String) {
            String s1 = (String) obj;
            log.info("obj is instance of String.class,string is : {}", s1);
        }

        // jdk 16
        if (obj instanceof String s2) {
            log.info("obj is instance of String.class(jdk16),string is : {}", s2);
        }
        if (obj instanceof String s2 && s2.contains("is")) {
            log.info("obj is instance of String.class(jdk16),string is : {}", s2);
        }

        log.info("----- jdk16()-end -----");
    }

    public void Jdk17() {
        log.info("----- jdk17()-start -----");

        var s = """
            This is a string area.
            It's support multiple line.
            """;
        log.info("text multiple line = {}", s);

        log.info("----- jdk17()-end -----");
    }


    // new on jdk 14
    private record Person(String name, String address, int price) {

    }


    public static void main(String[] args) {
        NewJDK jdk = new NewJDK();
        jdk.lambda();
        jdk.Jdk8();
        jdk.Jdk12();
        jdk.Jdk14();
        jdk.Jdk16();
        jdk.Jdk17();
    }
}
