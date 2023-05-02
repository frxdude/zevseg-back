package com.zevseg.web.exception;
import com.zevseg.web.model.ErrorDetail;

import java.util.List;
/**
 * @author Sainjargal Ishdorj
 * @created 2022.06.02
 * @goal
 */
public class UnauthorizedException extends Exception{

    public List<ErrorDetail> errorDetails;

    public String reason;

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

}
