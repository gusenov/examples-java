import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import javax.swing.table.AbstractTableModel;

/**
 * Табличная модель маршрутов.
 */
public class RouteTableModel extends AbstractTableModel {

    /** Столбцы таблицы. */
    private final String[] columnNames = {
            "Начальный пункт",
            "Конечный пункт",
            "Время отправления",
            "Время прибытия",
            "Номер маршрута",
            "Стоимость билета"
    };

    /** Связный список для хранения маршрутов. */
    private List<Route> data = new LinkedList<>();

    /** Конструктор по умолчанию. */
    public RouteTableModel() {
    }

    /**
     * Получить количество столбцов.
     *
     * @return  количество столбцов.
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Получить количество строк.
     *
     * @return  количество строк.
     */
    public int getRowCount() {
        return data.size();
    }

    /**
     * Получить наименование столбца.
     *
     * @param col  индекс столбца.
     * @return     строковое наименование столбца.
     */
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Получить значение ячейки.
     *
     * @param row  индекс строки.
     * @param col  индекс столбца.
     * @return     значение для отображения в ячейке на пересечении строки и столбца.
     */
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0: return data.get(row).getStartPoint();
            case 1: return data.get(row).getEndPoint();
            case 2: return data.get(row).getLeaveTimeAsString();
            case 3: return data.get(row).getArrivalTimeAsString();
            case 4: return data.get(row).getRouteNumber();
            case 5: return data.get(row).getTicketPrice();
        }
        return null;
    }

    /**
     * Можно ли редактировать значение ячейки?
     *
     * @param row     индекс строки.
     * @param column  индекс столбца.
     * @return        true - ячейка будет редактируемой; false - редактирование запрещено.
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }


    /**
     * Загрузка данных из CSV-файла.
     *
     * @param csvFile  путь к файлу.
     * @return  количество загруженных маршрутов.
     */
    public int loadData(String csvFile) {

        data.clear();

        if (!(new File(csvFile)).exists()) {
            fireTableDataChanged();  // обновить таблицу.
            return -1;
        }

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            FileInputStream fis = new FileInputStream(csvFile);

            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
//            InputStreamReader isr = new InputStreamReader(fis, "Cp1251");

            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {

                String[] route = line.split(cvsSplitBy);  // очередная строка из .csv файла.

                // Добавление маршрута в связный список:
                addRoute(new Route(
                        Integer.valueOf(route[0]),
                        route[1],
                        Utils.DATE_FORMAT.parse(route[2]),
                        route[3],
                        Utils.DATE_FORMAT.parse(route[4]),
                        Integer.valueOf(route[5]),
                        Integer.valueOf(route[6])
                ));

            }

        } catch (IOException | ParseException e) {
            // Вывод в стандартный поток ошибок сообщения об ошибке:
            System.err.println("//---------------------------------------------------------------------");
            System.err.println("Exception message: " + e.getMessage());
            System.err.println("//---------------------------------------------------------------------");
            System.err.println("Exception message with class name: " + e.toString());
            System.err.println("//---------------------------------------------------------------------");
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        fireTableDataChanged();  // обновить таблицу.

        return data.size();
    }

    /**
     * Сохранить данные в CSV-файл.
     *
     * @param csvFile  путь к файлу.
     */
    public void saveData(String csvFile) {

        if (data.size() == 0) {
            File file = new File(csvFile);
            if (file.exists()) {
                file.delete();
            }
            return;
        }

        PrintWriter pw = null;

        try {
            FileOutputStream fos = new FileOutputStream(csvFile, false);

            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
//            OutputStreamWriter osw = new OutputStreamWriter(fos, "Cp1251");

            pw = new PrintWriter(osw);
            StringBuilder sb = new StringBuilder();

            for (Route route : data) {
                sb.append(route.getId());
                sb.append(';');
                sb.append(route.getStartPoint());
                sb.append(';');
                sb.append(route.getLeaveTimeAsString());
                sb.append(';');
                sb.append(route.getEndPoint());
                sb.append(';');
                sb.append(route.getArrivalTimeAsString());
                sb.append(';');
                sb.append(route.getRouteNumber());
                sb.append(';');
                sb.append(route.getTicketPrice());
                sb.append("\r\n");
            }

            pw.write(sb.toString());

        } catch (FileNotFoundException e) {
//        } catch (UnsupportedEncodingException | FileNotFoundException e) {

            // Вывод в стандартный поток ошибок сообщения об ошибке:
            System.err.println("//---------------------------------------------------------------------");
            System.err.println("Exception message: " + e.getMessage());
            System.err.println("//---------------------------------------------------------------------");
            System.err.println("Exception message with class name: " + e.toString());
            System.err.println("//---------------------------------------------------------------------");
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * Получить маршрут по индексу.
     *
     * @param idx  индекс маршрута в связном списке.
     * @return     экземпляр класса Route или null.
     */
    public Route getRouteAt(int idx) {
        if (idx >= 0 && idx < data.size())
            return data.get(idx);
        else
            return null;
    }

    /**
     * Добавить маршрут в связный список.
     *
     * @param route  маршрут.
     */
    public void addRoute(Route route) {
        if (route.getId() == -1) {  // если у маршрута не задан уникальный идентификатор, то:
            route.setId(getNewId());  // задаем новый уникальный идентификатор.
        }

        data.add(route);  // непосредственно добавление маршрута в связный список.
    }

    /**
     * Удалить маршрут из связаного списка.
     *
     * @param idx  индекс маршрута.
     * @return     true - успешно удалён; false - маршрут с заданным индексом не найден.
     */
    public boolean deleteRoute(int idx) {
        if (idx >= 0 && idx < data.size()) {
            data.remove(idx);
            return true;
        }
        return false;
    }

    /**
     * Генерация нового уникального идентификатора для маршрута.
     * Идентификатор генерируется по схеме:
     *  - шаг 1: поиск макс. идентификатора;
     *  - шаг 2: добавить к макс. идентификатору единицу.
     *
     * @return  новый идентификатор, который можно присвоить маршруту.
     */
    private Integer getNewId() {
        Integer newId = Integer.MIN_VALUE;

        for (Route route: data) {
            if (route.getId() > newId) {
                newId = route.getId();
            }
        }

        return newId < 0 ? 0 : newId + 1;
    }

    /**
     * Получить множество пунктов отправления.
     *
     * @return  множество пунктов отправления.
     */
    public Set<String> getStartPoints() {
        Set<String> result = new HashSet<>(data.size());
        for (Route route: data) {
            result.add(route.getStartPoint());
        }
        return result;
    }

    /**
     * Получить множество пунктов прибытия.
     *
     * @return  множество пунктов прибытия.
     */
    public Set<String> getEndPoints() {
        Set<String> result = new HashSet<>(data.size());
        for (Route route: data) {
            result.add(route.getEndPoint());
        }
        return result;
    }

}
