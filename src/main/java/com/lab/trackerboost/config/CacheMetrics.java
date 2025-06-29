package com.lab.trackerboost.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CacheMetrics {

    private final Counter cacheHits;
    private final Counter cacheMisses;

    public CacheMetrics(MeterRegistry meterRegistry) {
        this.cacheHits = meterRegistry.counter("cache_hits_total");
        this.cacheMisses = meterRegistry.counter("cache_misses_total");
    }

    public void incrementCacheHit() {
        cacheHits.increment();
    }

    public void incrementCacheMiss() {
        cacheMisses.increment();
    }
}
