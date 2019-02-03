package ru.mironow.statistics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import ru.mironow.exes.DificultExe;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для загрузки статистики с Яндекс Диска
 *
 * Created By alex on date 30.01.19 20:50
 */
public class StatisticServiceImpl implements StatisticService {

    @Value("${yandex.disk.public_key}")
    private String linkFolder;
    private String attrFile = ".json";
    private String queryFolder = "https://cloud-api.yandex.net/v1/disk/public/resources/download";

    private final ObjectMapper objectMapper;

    public StatisticServiceImpl() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Statistic loadStatisticByUserName(String userName) throws NotLoadStatistic {
        try {
            String queryString = "?public_key=" + linkFolder + "&path="  + URLEncoder.encode("/" + userName, "utf-8") + attrFile;
            String yandexResponse = fromURLtoString(queryFolder + queryString);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> data = objectMapper.readValue(yandexResponse, new TypeReference<Map<String,String>>(){});
            String dataStr = fromURLtoString(data.get("href"));
            Statistic statistic = objectMapper.readValue(dataStr, Statistic.class);
            return statistic;
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            Statistic statistic = new Statistic();
            statistic.setUserName(userName);
            statistic.setNumberLastExe(0);
            statistic.setDataStatistic(new HashMap<Integer, DificultExe>());
            return statistic;
        } catch(Exception e) {
            e.printStackTrace();
            throw new NotLoadStatistic("Невозможно загрузить статистику. Сервер не доступен");
        }
    }

    /**
     * Загрузка данных по URL
     * @param urlResource URL ресурса
     * @return строка данных
     * @throws IOException ошибка при открытии данных
     */
    private String fromURLtoString(String urlResource) throws IOException {
        URL url = new URL(urlResource);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String responseString = IOUtils.toString(in, "UTF-8");
        conn.disconnect();
        return responseString;
    }
}
