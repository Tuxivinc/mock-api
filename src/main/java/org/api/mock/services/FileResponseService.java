package org.api.mock.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FileResponseService {

    @Value("${path.mock.response}")
    private String pathMockResponse;

    private static final Logger LOG = LoggerFactory.getLogger(FileResponseService.class);

    public Optional<String> getContentFile(String filename) throws IOException {
        LOG.debug("Get File {}/{}", pathMockResponse, filename);
        try (Stream<Path> files = Files.list(Path.of(pathMockResponse))) {
            return files
                    .filter(f -> !f.toFile().isDirectory())
                    .filter(f -> f.toFile().getName().equals(filename))
                    .map(file -> {
                        try {
                            return Files.readString(Paths.get(file.toUri()));
                        } catch (IOException e) {
                            LOG.error("Cannot read file {}", file.getFileName());
                            return null;
                        }
                    })
                    .findFirst();
        }
    }

    public String addFile(String filename, String content) throws IOException {
        LOG.debug("add File {}/{} with content {}", pathMockResponse, filename, content);
        Files.writeString(Path.of(pathMockResponse, "/", filename), content, Charset.defaultCharset(), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        return content;
    }

}
