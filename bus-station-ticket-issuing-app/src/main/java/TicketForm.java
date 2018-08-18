import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

/** Панель для отображения на вкладке для формирования билетов. */
public class TicketForm extends JPanel {

    /** Имя файла данных по умолчанию. */
    protected final String ROUTES_DEFAULT_FILE_NAME = "routes.csv";

    /** Панель со свойствами маршрута, которые будут выступать в качестве критериев для поиска маршрутов. */
    protected RouteProps routeProps;

    /** Кнопка для формирования билета на выбранный маршрут. */
    protected final JButton actionButton = new JButton("Сформировать билет");

    /** Сбросить результаты поиска. */
    protected final JButton resetButton = new JButton("Показать все маршруты");

    /** Панель с таблицей маршрутов. */
    protected final RouteTable routesTable = new RouteTable();

    /** Модель данных для таблицы маршрутов. */
    protected RouteTableModel routeTableModel = routesTable.getRouteTableModel();

    /** Диалоговое окно с билетом на выбранный маршрут. */
    private Ticket ticket = new Ticket((JFrame) SwingUtilities.getWindowAncestor(this),
            "Билетная касса автовокзала - Печать билета", true);

    /** Конструктор. */
    public TicketForm() {
    }

    /** Инициализация. */
    public void init() {
        JTable table = routesTable.getTable();  // таблица маршрутов.

        routeProps = new RouteProps();  // создание панели для задания критериев поиска маршрутов.

        // Программирование кнопки поиска маршрутов по заданным критериям:
        routeProps.getActionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                table.setRowSorter(null);  // удаление предыдущего фильтра.

                // Фильтр для строк таблицы реализующий отбор маршрутов по заданным критериям:
                RowFilter<Object,Object> filter = new RowFilter<Object,Object>() {
                    public boolean include(Entry<? extends Object, ? extends Object> entry) {

                        // Очередной маршрут:
                        int rowID = (Integer)entry.getIdentifier();
                        RouteTableModel data = (RouteTableModel)entry.getModel();
                        Route route = data.getRouteAt(rowID);

                        // Критерии поиска:
                        routeProps.updateRoute();
                        Route criteria = routeProps.getRoute();

                        // Если совпадают пункт отправления и пункт прибытия
                        // и дата отправления с датой прибытия лежат внутри диапазона заданного критериями то:
                        if (route.getStartPoint().equalsIgnoreCase(criteria.getStartPoint())
                                && route.getEndPoint().equalsIgnoreCase(criteria.getEndPoint())

                                && (route.getLeaveTime().compareTo(criteria.getLeaveTime()) == 0
                                || route.getLeaveTime().compareTo(criteria.getLeaveTime()) > 0)

                                && (route.getArrivalTime().compareTo(criteria.getArrivalTime()) == 0
                                || route.getArrivalTime().compareTo(criteria.getArrivalTime()) < 0)) {
                            return true;  // показать маршрут в таблице.
                        }

                        return false;  // скрыть маршрут потому что не удовлетворяет критериям поиска.
                    }
                };

                // Установка фильтра на таблицу:
                TableRowSorter<RouteTableModel> sorter = new TableRowSorter<RouteTableModel>(routeTableModel);
                sorter.setRowFilter(filter);
                table.setRowSorter(sorter);

                actionButton.setEnabled(table.getSelectedRow() > 0);
                resetButton.setEnabled(true);
            }
        });

        Utils.centreWindow(ticket);  // разместить диалоговое окно с билетом по центру.
        ticket.createGUI();  // создание диалогового окна в котором будет отображаться билет.

        // Программирование кнопки для формирования билета на выбранный маршрут:
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRowIdx = table.getSelectedRow();  // индекс строки.
                if (selectedRowIdx > -1) {
                    int realIdx = table.convertRowIndexToModel(selectedRowIdx);  // реальный индекс.

                    // Получение реального индекса нужно потому что таблица может быть отфильтрована
                    // поэтому индекс строки может не соответствовать индексу из списка в модели данных таблицы.

                    ticket.setRoute(routeTableModel.getRouteAt(realIdx));
                    ticket.setVisible(true);  // показать диалоговое окно с билетом.

                }
            }
        });

        // Программирование кнопки сброса результатов поиска:
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                table.setRowSorter(null);  // удаление предыдущего фильтра.
                resetButton.setEnabled(false);
            }
        });

        // Поведение при клике на строку из таблицы маршрутов:
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                int selectedRowIdx = table.getSelectedRow();
                if (selectedRowIdx > -1) {
                    actionButton.setEnabled(true);
                } else {
                    // Если маршрут не выбран, то отключить кнопку формирования билета:
                    actionButton.setEnabled(false);
                }
            }
        });
    }

    /** Создание графического интерфейса панели для отображения на вкладке для формирования билетов. */
    public void createGUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));  // раскладка.
        setBorder(new EmptyBorder(8, 8, 8, 8));  // отступ от краев.

        // Создание панели с критериями поиска маршрутов:
        routeProps.createGUI();
        routeProps.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(routeProps);

        add(Box.createRigidArea(new Dimension(0,8)));  // отступ между поисковой панелью и таблицей.

        // Создание таблицы для отображения найденных маршрутов:
        routesTable.createGUI();
        routesTable.setBorderTitle("Маршруты отвечающие заданным критериям: ");

        // Кнопки под таблицей маршрутов:
        routesTable.getButtonsBox().add(Box.createRigidArea(new Dimension(8,0)));
        routesTable.getButtonsBox().add(resetButton);
        routesTable.getButtonsBox().add(Box.createRigidArea(new Dimension(8,0)));
        routesTable.getButtonsBox().add(actionButton);
        routesTable.getButtonsBox().add(Box.createRigidArea(new Dimension(8,0)));

        resetButton.setEnabled(false);
        actionButton.setEnabled(false);

        routesTable.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(routesTable);
    }

    /** Заполнить выпадающие списки пунктами отправления и прибытия. */
    protected void updateComboItems() {
        routeProps.setStartPoints(routeTableModel.getStartPoints());  // пункты отправления.
        routeProps.setEndPoints(routeTableModel.getEndPoints());  // пункты прибытия.
    }

    /**
     * Загрузить данные.
     *
     * @return  количество загруженных маршрутов.
     */
    public int loadData() {
        int result = routeTableModel.loadData(ROUTES_DEFAULT_FILE_NAME);  // загрузка данных из файла.
        updateComboItems();  // заполнение выпадающих списков пунктами отправления и прибытия.
        return result;
    }

    /**
     * Включить или выключить панель со свойствами маршрута.
     *
     * @param value  true - включить; false - выключить.
     */
    public void enableRoutePropsPanel(boolean value) {
        Utils.enableComponent(routeProps, value);
    }

}
