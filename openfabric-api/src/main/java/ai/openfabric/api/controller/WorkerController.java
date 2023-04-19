package ai.openfabric.api.controller;

import ai.openfabric.api.model.dto.WorkerDto;
import ai.openfabric.api.model.dto.WorkerResponse;
import ai.openfabric.api.service.WorkerService;
import ai.openfabric.api.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("${node.api.path}/worker")
@RequestMapping("/api")
public class WorkerController {

    private WorkerService workerService;



    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping("/create-worker")
    public ResponseEntity<WorkerDto> createWorker(@RequestBody WorkerDto workerDto) {
        return new ResponseEntity<>(workerService.createWorker(workerDto), HttpStatus.CREATED);


    }


    @GetMapping("/get-workers")
    public WorkerResponse getPost(@RequestParam(value = "pageNo",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                  @RequestParam(value = "pageSize",defaultValue = AppConstant.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                  @RequestParam(value = "sortBy",defaultValue = AppConstant.DEFAULT_SORT_BY,required = false) String sortBy
    ){
        return workerService.getAllWorker(pageNo,pageSize,sortBy);
    }

    @GetMapping("/get-workers/{id}")
    public ResponseEntity<WorkerDto> getWorker(@PathVariable(value = "id") String id){
        return ResponseEntity.ok(workerService.getWorkerById(id));

    }



//      List workers (paginated)
//* ▶️ Start and ⏹️ Stop worker
//* 🔍 Get worker information
//* 📊 Get worker statistics
}
