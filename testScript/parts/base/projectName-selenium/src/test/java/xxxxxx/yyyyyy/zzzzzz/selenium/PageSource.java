package xxxxxx.yyyyyy.zzzzzz.selenium;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Class that saves the page source as evidence.
 */
public class PageSource {

    private static final Logger logger = LoggerFactory.getLogger(PageSource.class);

    private final boolean enablePageSource;

    private File evidenceSavingDirectory;

    private final AtomicInteger sequence = new AtomicInteger(0);

    public PageSource(boolean enablePageSource) {
        this.enablePageSource = enablePageSource;
    }

    public void setUp(File evidenceSavingDirectory) {
        sequence.set(0);
        this.evidenceSavingDirectory = evidenceSavingDirectory;
    }

    public void save(WebDriver webDriver) {
        save(webDriver, null);
    }

    /**
     * Save the page source if saving page source is enabled.
     * @param webDriver WebDriver to get the page source
     * @param subTitle Suffix of the file name
     */
    public void save(WebDriver webDriver, String subTitle) {
        if (!enablePageSource) {
            return;
        }
        saveForced(webDriver, subTitle);
    }

    /**
     * Save the page source regardless of the setting.
     * @param webDriver WebDriver to get the page source
     * @param subTitle Suffix of the file name
     */
    public void saveForced(WebDriver webDriver, String subTitle) {

        subTitle = StringUtils.hasText(subTitle) ? "-" + subTitle : "";

        int sequenceNo = sequence.incrementAndGet();
        String evidenceFile = String.format("page_source_%03d%s.txt", sequenceNo, subTitle);

        try {
            Files.createDirectories(evidenceSavingDirectory.toPath());
            Files.writeString(new File(evidenceSavingDirectory, evidenceFile).toPath(),
                    webDriver.getPageSource(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

}
