package reportHelpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadmeUpdater {
    public static String readReadme() throws IOException {
        Path path = Path.of("./README.md");
        String s = Files.readString(path);
        return s;
    }

    public static String updateReadme(String readme, TestResults results) throws IOException {

            String startMarker = "<!-- TEST-RESULTS:START -->";
            String endMarker = "<!-- TEST-RESULTS:END -->";

            int start = readme.indexOf(startMarker);
            int end = readme.indexOf(endMarker);

            if (start == -1 || end == -1 || end < start) {
                throw new IOException("README markers not found or incorrectly positioned");
            }

            String before = readme.substring(0, start + startMarker.length());

        String newContent =
                "\n| Показатель | Количество |\n" +
                        "|---|---:|\n" +
                        "| Всего тестов | " + results.getTotal() + " |\n" +
                        "| Успешно | " + results.getPassed() + " |\n" +
                        "| Ошибки | " + results.getFailed() + " |\n" +
                        "| Пропущено | " + results.getSkipped() + " |\n";

            String after = readme.substring(end);

            return before + newContent + after;
        }

    public static void writeReadme(String content) throws IOException {
        Path path = Path.of("./README.md");
        Files.writeString(path, content);
    }

}