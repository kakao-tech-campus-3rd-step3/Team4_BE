package com.example.demo.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.core.io.ClassPathResource;

public class YamlResourceLoader {

    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    private YamlResourceLoader() {}

    public static <T> T load(String resourcePath, Class<T> clazz) {
        try (InputStream inputStream = new ClassPathResource(resourcePath).getInputStream()) {
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new RuntimeException("리소스를 불러오는 중 오류 발생: " + resourcePath, e);
        }
    }

    public static <T> List<T> loadList(String resourcePath, TypeReference<List<T>> typeReference) {
        try (InputStream inputStream = new ClassPathResource(resourcePath).getInputStream()) {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("리소스를 불러오는 중 오류 발생: " + resourcePath, e);
        }
    }
}
