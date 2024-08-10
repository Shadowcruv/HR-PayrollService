package com.example.emailVerificationPractice.Config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class CacheConfiguration {
//
//    CacheManagerCustomizer<ConcurrentMapCacheManager> customizer(){
//        return new ConcurrentCustomizer();
//    }
//
//    class ConcurrentCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager>{
//
//        @Override
//        public void customize(ConcurrentMapCacheManager cacheManager) {
//            cacheManager.setAllowNullValues(true); //Default is false, ConcurrentHashMap itself does not hold null values but the process is able to happen through an internal holder object.
//            cacheManager.setStoreByValue(false); //Default is false but if you make it true, the entity that's using it must implement seriaziable
//        }
//    }
}
