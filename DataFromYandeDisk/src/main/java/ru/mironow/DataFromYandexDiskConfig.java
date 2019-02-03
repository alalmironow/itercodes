package ru.mironow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.mironow.statistics.StatisticService;
import ru.mironow.statistics.StatisticServiceImpl;

/**
 * Конфигурационный класс для компонентов загрузки данных c Яндекс Диска
 *
 * Created By alex on date 30.01.19 20:24
 */
@Configuration
@PropertySource("classpath:application-data.properties")
public class DataFromYandexDiskConfig {

    @Bean
    public StatisticService getStatisticService() {
        return new StatisticServiceImpl();
    }
}
