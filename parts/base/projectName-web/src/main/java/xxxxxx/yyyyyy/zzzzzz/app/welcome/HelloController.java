package xxxxxx.yyyyyy.zzzzzz.app.welcome;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @GetMapping(value = "/")
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        ZonedDateTime dateTime = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.LONG)
                .withLocale(locale);

        String formattedDate = dateTime.format(dateTimeFormatter);

        model.addAttribute("serverTime", formattedDate);

        return "welcome/home";
    }

}
