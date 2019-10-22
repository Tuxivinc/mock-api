package org.api.mock.services;

import org.api.mock.ctrl.ApiMockSimul;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The type Clean files.
 */
@Service
public class CleanFiles {

    private static final Logger LOG = LoggerFactory.getLogger(CleanFiles.class);

    /**
     * Schedule task using cron expression. (every 15 minutes)
     */
    @Scheduled(cron = "0 */15 * * * *")
    public void scheduleTaskUsingCronExpression() {
        LOG.debug("Run clean temporary files");

        try {
            // Création d'un fichier temporaire (pour récupération des infos)
            File fileTmp = File.createTempFile(ApiMockSimul.PREFIX_FILE_TMP, ApiMockSimul.SUFFIX_FILE_TMP);
            LocalDateTime now = LocalDateTime.now();

            // Récupération des fichiers créés par le mock
            List<File> listFileDelete = Arrays.asList(new File(fileTmp.getParent()).listFiles(file -> {
                Pattern p = Pattern.compile("^" + ApiMockSimul.PREFIX_FILE_TMP + ".*" + ApiMockSimul.SUFFIX_FILE_TMP + "$");
                return p.matcher(file.getName()).matches();
            }));

            // Suppression des fichiers de plus de 2 minutes
            listFileDelete
                    .stream()
                    .filter(f -> now.minusMinutes(2L).isAfter(LocalDateTime.ofInstant(Instant.ofEpochMilli(f.lastModified()), ZoneId.systemDefault())))
                    .forEach(f -> {
                        try {
                            Files.delete(f.toPath());
                        } catch (IOException e) {
                            LOG.error("Error when delete file {}", f.getName(), e);
                        }
                    });
        } catch (IOException e) {
            LOG.error("Error when clean files", e);
        }
    }

}
