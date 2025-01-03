package com.example.kafka_error_handling.util;

import com.example.kafka_error_handling.dto.User;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import com.opencsv.bean.CsvToBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

//Class Util to read a csv from resource folder into the project and transform it into List
public class CsvReaderUtils {

    public static List<User> readDataFromCsv(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("users.csv").getInputStream()))){
            CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
