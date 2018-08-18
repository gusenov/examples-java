import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** Главный класс. */
public class Main {

    /** Главный фрейм. */
    private final JFrame mainFrame = new JFrame("Билетная касса автовокзала");

    /** Главная панель. */
    private final JPanel mainPanel = new JPanel();

    /** Раскладка для главной панели. */
    private final BorderLayout mainLayout = new BorderLayout();

    /** Заголовок приложения. */
    private final JLabel appTitle = new JLabel("Билетная касса автовокзала");

    /** Панель вкладок. */
    private final JTabbedPane tabbedPane = new JTabbedPane();

    /** Панель управления маршрутами. */
    private final RouteManager routesPanel = new RouteManager();

    /** Панель для поиска маршрутов и формирования билетов к ним. */
    private final TicketForm ticketSearchPanel = new TicketForm();

    /** Конструктор по умолчанию. */
    public Main() {
        routesPanel.init();
        ticketSearchPanel.init();

        // Поведение при переключении вкладок:
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                onTabSelectionChanged(tabbedPane.getSelectedIndex());
            }
        });

        // При закрытии окна нужно завершать программу:
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);  // выход.
            }
        });
    }

    /** Создать и показать графический интерфейс приложения. */
    public void createAndShowGUI() {
        mainPanel.setLayout(mainLayout);  // установка раскладки главного окна.
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Font currentFont = appTitle.getFont();  // текущиий шрифт.
        appTitle.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), 24));  // увеличиваем шрифт.
        appTitle.setBorder(new EmptyBorder(10, 10, 10, 10));  // отступы от краёв окна.
        mainPanel.add(appTitle, BorderLayout.PAGE_START);

        // Панель управления маршрутами:
        routesPanel.createGUI();
        tabbedPane.addTab("Маршруты", null, routesPanel, "Добавление новых маршрутов, стоимости и времени");

        // Панель для поиска маршрутов и формирования билетов к ним:
        ticketSearchPanel.createGUI();
        tabbedPane.addTab("Формирование билетов", null, ticketSearchPanel, "Выбор из имеющихся маршрутов для формирования билета");

        mainPanel.add(tabbedPane, BorderLayout.CENTER);  // добавление панели вкладок на форму.

        mainFrame.setSize(1024, 768);  // размеры окна.
        mainFrame.setResizable(false);  // окно не доступно для изменения размеров.
        mainFrame.add(mainPanel);

        Utils.centreWindow(mainFrame);  // разместить главное окно.

        mainFrame.setVisible(true);  // показать фрейм.
    }

    /**
     * Главный метод.
     *
     * @param args  аргументы.
     */
    public static void main(String[] args) {
        // Не будем использовать главный поток для GUI-операций.
        // Вместо этого запустим метод createGUI() в потоке обработки событий.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main main = new Main();
                main.createAndShowGUI();
            }
        });
    }

    /**
     * Метод вызывающийся при переключении вкладок.
     *
     * @param selectedIndex  индекс вкладки.
     */
    private void onTabSelectionChanged(int selectedIndex) {
        // При переключении вкладок менять также и заголовок окна приложения:
        mainFrame.setTitle("Билетная касса автовокзала - "
                + tabbedPane.getTitleAt(selectedIndex));

        switch (selectedIndex) {
            case 0:
                break;
            case 1:
                // При переходе на панель поиска маршрутов загрузить новые данные из файла:
                ticketSearchPanel.enableRoutePropsPanel(ticketSearchPanel.loadData() > 0);
                break;
        }
    }

}