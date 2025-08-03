package Spring_Boot.identity_service.exception;

import Spring_Boot.identity_service.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


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
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>>handleAccessDeniedException(AccessDeniedException e){
        ApiResponse<Object>response=new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Không có quyền truy cập", null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
