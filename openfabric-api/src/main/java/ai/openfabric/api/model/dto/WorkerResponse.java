package ai.openfabric.api.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class WorkerResponse {

    private List<WorkerDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElement;
    private int totalPage;
    private boolean last;
}
