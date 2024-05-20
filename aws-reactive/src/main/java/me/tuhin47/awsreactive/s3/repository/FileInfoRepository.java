package me.tuhin47.awsreactive.s3.repository;

import me.tuhin47.awsreactive.s3.domain.FileInfo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface FileInfoRepository extends ReactiveCrudRepository<FileInfo, Long> {

    Flux<FileInfo> findByFileName(String name);
}
