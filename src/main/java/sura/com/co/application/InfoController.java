package sura.com.co.application;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sura.com.co.domain.model.Info;
import sura.com.co.domain.service.InfoService;


@RestController
@RequestMapping("/v1")
public class InfoController {

    private InfoService InfoService;

    public InfoController(InfoService infoService){

        this.InfoService = infoService;
    }


    @GetMapping("/infos")
    public Flux<Info> getallInfos(@RequestParam(value="year", required=false) Integer year,
                                       @RequestParam(value = "name", required = false) String name){
        if(year != null){
            return InfoService.getInfoByYear(year);
        }
        if(name != null){
            return InfoService.getInfoByName(name).flux();
        }
        return InfoService.getAllInfos().log();

    }

    @GetMapping("infos/{id}")
    public Mono<ResponseEntity<Info>> getInfoById(@PathVariable String id){
        return InfoService.getInfoId(id)
                .map(info -> ResponseEntity.ok().body(info))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }
    @PostMapping("/infos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Info> addInfo(@RequestBody @Valid Info info){
        return InfoService.addInfo(info).log();

    }

    @PutMapping("/infos/{id}")
    public Mono<ResponseEntity<Info>> updateInfo(@RequestBody Info updatedInfo, @PathVariable String id){

        return InfoService.updateInfo(updatedInfo, id)
                .map(info -> {
                    return ResponseEntity.ok().body(info);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @DeleteMapping("/infos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Info> deleteInfo(@PathVariable String id){
        return InfoService.deleteInfo(id);
    }



}
