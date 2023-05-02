package com.zevseg.web.helper;

import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.util.Logger;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Helper
 *
 * @author Sainjargal Ishdorj
 **/

@Service
public class Helper {

    MessageSource messageSource;
    Environment env;

    public Helper(MessageSource messageSource, Environment env) {
        this.messageSource = messageSource;
        this.env = env;
    }

    public void mkdir(String folder) throws BusinessException {
        Path path = Paths.get(Objects.requireNonNull(env.getProperty("upload.dir"))).resolve(folder);
        try {
            Logger.info(this.getClass().getName(), "[mkdir][input][folder=" + folder + "]");
            Files.createDirectory(path);
            Logger.info(this.getClass().getName(), "[mkdir][output][Directory is created]");
        } catch (FileAlreadyExistsException e) {
            Logger.info(this.getClass().getName(), "[mkdir][output][Directory exists]");
        } catch (IOException e) {
            Logger.info(this.getClass().getName(), "[mkdir][output][Failed to create directory]");
            throw new BusinessException("Failed to create directory");
        }
    }
}
