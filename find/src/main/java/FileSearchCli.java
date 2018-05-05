import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FileSearchCli {

    /** Аргументы командной строки. */
    private String[] args = null;

    /** Индексы ключей (аргументов начинающихся с символа минуса). */
    private Map<String, Integer> switchIndexes = new HashMap<String, Integer>();

    /** Множество обработанных индексов, чтобы избежать повторной обработки. */
    private Set<Integer> takenIndexes = new TreeSet<Integer>();

    /** Флаг рекурсивного поиска. */
    private boolean isRecursiveSearch;

    /** Папка в которой нужно искать файлы. */
    private Path searchPath;

    /** Имена файлов, которые нужно найти. */
    private String[] filesNames;

    /** Результаты поиска. */
    private List<File> results = new ArrayList<File>();

    /**
     * Конструктор.
     *
     * @param args  аргументы командной строки.
     */
    public FileSearchCli(String[] args) {
        parse(args);
    }

    /**
     * Парсинг аргументов командной строки.
     * Можно вызывать этот метод для перенастройки экземпляра данного класса.
     *
     * @param arguments  аргументы командной строки.
     */
    public void parse(String[] arguments) {
        args = arguments;

        switchIndexes.clear();

        takenIndexes.clear();

        // Запоминание индексов ключей (аргументов, которые начинаются с символа '-'):
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                switchIndexes.put(args[i], i);
                takenIndexes.add(i);  // запоминаем, что данный аргумент уже обработан.
            }
        }

        isRecursiveSearch = false;  // по умолчанию рекурсивный поиск отключен.

        if (switchPresent("-r")) {  // если присутствует ключ -r, то:
            isRecursiveSearch = true;  // включаем рекурсивный поиск.
        }

        // По умолчанию местом поиска считается текущая папка:
        searchPath = FileSystems.getDefault().getPath(".").toAbsolutePath().normalize();

        if (switchPresent("-d")) {  // если присутствует ключ -d, то:
            String directory = switchValue(("-d"));  // из аргументов получаем путь к месту поиска.
            searchPath = Paths.get(directory);  // задаём новое место для поиска файлов.
        }

        filesNames = this.targets();  // получаем из аргументов список файлов для поиска.
    }

    /**
     * Метод для проверки наличия среди аргументов заданного ключа с префиксом '-'.
     *
     * @param switchName  заданный аргумент.
     * @return  true - присутствует, false - отсутствует.
     */
    private boolean switchPresent(String switchName) {
        return switchIndexes.containsKey(switchName);
    }

    /**
     * Метод для получения аргумента следующего за ключем (аргументом с префиксом '-').
     *
     * @param switchName  заданный аргумент с префиксом '-'.
     * @return  аргумент следующий за аргументом с префиксом '-' или null, если нет заданного аргумента с префиксом '-'.
     */
    private String switchValue(String switchName) {
        if (!switchIndexes.containsKey(switchName))
            return null;

        int switchIndex = switchIndexes.get(switchName);

        if (switchIndex + 1 < args.length) {
            takenIndexes.add(switchIndex + 1);
            return args[switchIndex + 1];
        }

        return null;
    }

    /**
     * Метод, который возвращает все аргументы без префикса '-'.
     *
     * @return  строковый массив аргументов без префикса '-'.
     */
    private String[] targets() {
        String[] targetArray = new String[args.length - takenIndexes.size()];
        int targetIndex = 0;
        for (int i = 0; i < args.length; i++) {
            if (!takenIndexes.contains(i)) {
                targetArray[targetIndex++] = args[i];
            }
        }
        return targetArray;
    }

    /**
     * Рекурсивный метод для обхода папок и поиска заданных файлов.
     *
     * @param root  начальная папка.
     */
    private void walk(File root) {
        File[] list = root.listFiles();

        if (list == null) {
            return;
        }

        for (File f : list) {
            if (f.isDirectory()) {
                if (isRecursiveSearch) {  // если поиск рекурсивный, то:
                    walk(f);  // заходим во все внутренние папки.
                }
            } else {
                // Сверка имён искомых файлов с именем очередного файла:
                for (String fileName : filesNames) {
                    if (fileName.compareTo(f.getName()) == 0) {
                        this.results.add(f.getAbsoluteFile());
                    }
                }
            }
        }

    }

    /**
     * Метод для запуска поиска.
     *
     * @return  коллекция искомых файлов.
     */
    public List<File> search() {
        results.clear();
        walk(searchPath.toFile());
        return this.results;
    }

    // Геттеры для приватных свойств:

    public boolean isRecursiveSearch() {
        return isRecursiveSearch;
    }

    public Path getSearchPath() {
        return searchPath;
    }

    public String[] getFilesNames() {
        return filesNames;
    }

    /**
     * Главный метод являющийся точкой входа в программу.
     *
     * @param args  аргументы командной строки.
     */
    public static void main(String[] args) {
        FileSearchCli fileSearchCli = new FileSearchCli(args);

        List<File> results = fileSearchCli.search();  // поиск...

        // Вывод результатов поиска:
        for (File file : results) {
            System.out.println(file.toString());
        }
    }
}
