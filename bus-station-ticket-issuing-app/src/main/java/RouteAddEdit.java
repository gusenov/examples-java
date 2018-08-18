import java.util.Date;
import javax.swing.*;

/** Панель для добавления/редактирования маршрутов. */
public class RouteAddEdit extends RouteProps {

    /** Заголовок перед cчётчиком для задания номера маршрута. */
    private final JLabel routeNumberTitle = new JLabel("Номер маршрута: ");

    /** Счётчик для задания номера маршрута. */
    private final JSpinner routeNumber = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

    /** Заголовок перед cчётчиком для задания стоимости билета. */
    private final JLabel ticketPriceTitle = new JLabel("Стоимость билета (руб.): ");

    /** Счётчик для задания стоимости билета. */
    private final JSpinner ticketPrice = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

    /** Состояние панели. */
    private RouteAddEditState state;

    /** Конструктор. */
    public RouteAddEdit() {
        super();  // вызов конструктора суперкласса.
    }

    /** Создание графического интерфейса. */
    public void createGUI() {
        super.createGUI();

        setBorderTitle("Добавление / редактирование маршрута: ");

        leaveTimeTitle.setText("Время отправления: ");
        arrivalTimeTitle.setText("Время прибытия: ");

        // Вкл. возможность редактирования пунктов отправления и прибытия:
        startPoint.setEditable(true);
        endPoint.setEditable(true);

        // Заголовок перед cчётчиком для задания номера маршрута:
        add(routeNumberTitle, Utils.titleConstraints(0, 3));

        // Счётчик для задания номера маршрута:
        add(routeNumber, Utils.editControlConstraints(1, 3));
        Utils.configNumberSpinner(routeNumber);  // сконфигурировать счётчик для задания номера маршрута.

        // Заголовок перед cчётчиком для задания стоимости билета:
        add(ticketPriceTitle, Utils.titleConstraints(0, 4));

        // Счётчик для задания стоимости билета:
        add(ticketPrice, Utils.editControlConstraints(1, 4));
        Utils.configNumberSpinner(ticketPrice);  // сконфигурировать счётчик для задания стоимости билета.

        actionButton.setText("Сохранить");

        Utils.enableComponent(this, false);
    }

    /** Зафиксировать введённые данные по маршруту. */
    public void updateRoute() {
        super.updateRoute();

        route.setRouteNumber((Integer)routeNumber.getValue());
        route.setTicketPrice((Integer)ticketPrice.getValue());
    }

    /**
     * Установить маршрут свойства которого необходимо отредактировать в данной панели.
     *
     * @param route  маршрут.
     */
    public void setRoute(Route route) {
        super.setRoute(route);

        routeNumber.setValue(route.getRouteNumber());
        ticketPrice.setValue(route.getTicketPrice());
    }

    /**
     * Получить состояние панели.
     *
     * @return  состояние панели.
     */
    public RouteAddEditState getState() {
        return state;
    }

    /**
     * Перевести состояние панели.
     *
     * @param state  состояние панели.
     */
    public void setState(RouteAddEditState state) {
        this.state = state;

        switch (state) {
            case ADD:
                setBorderTitle("Добавление нового маршрута: ");
                Utils.enableComponent(this, true);
                setRoute(Route.createNewEmptyRoute());
                break;

            case EDIT:
                setBorderTitle("Редактирование маршрута: ");
                Utils.enableComponent(this, true);
                break;

            case NONE:
                setBorderTitle("Добавление / редактирование маршрута: ");


                // Очистка элементов ввода данных по маршруту:

                Date now = new Date();

                startPoint.setSelectedItem("");
                leaveTime.setValue(now);

                endPoint.setSelectedItem("");
                arrivalTime.setValue(now);

                routeNumber.setValue(0);
                ticketPrice.setValue(0);


                Utils.enableComponent(this, false);  // выкл. всю панель.
                break;
        }
    }

}
