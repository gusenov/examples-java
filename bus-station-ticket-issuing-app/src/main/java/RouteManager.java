import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/** Панель управления маршрутами. */
public class RouteManager extends TicketForm {

    /** Кнопка для добавления нового маршрута. */
    private final JButton addButton = new JButton("Добавить новый маршрут");

    /** Конструктор по умолчанию. */
    public RouteManager() {
    }

    /** Инициализация. */
    public void init() {
        JTable table = routesTable.getTable();  // таблица.

        // Панель для отображения свойств маршрута, чтобы можно было
        // добавлять или редактировать через неё маршруты:
        routeProps = new RouteAddEdit();

        loadData();

        // Программирование кнопки сохранения добавляемого или редактируемого маршрута:
        routeProps.getActionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                routeProps.updateRoute();  // зафиксировать введенные данные по маршруту.

                int selectedRowIdx = table.getSelectedRow();  // индекс выделеленной в таблице маршрутов строки.

                // Если панель находилась в состоянии добавления нового маршрута:
                if (((RouteAddEdit)routeProps).getState() == RouteAddEditState.ADD) {

                    Route newRoute = routeProps.getRoute();  // новый маршрут.
                    routeTableModel.addRoute(newRoute);  // добавление маршрута в модель данных таблицы.

                    selectedRowIdx = table.getRowCount() - 1;
                    if (selectedRowIdx < 0) selectedRowIdx = 0;

                    // Обновить таблицу после вставки нового маршрута:
                    routeTableModel.fireTableRowsInserted(selectedRowIdx, selectedRowIdx);

                    // Выделение только что вставленного в таблицу нового маршрута:
                    table.setRowSelectionInterval(selectedRowIdx, selectedRowIdx);

                    // Прокрутка к только что вставленного в таблицу новому маршруту:
                    table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));

                // Если панель находилась в состоянии редактирования существующего маршрута:
                } else if (((RouteAddEdit)routeProps).getState() == RouteAddEditState.EDIT) {

                    // Обновить таблицу после редактирования существующего маршрута:
                    routeTableModel.fireTableRowsUpdated(selectedRowIdx, selectedRowIdx);
                }

                updateComboItems();  // заполнение выпадающих списков пунктами отправления и прибытия.

                saveData();
            }
        });

        // Поведение при клике на строку из таблицы маршрутов:
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                int selectedRowIdx = table.getSelectedRow();  // индекс выделенного маршрута.
                if (selectedRowIdx > -1) {

                    // Заполнить панель редактирования маршрута
                    // выделенным в данный момент маршрутом:
                    Route route = routeTableModel.getRouteAt(selectedRowIdx);
                    ((RouteAddEdit) routeProps).setState(RouteAddEditState.EDIT);
                    routeProps.setRoute(route);

                    actionButton.setEnabled(true);  // вкл. кнопку удаления маршрута.

                // Если маршрут не выбран, то отключить кнопку удаления маршрута:
                } else {
                    actionButton.setEnabled(false);
                }
            }
        });

        // Программирование кнопки добавления нового маршрута:
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ((RouteAddEdit)routeProps).setState(RouteAddEditState.ADD);
            }
        });

        // Программирование кнопки удаления существующего маршрута:
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRowIdx = table.getSelectedRow();  // индекс выделенного маршрута.
                if (selectedRowIdx > -1) {
                    routeTableModel.deleteRoute(selectedRowIdx);  // удалить маршрут.

                    // Обновить таблицу после удаления маршрута:
                    routeTableModel.fireTableRowsDeleted(selectedRowIdx, selectedRowIdx);

                    // Выключить панель добавления/редактирования маршрута:
                    ((RouteAddEdit) routeProps).setState(RouteAddEditState.NONE);

                    saveData();
                }
            }
        });
    }

    /** Создание графического интерфейса панели управления маршрутами. */
    public void createGUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));  // раскладка.
        setBorder(new EmptyBorder(8, 8, 8, 8));  // отступ от краев.

        // Таблица маршрутов:
        routesTable.setAlignmentX(Component.LEFT_ALIGNMENT);
        routesTable.createGUI();
        routesTable.setBorderTitle("Все маршруты: ");
        routesTable.getButtonsBox().add(addButton);

        // Кнопки под таблицей маршрутов:
        actionButton.setText("Удалить выбранный маршрут");
        actionButton.setEnabled(false);

        routesTable.getButtonsBox().add(Box.createRigidArea(new Dimension(8,0)));
        routesTable.getButtonsBox().add(actionButton);
        routesTable.getButtonsBox().add(Box.createRigidArea(new Dimension(8,0)));

        add(routesTable);

        add(Box.createRigidArea(new Dimension(0,16)));  // отступ.

        // Панель для добавления или редактирования маршрутов:
        routeProps.setAlignmentX(Component.LEFT_ALIGNMENT);
        routeProps.createGUI();
        add(routeProps);
    }

    /** Сохранить данные. */
    public void saveData() {
        routeTableModel.saveData(ROUTES_DEFAULT_FILE_NAME);
    }

}
