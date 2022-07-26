package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.dao.DaoImpl;
import org.example.exception.FileNotFoundException;
import org.example.exception.PathNotValidException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Slf4j
public class Service {
    DaoImpl dao = new DaoImpl();

    public void upload(String location) {
        if (StringUtils.isEmpty(location)) {
            throw new PathNotValidException();
        }
        dao.save(location);
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
        if (Objects.isNull(binary)){
            throw new RuntimeException("binary is null!");
        }
        File file = new File(location);
        file.getParentFile().mkdirs();
        try {
            Files.write(file.toPath(), binary);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
}
