package com.example.emailVerificationPractice.Config;

import com.example.emailVerificationPractice.Entity.Employee;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EhCacheConfiguration {

//    CacheManager cacheManager(){
//        return new EhCacheCacheManager();
//    }
//
//    private org.ehcache.CacheManager ehCacheManager(){
//        EhCacheCache
//    }





//Configuration for Elcachce 3.x but I couldn't know how to configure disk on where to  put a disk file path
//    @Bean
//    public CacheManager getCacheManager(){
//        CachingProvider provider = Caching.getCachingProvider();
//        javax.cache.CacheManager cacheManager = provider.getCacheManager();
//
//        // Define the base directory for disk storage
//        File diskStorePath = new File(System.getProperty("user.dir"), "ehcache_data");
//
//        // Configure the persistence configuration with the specified path
//        DefaultPersistenceConfiguration persistenceConfiguration = new DefaultPersistenceConfiguration(diskStorePath);
//
//
//
//        CacheConfiguration<Long, Employee> configuration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, Employee.class, ResourcePoolsBuilder
//                .heap(100)
//                .offheap(10, MemoryUnit.MB)
//                .disk(10,MemoryUnit.MB,true))
//                .build();
//
//        javax.cache.configuration.Configuration<Long, Employee> cacheConfig = Eh107Configuration.fromEhcacheCacheConfiguration(configuration);
//
//        cacheManager.createCache("employees", cacheConfig);
//        return cacheManager;
//    }
//
//


}
