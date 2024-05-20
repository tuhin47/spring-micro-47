package me.tuhin47.awsreactive.s3.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.awsreactive.s3.domain.FileInfo;
import me.tuhin47.awsreactive.s3.repository.FileInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileInfoService {

    private final FileInfoRepository fileInfoRepository;

    public Flux<FileInfo> saveFilesInfo(Flux<FileInfo> fileInfo) {
        log.info("Saving file info: '{}'", fileInfo);
        return fileInfoRepository.saveAll(fileInfo);
    }

    public Flux<FileInfo> findAllFiles() {
        log.info("Retrieving all files info");
        return fileInfoRepository.findAll();
    }

    public Flux<FileInfo> getFileByName(String fileName) {
        log.info("Retrieving file by name '{}'", fileName);
        return fileInfoRepository.findByFileName(fileName);
    }
}
