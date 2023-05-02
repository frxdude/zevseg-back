package com.zevseg.web.controller;

import com.zevseg.web.dto.ErrorResponse;
import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * FileController
 *
 * @author Sainjargal Ishdorj
 **/

@Api(tags = "Image")
@RestController
@RequestMapping("files")
public class FileController {

    FileService service;

    @Autowired
    public FileController(FileService service) {
        this.service = service;
    }

    @ApiOperation(value = "File хуулах. |", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "body-гүй created статус буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> upload(@RequestParam MultipartFile file,
                                         @RequestParam(required = false) String folder,
                                         @RequestParam(required = false) String name,
                                         HttpServletRequest req) throws BusinessException, IOException {
        service.upload(file, folder, name, req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
