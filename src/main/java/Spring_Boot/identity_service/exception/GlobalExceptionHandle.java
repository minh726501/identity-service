package Spring_Boot.identity_service.exception;

import Spring_Boot.identity_service.dto.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandle  {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>>handleRuntimeException(RuntimeException e){
        ApiResponse<Object>response=new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<String>>>handleValidationException(MethodArgumentNotValidException e){
        List<String>errors=e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->error.getField()+": "+error.getDefaultMessage())
                .toList();
        ApiResponse<List<String>>response=new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),"Validation failed",errors);
        return ResponseEntity.badRequest().body(response);
    }
}
