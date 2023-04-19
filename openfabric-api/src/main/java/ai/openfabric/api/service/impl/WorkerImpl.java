package ai.openfabric.api.service.impl;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.model.dto.WorkerDto;
import ai.openfabric.api.model.dto.WorkerResponse;
import ai.openfabric.api.repository.WorkerRepository;
import ai.openfabric.api.service.WorkerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerImpl implements WorkerService {
    private WorkerRepository workerRepository;

    public WorkerImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public WorkerDto createWorker(WorkerDto workerDto) {
        Worker workerSaved = workerRepository.save(mapToEntity(workerDto));
        return mapToDto(workerSaved);
    }

    @Override
    public WorkerResponse getAllWorker(int pageNo, int pageSize, String sortBy) {

        PageRequest pageable =  PageRequest.of(pageNo,pageSize, Sort.by(sortBy));

        Page<Worker> workerPage = workerRepository.findAll(pageable);


        List<Worker> workerList = workerPage.getContent();

        List<WorkerDto> content = workerList.stream().map(worker -> mapToDto(worker)).collect(Collectors.toList());

        WorkerResponse workerResponse = new WorkerResponse();
        workerResponse.setContent(content);
        workerResponse.setLast(workerPage.isLast());
        workerResponse.setPageNo(workerPage.getNumber());
        workerResponse.setTotalElement(workerPage.getTotalElements());
        workerResponse.setPageSize(workerPage.getSize());
        workerResponse.setTotalPage(workerPage.getTotalPages());


        return workerResponse;
    }

    @Override
    public WorkerDto getWorkerById(String id) {
        Worker worker = workerRepository.findById(id).orElseThrow(()->
                new IllegalStateException("Not Found"));
        return mapToDto(worker);
    }

    @Override
    public WorkerDto updateWorker(String id, WorkerDto workerDto) {
        Worker worker = workerRepository.findById(id).orElseThrow(()->
                new IllegalStateException("Not Found"));

        worker.setStatus(workerDto.getStatus());
        worker.setName(workerDto.getName());
        worker.setEmail(workerDto.getEmail());

        Worker worker1 = workerRepository.save(worker);
        return mapToDto(worker1);
    }

    private WorkerDto mapToDto(Worker worker){
        WorkerDto workerDto = new WorkerDto();
        workerDto.setEmail(worker.getEmail());
        workerDto.setName(worker.getName());
        workerDto.setId(worker.getId());
        workerDto.setCreated_at(worker.getCreatedAt().toString());
        workerDto.setStatus(worker.getStatus());
        return workerDto;

    }

    private Worker mapToEntity(WorkerDto workerDto){
        Worker worker = new Worker();
        worker.setEmail(workerDto.getEmail());
        worker.setStatus(workerDto.getStatus());
        worker.setName(workerDto.getName());
        return  worker;
    }
}
