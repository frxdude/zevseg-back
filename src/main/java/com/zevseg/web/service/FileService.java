package com.zevseg.web.service;

import com.zevseg.web.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * FileService
 *
 * @author Sainjargal Ishdorj
 **/

public interface FileService {

    void upload(MultipartFile file, String folder, String name, HttpServletRequest req) throws BusinessException, IOException;

}
