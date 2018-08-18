import java.awt.*;
import java.util.Date;
import java.util.Set;
import javax.swing.*;

/** Панель на которой представлены элементы ввода свойств маршрута. */
public class RouteProps extends JPanel {

    /** Заголовок перед выпадающим списком для выбора начального пункта отправления. */
    protected final JLabel startPointTitle = new JLabel("Начальный пункт отправления: ");

    /** Выпадающий список для выбора начального пункта отправления. */
    protected final JComboBox<String> startPoint = new JComboBox<String>(new DefaultComboBoxModel<String>());

    /** Заголовок перед выпадающим списком для выбора конечного пункта прибытия. */
    protected final JLabel endPointTitle = new JLabel("Конечный пункт прибытия: ");

    /** Выпадающий список для выбора конечного пункта прибытия. */
    protected final JComboBox<String> endPoint = new JComboBox<String>(new DefaultComboBoxModel<String>());

    /** Заголовок перед cчётчиком для выбора даты/времени отправления. */
    protected final JLabel leaveTimeTitle = new JLabel("Мин. время отправления: ");

    /** Счётчик для выбора даты/времени отправления. */
    protected final JSpinner leaveTime = new JSpinner(new SpinnerDateModel());

    /** Заголовок перед cчётчиком для выбора даты/времени прибытия. */
    protected final JLabel arrivalTimeTitle = new JLabel("Макс. время прибытия: ");

    /** Счётчик для выбора даты/времени прибытия. */
    protected final JSpinner arrivalTime = new JSpinner(new SpinnerDateModel());

    /** Кнопка для выполнения некоего действия, например, запуска поиска маршрутов. */
    protected final JButton actionButton = new JButton("Найти");

    /** Маршрут свойства которого отображаются в элементах управления данной панели. */
    protected Route route = Route.createNewEmptyRoute();

    /** Конструктор по умолчанию. */
    public RouteProps() {
        super();
    }

    /** Создание графического интерфейса панели на которой представлены элементы ввода свойств маршрута. */
    public void createGUI() {
        setLayout(new GridBagLayout());  // раскладка.
        setBorderTitle("Поиск маршрутов");

        // Позиционирование компонентов в раскладке GridBagLayout:


        // Заголовок перед выпадающим списком для выбора начального пункта отправления:
        add(startPointTitle, Utils.titleConstraints(0, 1));

        // Выпадающий список для выбора начального пункта отправления:
        add(startPoint, Utils.editControlConstraints(1, 1));


        // Заголовок перед выпадающим списком для выбора конечного пункта прибытия:
        add(endPointTitle, Utils.titleConstraints(2, 1));

        // Выпадающий список для выбора конечного пункта прибытия:
        add(endPoint, Utils.editControlConstraints(3, 1));


        // Заголовок перед cчётчиком для выбора даты/времени отправления:
        add(leaveTimeTitle, Utils.titleConstraints(0, 2));

        Utils.configDateTimeSpinner(leaveTime);  // сконфигурировать счётчик для выбора даты/времени отправления.

        // Счётчик для выбора даты/времени отправления:
        add(leaveTime, Utils.editControlConstraints(1, 2));


        // Заголовок перед cчётчиком для выбора даты/времени прибытия:
        add(arrivalTimeTitle, Utils.titleConstraints(2, 2));

        Utils.configDateTimeSpinner(arrivalTime);  // сконфигурировать счётчик для выбора даты/времени прибытия.

        // Счётчик для выбора даты/времени прибытия:
        add(arrivalTime, Utils.editControlConstraints(3, 2));


        // Кнопка для выполнения некоего действия, например, запуска поиска маршрутов:

        GridBagConstraints constraints;
        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.insets = new Insets(4, 4, 4, 4);
        constraints.anchor = GridBagConstraints.LINE_END;
        add(actionButton, constraints);
    }

    /**
     * Установка рамки с заголовком для данной панели.
     *
     * @param caption  заголовок.
     */
    public void setBorderTitle(String caption) {
        setBorder(BorderFactory.createTitledBorder(caption));
    }

    /**
     * Получить маршрут свойства которого в данный момент отображаются в панели.
     *
     * @return  экземпляр класса Route.
     */
    public Route getRoute() {
        return route;
    }

    /** Обновить данные маршрута руководствуюясь данными из элементов управления. */
    public void updateRoute() {
        route.setStartPoint((String)startPoint.getSelectedItem());
        route.setLeaveTime((Date)leaveTime.getValue());
        route.setEndPoint((String)endPoint.getSelectedItem());
        route.setArrivalTime((Date)arrivalTime.getValue());
    }

    /**
     * Установить маршрут свойства которого необходимо показать в данной панели.
     *
     * @param route  маршрут.
     */
    public void setRoute(Route route) {
        this.route = route;

        startPoint.setSelectedItem(route.getStartPoint());
        endPoint.setSelectedItem(route.getEndPoint());
        leaveTime.setValue(route.getLeaveTime());
        arrivalTime.setValue(route.getArrivalTime());
    }

    /**
     * Получить ссылку на кнопку выполняющую некое действие.
     *
     * @return  ссылка на кнопку JButton.
     */
    public JButton getActionButton() {
        return actionButton;
    }

    /**
     * Установить множества пунктов отправления для соответствующего выпадающего списка.
     *
     * @param items  множество пунктов отправления.
     */
    public void setStartPoints(Set<String> items) {
        final DefaultComboBoxModel model = (DefaultComboBoxModel)startPoint.getModel();

        // Добавление пунктов отправления, которые есть в таблице марщрутов:
        for (String item : items) {
            if (model.getIndexOf(item) == -1)
                startPoint.addItem(item);
        }

        // Удаление пунктов отправления, которых больше нет в таблице марщрутов:
        for (int i = 0; i < model.getSize(); ++i) {
            if (!items.contains((String)model.getElementAt(i))) {
                model.removeElementAt(i);
            }
        }
    }

    /**
     * Установить множества пунктов прибытия для соответствующего выпадающего списка.
     *
     * @param items  множество пунктов прибытия.
     */
    public void setEndPoints(Set<String> items) {
        final DefaultComboBoxModel model = (DefaultComboBoxModel)endPoint.getModel();

        // Добавление пунктов прибытия, которые есть в таблице марщрутов:
        for (String item : items) {
            if (model.getIndexOf(item) == -1)
                endPoint.addItem(item);
        }

        // Удаление пунктов прибытия, которых больше нет в таблице марщрутов:
        for (int i = 0; i < model.getSize(); ++i) {
            if (!items.contains((String)model.getElementAt(i))) {
                model.removeElementAt(i);
            }
        }
    }

}
