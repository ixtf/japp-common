package rx;

import com.hengyi.japp.common.J;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

/**
 * Created by jzb on 16-6-13.
 */
public class RxTest {
    public static void main(String[] args) {
        UUID uuid = UUID.nameUUIDFromBytes("098f6bcd-4621-3373-8ade-4e832627b4f6,098f6bcd-4621-3373-8ade-4e832627b4f6".getBytes(StandardCharsets.UTF_8));//098f6bcd-4621-3373-8ade-4e832627b4f6
        System.out.println(uuid);
        long l = J.date(LocalDate.of(2016, Month.MARCH, 22)).getTime();
        byCreate();
        byCreate().flatMap(s -> Observable.just(s)).subscribe(s -> System.out.print(s));
    }

    private static Observable<String> byCreate() {
        return Observable.create(sub -> {
            sub.onNext("test");
//            sub.onCompleted();
        });
    }

    private static Observable<String> byDefer() {
        return Observable.defer(() -> Observable.just(""));
    }
}
