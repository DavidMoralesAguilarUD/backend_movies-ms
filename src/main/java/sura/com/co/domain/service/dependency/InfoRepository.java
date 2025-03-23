package sura.com.co.domain.service.dependency;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sura.com.co.domain.model.Info;

public interface InfoRepository extends ReactiveMongoRepository<Info, String> {


    Flux<Info> findByYear(Integer year);
    Mono<Info> findByName(String name);

}
