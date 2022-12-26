package com.insider.poc1.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

//Desempenho: o EhCache foi projetado especificamente para oferecer alta performance e escalabilidade,
// o que o torna uma opção útil em aplicativos de alta carga.
//Facilidade de uso: o EhCache é fácil de configurar e usar, com uma interface simples e uma ampla variedade
// de opções de configuração.
//Integração com o Spring: o EhCache é totalmente integrado com o Spring, o que significa que você pode usar
// a anotação @Cacheable do Spring para habilitar o cache no seu aplicativo.
//Suporte a vários tipos de armazenamento: o EhCache oferece suporte a vários tipos de armazenamento, como memória,
// disco e armazenamento distribuído, o que o torna uma opção versátil para diferentes cenários de cache.
//Atualização em tempo real: o EhCache suporta atualização em tempo real, o que significa que o cache é
// atualizado automaticamente sempre que os dados são alterados no banco de dado
@Configuration
@EnableCaching
public class CacheConfiguration {
    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }
}
