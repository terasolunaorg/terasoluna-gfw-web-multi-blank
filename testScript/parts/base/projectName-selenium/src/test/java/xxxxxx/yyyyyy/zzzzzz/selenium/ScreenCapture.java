package xxxxxx.yyyyyy.zzzzzz.selenium;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Class that saves the screen capture as evidence.
 */
public class ScreenCapture {

    private static final Logger logger = LoggerFactory.getLogger(ScreenCapture.class);

    private final boolean enableCapture;

    private File evidenceSavingDirectory;

    private final AtomicInteger sequence = new AtomicInteger(0);

    public ScreenCapture(boolean enableCapture) {
        this.enableCapture = enableCapture;
    }

    public void setUp(File evidenceSavingDirectory) {
        sequence.set(0);
        this.evidenceSavingDirectory = evidenceSavingDirectory;
    }

    public void save(WebDriver webDriver) {
        save(webDriver, null);
    }

    /**
     * Save the screen capture if capture is enabled.
     * @param webDriver WebDriver to capture
     * @param subTitle Suffix of the file name
     */
    public void save(WebDriver webDriver, String subTitle) {
        if (!enableCapture) {
            return;
        }
        saveForced(webDriver, subTitle);
    }

    /**
     * Save the screen capture regardless of the setting.
     * @param webDriver WebDriver to capture
     * @param subTitle Suffix of the file name
     */
    public void saveForced(WebDriver webDriver, String subTitle) {

        if (!(webDriver instanceof TakesScreenshot takesScreenshot)) {
            logger.warn("WebDriver is not supported screenshot. WebDriver is {}.", webDriver);
            return;
        }

        subTitle = StringUtils.hasText(subTitle) ? "-" + subTitle : "";

        byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);

        int sequenceNo = sequence.incrementAndGet();
        String evidenceFile = String.format("screen_capture_%03d%s.png", sequenceNo, subTitle);

        try {
            Files.createDirectories(evidenceSavingDirectory.toPath());
            Files.write(new File(evidenceSavingDirectory, evidenceFile).toPath(), screenshot);
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

}
