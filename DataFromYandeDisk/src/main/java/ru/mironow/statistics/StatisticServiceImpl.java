package ru.mironow.statistics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import ru.mironow.exes.DificultExe;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Сервис для загрузки статистики с Яндекс Диска
 *
 * Created By alex on date 30.01.19 20:50
 */
public class StatisticServiceImpl implements StatisticService {

    @Value("${yandex.disk.public_key}")
    private String linkFolder;
    @Value("${yandex.login}")
    private String userName;
    @Value("${yandex.disk_access_token}")
    private String yearAccessToken;


    private String attrFile = ".json";
    private String queryFolder = "https://cloud-api.yandex.net/v1/disk/public/resources/download";
    private String authorizeUrl = "https://oauth.yandex.ru/token?";
    private String urlForGetLoadLink = "https://cloud-api.yandex.net/v1/disk/resources/upload";


    private final ObjectMapper objectMapper;

    public StatisticServiceImpl() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Statistic loadStatisticByUserName(String userName) throws NotLoadStatistic {
        try {
            String queryString = "?public_key=" + linkFolder + "&path="  + URLEncoder.encode("/" + userName, "utf-8") + attrFile;
            String yandexResponse = fromURLtoString(queryFolder + queryString, "GET", null, null, null);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> data = objectMapper
                    .readValue( yandexResponse,
                                new TypeReference<Map<String,String>>(){});
            String dataStr = fromURLtoString(data.get("href"), "GET", null, null, null);
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

    @Override
    public void saveStatistic(Statistic statistic, String password) throws DontSaveStatistic, PasswordWrong {
        String[] letters = password.split(" ");
        String resultToken = null;
        char[] chars = yearAccessToken.toCharArray();
        try {
            for (String letter : letters) {
                if (letter.trim().length() != 0) {
                    Integer index = Integer.parseInt(letter.trim());
                    chars[index] = ' ';
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PasswordWrong();
        }
        resultToken = new String(chars);
        resultToken = resultToken.replaceAll(" ", "");

        Map<String, String> data = new HashMap<>();
        data.put("path", "/statistics/" + statistic.getUserName() + ".json");
        data.put("overwrite", "true");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "OAuth " + resultToken);
        try {
            Map<String, String> response = objectMapper.readValue(
                    fromURLtoString(urlForGetLoadLink, "GET", data, headers, null),
                    new TypeReference<Map<String,String>>(){});
            if(!response.containsKey("href")) {
                throw new PasswordWrong();
            }
            fromURLtoString(response.get("href"),
                    "PUT",
                    null,
                    null,
                    objectMapper.writeValueAsString(statistic).getBytes("UTF-8"));
        } catch(IOException e) {
            e.printStackTrace();
            throw new DontSaveStatistic();
        }
    }

    /**
     * Загрузка данных по URL
     * @param urlResource URL ресурса
     * @return строка данных
     * @throws IOException ошибка при открытии данных
     */
    private String fromURLtoString(
            String urlResource,
            String requestMethod,
            Map<String, String> dataParams,
            Map<String, String> headers,
            byte[] dataFile) throws IOException, PasswordWrong {
        String params = "";
        if(dataParams != null) {
            Iterator<Map.Entry<String, String>> iterator = dataParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                params += entry.getKey()
                        + "="
                        + URLEncoder.encode(entry.getValue(), "utf-8")
                        + ((iterator.hasNext()) ? "&" : "");
            }
        }
        URL url = new URL(urlResource + ((requestMethod == "GET" && params.length() != 0) ? "?" + params : ""));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(requestMethod);
        conn.setDoInput(true);
        if(headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }
        switch (requestMethod) {
            case "POST":
                conn.setDoOutput(true);
                byte[] data = null;
                OutputStream os = conn.getOutputStream();
                data = params.getBytes("UTF-8");
                os.write(data);
                break;
            case "PUT":
                conn.setDoOutput(true);
                OutputStream osFile = conn.getOutputStream();
                osFile.write(dataFile);
                break;
        }
        if(conn.getResponseCode() == 401) {
            throw new PasswordWrong();
        }
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String responseString = IOUtils.toString(in, "UTF-8");
        conn.disconnect();
        return responseString;
    }
}
