package sura.com.co.domain.service.dependency;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import sura.com.co.domain.model.Review;

public interface ReviewRepository extends ReactiveMongoRepository<Review,String> {
    Flux<Review> findReviewsByMovieInfoId(Long movieInfoId);
}
