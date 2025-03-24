package sura.com.co.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sura.com.co.domain.model.Info;
import sura.com.co.domain.service.dependency.InfoRepository;

@Repository
public class InfoRepositoryImpl implements InfoRepository {
    private final InfoMongoRepository mongoRepository;

    public InfoRepositoryImpl(InfoMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Flux<Info> findByYear(Integer year) {

        return mongoRepository.findByYear(year);
    }

    @Override
    public Mono<Info> findByName(String name) {

        return mongoRepository.findByName(name);
    }

    @Override
    public Flux<Info> findAll() {
        return mongoRepository.findAll();
    }

    @Override
    public Mono<Info> findById(String id) {
        return mongoRepository.findById(id);
    }

    @Override
    public Mono<Info> save(Info info) {
        return mongoRepository.save(info);
    }


}
