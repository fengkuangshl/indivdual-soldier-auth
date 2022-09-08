package com.key.win.rsa.exception;

import com.key.win.rsa.web.EncryptResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Order(-1)
@ControllerAdvice
public class GlobalEncryptExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 非法参数
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BizEncryptException.class)
    @ResponseBody
    public ResponseEntity<EncryptResponse<String>> bizEncryptExceptionHandler(HttpServletRequest req, BizEncryptException ex) {
        logger.error("发生业务异常！原因是：{}", ex.getMessage(), ex);
        return ResponseEntity.ok().body(EncryptResponse.failed(ex.getMessage()));
    }
}