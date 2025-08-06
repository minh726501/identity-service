package Spring_Boot.identity_service.dto;

import Spring_Boot.identity_service.dto.response.PaginationInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Setter
@Getter
public class ApiResponse<T> implements ResponseBodyAdvice {
    private int status;
    private String message;
    private T data;
    private PaginationInfo pagination;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int status, String message, T data, PaginationInfo pagination) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
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
        if (body instanceof String) {
            return body;
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", body);
    }
}
