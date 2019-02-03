package ru.mironow.datasources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.mironow.data.DataFile;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * Компонент для сохранения данных в файл и получение их
 *
 * Created By Root to date 19.01.2019
 */
@Component
@PropertySource("application-data-from-file.properties")
public class DataFromFileSourceImpl implements DataFromFileSource {

    @Value("${data.path_to_file_data}")
    private String pathToFileUserData;

    private ObjectMapper objectMapper;
    private DataFile data;

    @PostConstruct
    private void firstLoadData() throws IOException {
            objectMapper = new ObjectMapper();
            try {
                InputStream is = new FileInputStream(pathToFileUserData);
                BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                String line = buf.readLine();
                StringBuilder sb = new StringBuilder();
                while(line != null){
                    sb.append(line).append("\n");
                    line = buf.readLine();
                }
                String fileAsString = sb.toString();
                data = objectMapper.readValue(Base64.decode(fileAsString), DataFile.class);
            } catch (FileNotFoundException e) {
                File file = new File(pathToFileUserData);
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.close();
                data = new DataFile();
            }
    }

    @Override
    public DataFile getData() {
        return data;
    }

    @Override
    public void saveData(DataFile dataFile) throws IOException {
        this.data = dataFile;
        String dataStr = objectMapper.writeValueAsString(this.data);
        File file = new File(pathToFileUserData);
        Writer writer = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(Base64.encode(dataStr.getBytes()));
        bufferedWriter.close();
    }
}