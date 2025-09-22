//package com.backend.blog.security;
//
//import java.time.Duration;
//import java.util.function.Supplier;
//
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.Bucket4j;
//import io.github.bucket4j.Refill;
//import io.github.bucket4j.distributed.proxy.ClientSideConfig;
//import io.github.bucket4j.distributed.proxy.ProxyManager;
//
//@Service
//public class RateLimiterService {
//
//	    @Autowired
//	    private RedissonClient redissonClient;
//
//	    private static final int REQUEST_LIMIT = 5;
//	    private static final Duration DURATION = Duration.ofMinutes(1);
//
//	    private final RedissonBucket4jExtension extension = RedissonBucket4jExtension.getDefault();
//
//	    public boolean isAllowed(String key) {
//	        ProxyManager<String> proxyManager = extension.proxyManagerForMap(redissonClient.getMap("buckets"));
//
//	        Supplier<Bucket> bucketSupplier = () -> {
//	            return Bucket4j.builder()
//	                .addLimit(Bandwidth.classic(REQUEST_LIMIT, Refill.greedy(REQUEST_LIMIT, DURATION)))
//	                .build();
//	        };
//
//	        Bucket bucket = proxyManager.builder().build(key, bucketSupplier, ClientSideConfig.getDefault());
//	        return bucket.tryConsume(1);
//	    }
//}
//
