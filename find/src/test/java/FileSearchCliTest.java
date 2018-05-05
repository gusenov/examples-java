import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class FileSearchCliTest {

    @Before
    public void before() {
    }

    /**
     * Метод для тестирования ключа -r.
     */
    @Test
    public void testRecursiveSearch() {
        // Рекурсивный поиск файла FileSearchCli.java в текущей папке с проектом:

        String[] args = "-r FileSearchCli.java".split(" ");
        FileSearchCli fileSearchCli = new FileSearchCli(args);

        // Проверка парсинга аргументов:
        assertTrue(fileSearchCli.isRecursiveSearch());
        assertTrue(fileSearchCli.getSearchPath().toString().endsWith("/find"));
        assertArrayEquals(new String[]{"FileSearchCli.java"}, fileSearchCli.getFilesNames());

        List<File> results = fileSearchCli.search();  // поиск...

        // Проверка результатов поиска:
        assertTrue(results.size() == 1);
        assertTrue(results.get(0).getAbsolutePath().endsWith("/find/src/main/java/FileSearchCli.java"));
    }

    /**
     * Метод для тестирования ключа -d directory.
     */
    @Test
    public void testSearchInDir() {
        // Поиск файла FileSearchCliTest.java в папке find/src/test/java:

        String currentDir = FileSystems.getDefault().getPath(".").toAbsolutePath().normalize().toString();
        Path searchPath = Paths.get(currentDir, "src", "test", "java");
        String[] args = new String[]{"-d", searchPath.toAbsolutePath().toString(), "FileSearchCliTest.java"};
        FileSearchCli fileSearchCli = new FileSearchCli(args);

        // Проверка парсинга аргументов:
        assertFalse(fileSearchCli.isRecursiveSearch());
        assertTrue(fileSearchCli.getSearchPath().toString().endsWith("/find/src/test/java"));
        assertArrayEquals(new String[]{"FileSearchCliTest.java"}, fileSearchCli.getFilesNames());

        List<File> results = fileSearchCli.search();  // поиск...

        // Проверка результатов поиска:
        assertTrue(results.size() == 1);
        assertTrue(results.get(0).getAbsolutePath().endsWith("/find/src/test/java/FileSearchCliTest.java"));

    }

}

