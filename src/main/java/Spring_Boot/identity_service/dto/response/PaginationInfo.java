package Spring_Boot.identity_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationInfo {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalElements;
}
