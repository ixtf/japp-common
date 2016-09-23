package ehcache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.hengyi.japp.common.Constant.MAPPER;

/**
 * Created by jzb on 16-6-13.
 */
public class EhcacheTest {
    private static final String path = "/tmp/ehcache/test";
    private PersistentCacheManager cacheManager;

    private Cache<String, String> getCache() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(path))
                .withCache("test-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(10, EntryUnit.ENTRIES)
                                .disk(10, MemoryUnit.MB, true))
                )
                .build(true);
        return cacheManager.getCache("test-cache", String.class, String.class);
    }

    @Before
    public void before() throws JsonProcessingException {
        Cache<String, String> cache = getCache();
        ArrayNode node = MAPPER.createArrayNode();
        node.add("1");
        node.add("2");
        cache.put("test", MAPPER.writeValueAsString(node));
        cacheManager.close();
    }

    @Test
    public void test() throws IOException {
        Cache<String, String> cache = getCache();
        String value = cache.get("test");
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(HashSet.class, String.class);
        Set<String> set = MAPPER.readValue(value, javaType);
        System.out.println(set);
    }
}
