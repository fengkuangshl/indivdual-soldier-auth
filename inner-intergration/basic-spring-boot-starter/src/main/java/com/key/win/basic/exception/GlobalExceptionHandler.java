package com.key.win.basic.exception;

import com.key.win.basic.web.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResponseEntity<Result<String>> bizExceptionHandler(HttpServletRequest req, BizException ex) {
        logger.error("发生业务异常！原因是：{}", ex.getMessage(), ex);
        return ResponseEntity.ok().body(Result.failed(ex.getMessage()));
    }

    /**
     * 非法参数
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<Result<String>> bizIllegalArgumentHandler(HttpServletRequest req, IllegalArgumentException ex) {
        logger.error("发生业务异常！原因是：{}", ex.getMessage(), ex);
        return ResponseEntity.ok().body(Result.failed(ex.getMessage()));
    }

    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(value = UserIllegalException.class)
    @ResponseBody
    public ResponseEntity<String> userIllegalException(HttpServletRequest req, UserIllegalException ex) {
        logger.error("token异常！原因是：{}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }


    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<String> requestAccessDenied(AccessDeniedException ex) {
        logger.error("AccessDenied异常！原因是：{}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    /**
     * 400错误
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<String> requestNotReadable(HttpMessageNotReadableException ex) {
        logger.error("400错误(HttpMessageNotReadableException)！原因是：" + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * 400错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({TypeMismatchException.class})
    public ResponseEntity<String> requestTypeMismatch(TypeMismatchException ex) {
        logger.error("400错误(TypeMismatchException)！原因是：" + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * 400错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<String> requestMissingServletRequest(MissingServletRequestParameterException ex) {
        logger.error("400错误(MissingServletRequestParameterException)！原因是：" + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * IO异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> iOExceptionHandler(IOException ex) {
        logger.error("IO异常！原因是：" + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    /**
     * 405错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<String> request405(HttpRequestMethodNotSupportedException ex) {
        logger.error("405错误！原因是：" + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("不支持请求方法");
    }


    /**
     * 超时异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<String> SocketTimeoutException(SocketTimeoutException ex) {
        logger.error("超时异常！原因是：" + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("连接超时,请检查网络环境或重试");
    }

    /**
     * 处理入参异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleIllegalParamException(MethodArgumentNotValidException ex) {
        logger.error("处理入参异常！原因是：" + ex.getMessage(), ex);
        StringBuilder message = new StringBuilder();
        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            if (!CollectionUtils.isEmpty(allErrors)) {
                allErrors.forEach(err -> message.append(err.getDefaultMessage()).append("|"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
    }

    /**
     * 其他类型的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handle(Exception ex) {
        logger.error("其他类型的异常！原因是：" + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}