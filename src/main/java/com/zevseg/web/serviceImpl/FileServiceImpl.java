package com.zevseg.web.serviceImpl;

import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.helper.Helper;
import com.zevseg.web.service.FileService;
import com.zevseg.web.util.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * FileServiceImpl
 *
 * @author Sainjargal Ishdorj
 **/

@Service
public class FileServiceImpl implements FileService {

    Helper helper;
    Environment env;

    @Autowired
    public FileServiceImpl(Helper helper, Environment env) {
        this.helper = helper;
        this.env = env;
    }

    /**
     * @param file   {@link MultipartFile}
     * @param folder String
     * @param name   String
     * @param req    {@link HttpServletRequest}
     * @throws BusinessException when content type cannot be accepted
     * @throws IOException when path cannot be found
     * @author Sainjargal Ishdorj
     **/

    @Override
    public void upload(MultipartFile file, String folder, String name, HttpServletRequest req) throws BusinessException, IOException {
        try {
            Logger.info(this.getClass().getName(), "[upload][input][file=" + file.getName() + ", folder=" + folder + ", name=" + name + "]");
            if (!Objects.equals(file.getContentType(), "image/jpeg") &&
                    !Objects.equals(file.getContentType(), "image/png") &&
                    !Objects.equals(file.getContentType(), "image/jpg") &&
                    !Objects.equals(file.getContentType(), "image/gif") &&
                    !Objects.equals(file.getContentType(), "image/svg+xml") &&
                    !Objects.equals(file.getContentType(), "image/tif") &&
                    !Objects.equals(file.getContentType(), "image/tiff") &&
                    !Objects.equals(file.getContentType(), "image/bmp") &&
                    !Objects.equals(file.getContentType(), "image/eps") &&
                    !Objects.equals(file.getContentType(), "image/x-dcraw"))
                throw new BusinessException("Unacceptable content type");

            String saveName = name;

            if(name == null || StringUtils.isBlank(name))
                saveName = System.nanoTime() + "." + Objects.requireNonNull(file.getContentType()).split("/")[1];

            helper.mkdir(folder);
            Path path = Paths.get(env.getProperty("upload.dir") + folder + "/" + saveName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Logger.info(this.getClass().getName(), "[upload][output][]");
        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[upload][" + ex.getMessage() + "]");
            throw ex;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[upload][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }
}
