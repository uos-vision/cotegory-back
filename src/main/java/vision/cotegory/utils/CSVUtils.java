package vision.cotegory.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CSVUtils {
    public static Optional<List<List<String>>> readCSV(String filePath) {
        List<List<String>> csvlist = null;
        File file = new File(filePath);
        BufferedReader bufferedReader = null;
        String line = "";

        if (!file.exists())
        {
            log.error("file not exist");
            return Optional.ofNullable(csvlist);
        }

        try {
            csvlist = new ArrayList<>();
            bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null)
            {
                List<String> aline = new ArrayList<String>();
                String[] lineArr = line.split(",");
                aline = Arrays.asList(lineArr);
                csvlist.add(aline);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            csvlist = null;
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (Exception e)
            {
                log.error(e.getMessage());
                csvlist = null;
            }
        }
        return Optional.ofNullable(csvlist);
    }

    public static void writeCSV(String filePath, String data) {
        File file = new File(filePath);
        BufferedWriter bufferedWriter = null;

        if (!file.exists()) {
            log.error("file not exist");
            return;
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(data);
            bufferedWriter.newLine();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if(bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

    }
}
