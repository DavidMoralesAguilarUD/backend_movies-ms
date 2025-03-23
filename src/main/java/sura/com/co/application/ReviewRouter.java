package sura.com.co.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import sura.com.co.domain.service.ReviewHandler;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReviewRouter {

    @Bean
    public RouterFunction<ServerResponse> reviewsRouter(ReviewHandler reviewsHandler) {
        return route()
                .nest(path("/v1/reviews"), builder -> {
                    builder.POST(request -> reviewsHandler.addReview(request))
                            .GET(request -> reviewsHandler.getReviews(request))
                            .PUT("/{id}",request -> reviewsHandler.updateReviews(request))
                            .DELETE("/{id}", request -> reviewsHandler.deleteReviews(request));
                })
                .GET("/v1/helloworld", request ->
                        ServerResponse.ok()
                                .header("Content-Type", "application/json")
                                .bodyValue(Map.of("message", "helloworld")))

                .build();
    }
}