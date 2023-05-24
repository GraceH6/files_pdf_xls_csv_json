import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FileDownloadTest {
    @Test
    void downloadTest() throws Exception {
        open("https://github.com/qa-guru/niffler/blob/master/README.md");
        File download =  $("a[href*='/qa-guru/niffler/raw/master/README.md']").download();

        try (InputStream is = new FileInputStream(download)) {
            byte[] bytes = is.readAllBytes();
            String FileAsString = new String(bytes, StandardCharsets.UTF_8);
            Assertions.assertTrue(FileAsString.contains("Технологии, использованные в Niffler"));
        }
    }

    @Test
    void uploadTest() throws Exception {
        open("https://tus.io/demo");
        $("input[type='file']").uploadFromClasspath("coffee.png");
        $("._heading_1as67_21").shouldHave(text("The upload is complete!"));
    }
}
