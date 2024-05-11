/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awssamples.s3.api.rounter;

import me.tuhin47.awssamples.s3.api.handler.AwsS3Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class AwsS3Router {

    @Bean
    public RouterFunction<ServerResponse> awsS3RouterFunction(AwsS3Handler awsS3Handler) {
        RequestPredicate uploadS3Route =
            RequestPredicates.POST("/s3/upload")
                             .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));
        RequestPredicate uploadLargeS3Route =
            RequestPredicates.POST("/s3/upload-large")
                             .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        RequestPredicate downloadFromS3Route =
            RequestPredicates.GET("/s3/download/{name}")
                             .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        RequestPredicate viewFileRoute =
            RequestPredicates.GET("/s3/view/{name}")
                             .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        RequestPredicate viewAllFromS3Route =
            RequestPredicates.GET("/s3/view-all")
                             .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        RequestPredicate viewAllFromDbRoute =
            RequestPredicates.GET("/s3/view-all-db")
                             .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        return RouterFunctions.route(uploadS3Route, awsS3Handler::upload2S3)
                              .andRoute(uploadLargeS3Route, awsS3Handler::uploadLargeFile2S3)
                              .andRoute(viewFileRoute, awsS3Handler::fileInfoFromDb)
                              .andRoute(viewAllFromDbRoute, awsS3Handler::viewAllFromDb)
                              .andRoute(viewAllFromS3Route, awsS3Handler::viewFromS3)
                              .andRoute(downloadFromS3Route, awsS3Handler::downloadFromS3);
    }
}
