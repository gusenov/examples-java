import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/** Панель с таблицей маршрутов. */
public class RouteTable extends JPanel {

    /** Таблица маршрутов. */
    private final JTable table = new JTable(new RouteTableModel());

    /** Панель с полосой прокрутки для помещения в неё таблицы маршрутов. */
    private final JScrollPane scrollPane = new JScrollPane(table);

    /** Контейнер для хранения кнопок под таблицей маршрутов. */
    private final Box buttonsBox = Box.createHorizontalBox();

    /** Конструктор по умолчанию. */
    public RouteTable() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /** Создание графического интерфейса панели с таблицей маршрутов. */
    public void createGUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));  // раскладка.
        setBorderTitle("Все маршруты: ");  // рамка с заголовком.

        add(Box.createRigidArea(new Dimension(0,8)));  // отступ.

        // Добавление в графический интерфейс полосы прокрутки:
        scrollPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(8, 8, 8, 8),
                scrollPane.getBorder()));
        scrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(scrollPane);

        add(Box.createRigidArea(new Dimension(0,8)));  // отступ.

        // Добавление в графический интерфейс кнопок под таблицей маршрутов:
        buttonsBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(buttonsBox);

        add(Box.createRigidArea(new Dimension(0,8)));  // отступ.
    }

    /**
     * Установка вокруг панели рамки с заголовком.
     *
     * @param caption  заголовок.
     */
    public void setBorderTitle(String caption) {
        setBorder(BorderFactory.createTitledBorder(caption));
    }

    /**
     * Получение контейнера для добавления в него компонентов,
     * которые будут размещены пот таблицей маршрутов.
     *
     * @return  контейнер.
     */
    public Box getButtonsBox() {
        return buttonsBox;
    }

    /**
     * Получение ссылки на таблицу маршрутов.
     *
     * @return  ссылка на таблицу маршрутов.
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Получение ссылки на табличную модель маршрутов.
     *
     * @return  ссылка на табличную модель маршрутов.
     */
    public RouteTableModel getRouteTableModel() {
        return (RouteTableModel)table.getModel();
    }

}
