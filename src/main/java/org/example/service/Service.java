package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.dao.DaoImpl;
import org.example.entity.DbEntity;
import org.example.exception.FileNotFoundException;
import org.example.exception.FileSizeException;
import org.example.exception.PathNotValidException;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class Service {

    private static final int maxSize = 200*1024*1024;

    private final DaoImpl dao = new DaoImpl();

    public void upload(String location) {
        if (StringUtils.isEmpty(location)) {
            throw new PathNotValidException();
        }
        readFile(location).ifPresentOrElse(dao::save,
                () -> log.info("Something was wrong :C"));

    }

    public void download(String filename, String location) {
        if (StringUtils.isAnyEmpty(filename, location)) {
            throw new PathNotValidException("path or location is null");
        }
        dao.findUsingStored(filename).ifPresentOrElse(entity -> writeFile(location, entity.getContent()),
                FileNotFoundException::new);
    }

    private void writeFile(String location, byte[] binary) {
        if (StringUtils.isEmpty(location)) {
            throw new PathNotValidException();
        }
        if (Objects.isNull(binary)) {
            throw new RuntimeException("binary is null!");
        }
        var file = new File(location);
        file.getParentFile().mkdirs();
        try {
            Files.write(file.toPath(), binary);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    // may cause outOfMemory, checks size after entire file is read
//    private Optional<DbEntity> readFile1(String location) {
//        try {
//            var file = new File(location);
//            var content = Files.readAllBytes(file.toPath());
//            if (maxSize < content.length) {
//                throw new FileSizeException();
//            }
//            var entityToSave = DbEntity.builder()
//                    .name(file.getName())
//                    .content(Files.readAllBytes(file.toPath()))
//                    .build();
//            return Optional.of(entityToSave);
//        } catch (IOException e) {
//            log.info(e.getMessage());
//            return Optional.empty();
//        }
//    }

    public static Optional<DbEntity> readFile(String location) {
        try (InputStream is = new FileInputStream(location);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            int size = 0;
            byte[] buffer = new byte[1024];

            for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
                if(maxSize<size){
                    throw new FileSizeException();
                }
                os.write(buffer, 0, len);
                size = size+buffer.length;
            }

            byte[] bytes = os.toByteArray();
            var entityToSave = DbEntity.builder()
                    .name(getFileName(location))
                    .content(bytes)
                    .build();
            return Optional.of(entityToSave);
        } catch (IOException e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    public static String getFileName(String location){
        String[] split = location.split("/");
        return split[split.length-1];
    }
}
