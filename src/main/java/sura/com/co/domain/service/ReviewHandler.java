package sura.com.co.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sura.com.co.domain.exception.ReviewDataException;
import sura.com.co.domain.model.Review;
import sura.com.co.domain.service.dependency.ReviewRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Map;

import java.util.stream.Collectors;

@Component
@Slf4j
public class ReviewHandler {

    @Autowired
    private Validator validator;
    private ReviewRepository reviewRepository;

    public ReviewHandler(ReviewRepository reviewRepository) {

        this.reviewRepository = reviewRepository;
    }
    public Mono<ServerResponse> addReview(ServerRequest request) {
        return request.bodyToMono(Review.class)
                .doOnNext(this::validate)
                .flatMap(reviewRepository::save)
                .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);

    }

    private void validate(Review review) {
        var constraintViolations = validator.validate(review);
        if(!constraintViolations.isEmpty()) {
            var errorMessage = constraintViolations.stream().map(ConstraintViolation::getMessage)
                    .sorted()
                    .collect(Collectors.joining(","));
            throw new ReviewDataException(errorMessage);
        }
    }

    public Mono<ServerResponse> getReviews(ServerRequest request) {
        var movieInfoId = request.queryParam("movieInfoId");

        if(movieInfoId.isPresent()) {
            var reviewsFlux = reviewRepository.findReviewsByMovieInfoId(Long.valueOf(movieInfoId.get()));
            return ServerResponse.ok().body(reviewsFlux, Review.class);
        } else{
            var reviewsFlux = reviewRepository.findAll();
            return ServerResponse.ok().body(reviewsFlux, Review.class);
        }

    }

    public Mono<ServerResponse> updateReviews(ServerRequest request) {
        var reviewId = request.pathVariable("id");

        return reviewRepository.findById(reviewId)
                .flatMap(review -> request.bodyToMono(Review.class)
                        .map(reqReview -> {
                            review.setComment(reqReview.getComment());
                            review.setRating(reqReview.getRating());
                            return review;
                        })
                        .flatMap(reviewRepository::save)
                        .flatMap(savedReview -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(savedReview))
                )
                .switchIfEmpty(Mono.defer(() -> {
                    var errorResponse = Map.of(
                            "error message", "Review not found for the given Review id: " + reviewId
                    );
                    return ServerResponse.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(errorResponse);
                }));
    }

    public Mono<ServerResponse> deleteReviews(ServerRequest request) {
        var reviewId = request.pathVariable("id");
        var existingReview = reviewRepository.findById(reviewId);

        return existingReview
                .flatMap(review -> reviewRepository.deleteById(reviewId)
                        .then(ServerResponse.noContent().build()));
    }
}
