package parsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FileParsingTest {
    ClassLoader cl = FileParsingTest.class.getClassLoader();
    @Test
    void pdfParsingTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File download =  $("a[href*='junit-user-guide-5.9.3.pdf']").download();
        PDF pdf = new PDF(download);
        Assertions.assertEquals("Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein", pdf.author);
    }

    @Test
    void xlsParsingTest() throws Exception {
        open("https://excelvba.ru/programmes/Teachers");
        File download = $("a[href*='teachers.xls']").download();
        XLS xls = new XLS(download);
        Assertions.assertTrue(
                xls.excel.getSheetAt(0).getRow(3).getCell(2).getStringCellValue()
                        .startsWith("1. Суммарное количество часов планируемое на штатную по всем разделам плана  должно \n" +
                                "составлять примерно 1500 час в год.")
        );
    }

    @Test
    void csvParsingTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("teachers.csv");
             InputStreamReader isr = new InputStreamReader(is)) {
            CSVReader csvReader = new CSVReader(isr);
            List<String[]> content = csvReader.readAll();
            Assertions.assertArrayEquals(new String[] {"Tuchs", "JUnit 5"}, content.get(0));
        }
    }
}
