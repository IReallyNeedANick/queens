package compileworks.queens;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

/**
 * @author Boris Marn
 */
@SpringBootApplication
public class QueensMain {

    public static void main(String[] args) {
        SpringApplication.run(QueensMain.class, args);
    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("queens-shell:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
