package Spring_Boot.identity_service.config;

import Spring_Boot.identity_service.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiResponse apiResponse=new ApiResponse<>(401,"Invalid or expired token",null);
        ObjectMapper mapper=new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(apiResponse));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // ⚠️ MUST
        response.setContentType("application/json");             // ⚠️ MUST
        response.flushBuffer();
    }
}
