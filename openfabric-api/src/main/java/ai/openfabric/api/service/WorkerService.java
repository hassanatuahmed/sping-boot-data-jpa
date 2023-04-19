package ai.openfabric.api.service;

import ai.openfabric.api.model.dto.WorkerDto;
import ai.openfabric.api.model.dto.WorkerResponse;


public interface WorkerService {

    WorkerDto createWorker(WorkerDto workerDto);

    WorkerResponse getAllWorker(int pageNo, int pageSize, String sortBy);

    WorkerDto getWorkerById(String id);

    WorkerDto updateWorker(String id, WorkerDto workerDto);
}
