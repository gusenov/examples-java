import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/** Диалоговое окно с билетом и кнопками для печати и сохранения билета. */
public class Ticket extends JDialog {

    /** Главная панель на которой расположены холст и кнопки. */
    private final JPanel mainPanel = new JPanel();

    /** Раскладка для главной панели. */
    private final SpringLayout mainLayout = new SpringLayout();

    /** Панель выступающая в роли холста на котором рисуется билет. */
    private final TicketCanvas canvas = new TicketCanvas();

    /** Кнопка для печати билета. */
    private final JButton printButton = new JButton("Печать…");

    /** Кнопка для сохранения билета в виде PNG-изображения. */
    private final JButton saveButton = new JButton("Сохранить как изображение…");

    /** Выборщик файла для сохранения в нём изображения билета. */
    private final JFileChooser fileChooser = new JFileChooser();

    /** Формат даты для использования даты отправления в имени сохраняемого билета. */
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm");

    /** Текущий маршрут для которого рисуется билет. */
    private Route route;

    /**
     * Конструктор.
     *
     * @param frame  родительский фрейм.
     * @param s      заголовок окна.
     * @param b      должно ли окно быть модальным?
     */
    public Ticket(Frame frame, String s, boolean b) {
        super(frame, s, b);  // предварительный вызов конструктора суперкласса.

        getContentPane().add(mainPanel);  // добавление главной панели в диалоговое окно.
        setSize(640, 480);  // установка размеров диалогового окна.
        setResizable(false);  // отключить возможность изменения размеров диалогового окна.

        // Фильтр для выборщика файлов, который отображает только PNG-файлы:
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);  // не показывать все типы файлов в выборщике (нужны только PNG).

        // Программирование кнопки "Сохранить как изображение…":
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                // Установка имени по умолчанию для сохраняемого файла в формате:
                // Билет Город-Город на ##-##-#### ##-##
                fileChooser.setSelectedFile(new File("Билет " + route.getStartPoint() + "-" + route.getEndPoint()
                        + " на " + dateFormat.format(route.getLeaveTime()) + ".png"));

                // Показать окно для выбора целевого файла:
                if (fileChooser.showSaveDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();  // путь к целевому файлу для сохранения в него.

                    // При необходимости добавить на конце имени файла расришение ".png":
                    if (!file.toString().toLowerCase().endsWith(".png")) {
                        file = new File(file.toString() + ".png");
                    }

                    // Сохранение графического представления панели canvas в выбранный файл:
                    BufferedImage bi = new BufferedImage(canvas.getSize().width, canvas.getSize().height,
                            BufferedImage.TYPE_INT_ARGB);
                    Graphics g = bi.createGraphics();
                    canvas.paint(g);
                    g.dispose();
                    try {
                        ImageIO.write(bi,"png", file);
                    } catch (Exception e) {
                        // Вывод в стандартный поток ошибок сообщения об ошибке:
                        System.err.println("//---------------------------------------------------------------------");
                        System.err.println("Exception message: " + e.getMessage());
                        System.err.println("//---------------------------------------------------------------------");
                        System.err.println("Exception message with class name: " + e.toString());
                        System.err.println("//---------------------------------------------------------------------");
                        e.printStackTrace();
                    }

                }

            }
        });

        // Программирование кнопки "Печать…":
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Utils.printComponenet(canvas);
            }
        });
    }

    /** Метод для создания графического представления данного диалогового окна с билетом. */
    public void createGUI() {
        mainPanel.setLayout(mainLayout);  // установка раскладки для окна.

        mainPanel.add(canvas);  // добавить в окно холст.
        mainPanel.add(printButton);  // добавить в окно кнопку для печати билета.
        mainPanel.add(saveButton);  // добавить в окно кнопку для сохранения билета в PNG-файл.

        // Позиционирование холста:
        mainLayout.putConstraint(SpringLayout.NORTH, canvas, 5, SpringLayout.NORTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.WEST, canvas, 5, SpringLayout.WEST, mainPanel);
        mainLayout.putConstraint(SpringLayout.EAST, canvas, -5, SpringLayout.EAST, mainPanel);
        SpringLayout.Constraints canvasCst = mainLayout.getConstraints(canvas);
        canvasCst.setHeight(Spring.constant(400));

        // Позиционирование кнопки для печати:
        mainLayout.putConstraint(SpringLayout.NORTH, printButton, 5, SpringLayout.SOUTH, canvas);
        mainLayout.putConstraint(SpringLayout.SOUTH, printButton, -5, SpringLayout.SOUTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.WEST, printButton, 5, SpringLayout.WEST, mainPanel);

        // Позиционирование кнопки для сохранения в графический файл:
        mainLayout.putConstraint(SpringLayout.NORTH, saveButton, 5, SpringLayout.SOUTH, canvas);
        mainLayout.putConstraint(SpringLayout.SOUTH, saveButton, -5, SpringLayout.SOUTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.WEST, saveButton, 5, SpringLayout.EAST, printButton);
    }

    /**
     * Установка маршрута для которого рисуется билет.
     *
     * @param route  маршрут.
     */
    public void setRoute(Route route) {
        this.route = route;
        canvas.setRoute(route);
    }

}
