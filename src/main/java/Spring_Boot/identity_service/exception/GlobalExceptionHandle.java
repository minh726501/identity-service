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
public class GlobalExceptionHandle implements ResponseBodyAdvice {
    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ApiResponse<Objects>>handleRuntimeException(RuntimeException e){
        ApiResponse<Objects>response=new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<List<String>>>handleValidationException(MethodArgumentNotValidException e){
        List<String>errors=e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->error.getField()+": "+error.getDefaultMessage())
                .toList();
        ApiResponse<List<String>>response=new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),"Validation failed",errors);
        return ResponseEntity.badRequest().body(response);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // Nếu đã là ApiResponse thì không cần gói lại
        if (body instanceof ApiResponse) {
            return body;
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", body);
    }
}
