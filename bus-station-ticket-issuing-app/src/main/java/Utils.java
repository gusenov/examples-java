import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;

/** Вспомогательные методы. */
public class Utils {

    /** Формат даты использующийся для отображения дат отправления и прибытия по маршруту. */
    public static final String DATE_FORMAT_STRING = "dd.MM.yyyy HH:mm";

    /** Форматор дат. */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING);

    /**
     * Задание ограничений при использовании раскладки GridBagLayout
     * для заголовков на панели со свойствами маршрута.
     *
     * @param x  столбец.
     * @param y  строка.
     * @return   ограничения компонента для использования в раскладке GridBagLayout.
     */
    public static GridBagConstraints titleConstraints(int x, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.insets = new Insets(4, 4, 4, 4);
        constraints.weightx = 0.10;
        constraints.anchor = GridBagConstraints.LINE_END;
        return constraints;
    }

    /**
     * Задание ограничений при использовании раскладки GridBagLayout
     * для элементов ввода на панели со свойствами маршрута.
     *
     * @param x  столбец.
     * @param y  строка.
     * @return   ограничения компонента для использования в раскладке GridBagLayout.
     */
    public static GridBagConstraints editControlConstraints(int x, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.insets = new Insets(4, 4, 4, 4);
        constraints.weightx = 0.40;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return constraints;
    }

    /**
     * Конфигурирование элемента для ввода даты отправления или прибытия.
     *
     * @param spinner  элемент для ввода даты отправления или прибытия.
     */
    public static void configDateTimeSpinner(JSpinner spinner) {
        JSpinner.DateEditor dateEditor;
        DateFormatter dateFormatter;

        dateEditor = new JSpinner.DateEditor(spinner, DATE_FORMAT_STRING);

        dateFormatter = (DateFormatter)dateEditor.getTextField().getFormatter();
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);

        spinner.setEditor(dateEditor);
    }

    /**
     * Конфигурирование элемента для ввода номера маршрута или стоимости билета.
     *
     * @param spinner  элемент для ввода номера маршрута или стоимости билета.
     */
    public static void configNumberSpinner(JSpinner spinner) {
        JSpinner.NumberEditor numberEditor;
        NumberFormatter numberFormatter;

        numberEditor = new JSpinner.NumberEditor(spinner);

        numberFormatter = (NumberFormatter)numberEditor.getTextField().getFormatter();
        numberFormatter.setFormat(new DecimalFormat("#"));
        numberFormatter.setAllowsInvalid(false);

        spinner.setEditor(numberEditor);
    }

    /**
     * Полное включение/отключение компонента и всех его дочерних компонентов.
     *
     * @param parentComponent  родительский компонент.
     * @param value            true - включить; false - выключить.
     */
    public static void enableComponent(Component parentComponent, boolean value) {
        if (parentComponent instanceof Container) {  // если родительский компонент является контейнером:
            Container parentContainer = (Container)parentComponent;
            for (Component childComponent : parentContainer.getComponents()) {  // обход всех подкомпонентов:
                enableComponent(childComponent, value);
            }
        }
        parentComponent.setEnabled(value);
    }

    /**
     * Получение текущей даты/времени в качестве строки.
     *
     * @return  строка содержащая текущую дату/время.
     */
    public static String getNowDateAsString() {
        Date now = new Date();  // текущая дата/время.
        return DATE_FORMAT.format(now);
    }

    /**
     * Печать на принтере графического представления заданного компонента.
     *
     * @param component  компонент.
     */
    public static void printComponenet(Component component) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName(" Print Component ");

        pj.setPrintable (new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum) {
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D)pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                component.paint(g2);
                return Printable.PAGE_EXISTS;
            }
        });

        if (!pj.printDialog())
            return;

        try {
            pj.print();
        } catch (PrinterException e) {
            // Вывод в стандартный поток ошибок сообщения об ошибке:
            System.err.println("//---------------------------------------------------------------------");
            System.err.println("Exception message: " + e.getMessage());
            System.err.println("//---------------------------------------------------------------------");
            System.err.println("Exception message with class name: " + e.toString());
            System.err.println("//---------------------------------------------------------------------");
            e.printStackTrace();
        }
    }

    /**
     * Расположить окно по центру экрана.
     *
     * @param frame  фрейм окна.
     */
    public static void centreWindow(Window frame) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[]    gs = ge.getScreenDevices();  // количество дисплеев.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gs.length; ++i) {

            // Размещаем окно по центру первого дисплея и выходим:

            DisplayMode dm = gs[i].getDisplayMode();
            int x = (int) ((dm.getWidth() - frame.getWidth()) / 2);
            int y = (int) ((dm.getHeight() - frame.getHeight()) / 2);
            frame.setLocation(x, y);

            break;  // выход.
        }
    }

}
