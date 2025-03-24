package sura.com.co.domain.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sura.com.co.domain.model.Info;
import sura.com.co.domain.service.dependency.InfoRepository;

@Service
public class InfoService {
    private InfoRepository infoRepository;

    public InfoService(InfoRepository infoRepository ) {
        this.infoRepository = infoRepository;
    }
    public Flux<Info> getInfoByYear(Integer year) {
        return infoRepository.findByYear(year);
    }

    public Flux<Info> getAllInfos() {
        return infoRepository.findAll();
    }

    public Mono<Info> getInfoId(String id) {
        return infoRepository.findById(id);
    }

    public Mono<Info> addInfo(Info info) {
        return infoRepository.save(info);
    }


    public Mono<Info> getInfoByName(String name) {
        return infoRepository.findByName(name);
    }

    public Mono<Info> updateInfo(Info updatedInfo, String id) {
        return infoRepository.findById(id)
                .flatMap(info -> {
                    info.setCast(updatedInfo.getCast());
                    info.setName(updatedInfo.getName());
                    info.setRelease_date(updatedInfo.getRelease_date());
                    info.setYear(updatedInfo.getYear());
                    return infoRepository.save(info);
                });

    }


    public Mono<Info> deleteInfo(String id) {
        return infoRepository.findById(id);

    }
}
