package sura.com.co.domain.service.dependency;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sura.com.co.domain.model.Info;

public interface InfoRepository {
    Flux<Info> findByYear(Integer year);
    Mono<Info> findByName(String name);
    Flux<Info> findAll();
    Mono<Info> findById(String id);
    Mono<Info> save(Info info);
}
