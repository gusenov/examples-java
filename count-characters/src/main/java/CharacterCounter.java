import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;

/**
 * Простейшее графическое приложение
 * для подсчета количества символов
 * и количества вхождений заданного символа
 * в текстовом файле.
 */
public class CharacterCounter {
    // Создание компонентов:

    /** Главный фрейм. */
    private final JFrame mainFrame = new JFrame("Счётчик символов");

    private final JPanel panel = new JPanel();
    private final SpringLayout layout = new SpringLayout();

    private final JLabel pathToFileLabel = new JLabel("Путь к файлу:");
    private final JTextField pathToFileTextField = new JTextField(20);
    private final JButton pathToFileButton = new JButton("Открыть");

    private final JFileChooser fileChooser = new JFileChooser();
    private final FileNameExtensionFilter filter =
            new FileNameExtensionFilter("Тестовые файлы", "txt", "text");

    private final JLabel countOfCharactersDescriptionLabel =
            new JLabel("Количество всех символов = ");
    private final JLabel countOfCharactersLabel = new JLabel("0");

    private final JLabel characterLabel = new JLabel("Задайте символ:");
    private final JTextField characterTextField = new JTextField(5);

    private final JLabel countOfCharacterDescriptionLabel =
            new JLabel("Количество вхождений заданного символа:");
    private final JLabel countOfCharacterLabel = new JLabel("0");


    public void createAndShowGUI() {
        mainFrame.setSize(400,240);

        // Что будет происходить при закрытии фрейма?
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setLayout(layout);

        // Фильтр для отображения только текстовых файлов:
        fileChooser.setFileFilter(filter);

        // Обработчик события нажатия на кнопку "Открыть":
        pathToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                // Показать файловое диалоговое окно для открытия текстового файла:
                int returnVal = fileChooser.showOpenDialog(mainFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    // Вывести в текстовое поле имя открытого файла:
                    pathToFileTextField.setText(file.getName());

                    // Подсчитать и вывести количество всех символов:
                    outputResult(null);
                }
            }
        });

        pathToFileTextField.setEditable(false);

        // Обработчик события изменения в текстовом поле заданного для подсчёта символа:
        characterTextField.getDocument().addDocumentListener(new DocumentListener() {
            private void countChar() {
                if (characterTextField.getText().length() > 0) {
                    // Взять первый символ из текстового поля для подсчёта таких символов в файле:
                    Character filterCh = characterTextField.getText().charAt(0);

                    // Подсчёт и вывод количества вхождений заданного символа:
                    outputResult(filterCh);
                } else {

                    countOfCharacterLabel.setText("0");
                }
            }

            // При любом изменении в текстовом поле, вызывать метод countChar().

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                countChar();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                countChar();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                countChar();
            }
        });


        // Добавление компонентов на фрейм:

        panel.add(pathToFileLabel);
        panel.add(pathToFileTextField);
        panel.add(pathToFileButton);

        panel.add(countOfCharactersDescriptionLabel);
        panel.add(countOfCharactersLabel);

        panel.add(characterLabel);
        panel.add(characterTextField);

        panel.add(countOfCharacterDescriptionLabel);
        panel.add(countOfCharacterLabel);

        mainFrame.add(panel);


        // Позиционирование компонентов:

        // Сигнатура и описание метода putConstraint():
        // void putConstraint(String e1, Component c1, int pad, String e2, Component c2)
        // Links edge e1 of component c1 to edge e2 of component c2,
        // with a fixed distance between the edges.

        layout.putConstraint(SpringLayout.WEST, pathToFileLabel, 5,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, pathToFileLabel, 5,
                SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, pathToFileTextField, 5,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, pathToFileTextField, 5,
                SpringLayout.SOUTH, pathToFileLabel);

        layout.putConstraint(SpringLayout.WEST, pathToFileButton, 5,
                SpringLayout.EAST, pathToFileTextField);
        layout.putConstraint(SpringLayout.NORTH, pathToFileButton, 0,
                SpringLayout.NORTH, pathToFileTextField);


        layout.putConstraint(SpringLayout.WEST, countOfCharactersDescriptionLabel, 5,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, countOfCharactersDescriptionLabel, 10,
                SpringLayout.SOUTH, pathToFileTextField);

        layout.putConstraint(SpringLayout.WEST, countOfCharactersLabel, 5,
                SpringLayout.EAST, countOfCharactersDescriptionLabel);
        layout.putConstraint(SpringLayout.NORTH, countOfCharactersLabel, 0,
                SpringLayout.NORTH, countOfCharactersDescriptionLabel);


        layout.putConstraint(SpringLayout.WEST, characterLabel, 5,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, characterLabel, 10,
                SpringLayout.SOUTH, countOfCharactersDescriptionLabel);

        layout.putConstraint(SpringLayout.WEST, characterTextField, 5,
                SpringLayout.EAST, characterLabel);
        layout.putConstraint(SpringLayout.NORTH, characterTextField, 0,
                SpringLayout.NORTH, characterLabel);


        layout.putConstraint(SpringLayout.WEST, countOfCharacterDescriptionLabel, 5,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, countOfCharacterDescriptionLabel, 10,
                SpringLayout.SOUTH, characterTextField);

        layout.putConstraint(SpringLayout.WEST, countOfCharacterLabel, 5,
                SpringLayout.EAST, countOfCharacterDescriptionLabel);
        layout.putConstraint(SpringLayout.NORTH, countOfCharacterLabel, 0,
                SpringLayout.NORTH, countOfCharacterDescriptionLabel);


        mainFrame.setVisible(true);  // показать фрейм.
    }

    /**
     * Метод для подсчёта количества символов в файле.
     * @param file          файл.
     * @param filterCh      символ для фильтрации, если null, то считаются все символы.
     * @return              количество символов в файле.
     * @throws IOException  может быть ошибка ввода.
     */
    private static Integer countCharactersInFile(File file, Character filterCh) throws IOException {
        int result = 0;  // результирующее количество символов.

        Charset encoding = Charset.defaultCharset();  // кодировка по умолчанию.
        try (InputStream in = new FileInputStream(file);
            Reader reader = new InputStreamReader(in, encoding);
            Reader buffer = new BufferedReader(reader)) {  // буфер для эффективности.
                int r;
                while ((r = buffer.read()) != -1) {  // чтение буфера.
                    char ch = (char) r;

                    // Если символ для фильтрации не задан, то считаем все символы:
                    if (filterCh == null) {
                        result++;
                    } else if (filterCh == ch) {  // в противном случае, только заданные символы:
                        result++;
                    }

                }
            };

        return result;
    }

    /**
     * Метод для вывода результатов подсчёта символов.
     * @param filterCh  символ для фильтрации, если null, то выводится количество всех символов.
     */
    private void outputResult(Character filterCh) {
        File file = fileChooser.getSelectedFile();  // текстовый файл.
        Integer cnt = 0;  // количество символов.

        try {
            // Вызов метода для подсчёта количества символов в файле:
            cnt = countCharactersInFile(file, filterCh);
        } catch (IOException e) {
            // Если произошла ошибка ввода, то показать сообщение об ошибке:
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        if (filterCh == null) {
            // Количество всех символов:
            countOfCharactersLabel.setText(cnt.toString());
        } else {
            // Количество вхождений заданного символа:
            countOfCharacterLabel.setText(cnt.toString());
        }
    }

    /**
     * Главный метод.
     * Точка входу в программу.
     * @param args  аргументы переданные в программу.
     */
    public static void main(String[] args) {
        // Не будем использовать главный поток для GUI-операций.
        // Вместо этого запустим метод createAndShowGUI() в потоке обработки событий.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CharacterCounter characterCounter = new CharacterCounter();
                characterCounter.createAndShowGUI();
            }
        });
    }
}
