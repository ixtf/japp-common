package lmdb;

import org.deephacks.rxlmdb.KeyRange;
import org.deephacks.rxlmdb.KeyValue;
import org.deephacks.rxlmdb.RxDb;
import org.deephacks.rxlmdb.RxLmdb;
import org.fusesource.lmdbjni.ByteUnit;
import rx.Observable;

import java.util.List;

/**
 * Created by jzb on 16-7-1.
 */
public class LmdbTest {
    private static RxLmdb lmdb = RxLmdb.builder()
            .size(10, ByteUnit.GIBIBYTES)
            .path("/tmp/rxlmdb")
            .build();
    private static RxDb db = lmdb.dbBuilder()
            .name("test")
            .build();

    public static void main(String[] args) {
//        baseTest();
        db.get(Observable.just(new byte[]{1})).subscribe(kv -> {
            System.out.println(kv);
        });
    }

    private static void baseTest() {
        KeyValue[] kvs = new KeyValue[]{
                new KeyValue(new byte[]{1}, new byte[]{1}),
                new KeyValue(new byte[]{2}, new byte[]{2}),
                new KeyValue(new byte[]{3}, new byte[]{3})
        };
        // put
        db.put(Observable.from(kvs));
        // get
        Observable<KeyValue> o1 = db.get(Observable.just(new byte[]{1}));
        // scan forward
        Observable<List<KeyValue>> o2 = db.scan();
        // scan backward
        Observable<List<KeyValue>> o3 = db.scan(KeyRange.backward());
        // scan range forward
        Observable<List<KeyValue>> o4 = db.scan(
                KeyRange.range(new byte[]{1}, new byte[]{2})
        );
        // parallel range scans
        Observable<List<KeyValue>> o5 = db.scan(
                lmdb.readTx(),
                KeyRange.range(new byte[]{1}, new byte[]{1}),
                KeyRange.range(new byte[]{2}, new byte[]{2}),
                KeyRange.range(new byte[]{3}, new byte[]{3})
        );
        // zero copy parallel range scans
        Observable<List<Byte>> o6 = db.scan(
                lmdb.readTx(),
                (key, value) -> key.getByte(0),
                KeyRange.range(new byte[]{1}, new byte[]{1}),
                KeyRange.range(new byte[]{2}, new byte[]{2}),
                KeyRange.range(new byte[]{3}, new byte[]{3}));
        // Cursor scans
        Observable<List<byte[]>> obs = db.cursor((cursor, subscriber) -> {
            cursor.first();
            subscriber.onNext(cursor.keyBytes());
            cursor.last();
            subscriber.onNext(cursor.keyBytes());
        });
        // count rows
        Integer count = db.scan()
                .flatMap(Observable::from)
                .count().toBlocking().first();
        // delete
        db.delete(Observable.just(new byte[]{1}));
        // delete range
        Observable<byte[]> keys = db.scan()
                .flatMap(Observable::from)
                .map(kv -> kv.key());
        db.delete(keys);
    }
}
