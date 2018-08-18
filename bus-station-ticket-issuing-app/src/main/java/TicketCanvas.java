import java.awt.*;
import javax.swing.*;

/** Холст на котором рисуется билет. */
public class TicketCanvas extends JPanel {

    /** Маршрут для которого рисуется билет. */
    private Route route;

    /** Текущее время. */
    private String now;

    /** Конструктор. */
    public TicketCanvas() {
        setBackground(Color.WHITE);  // фон.
    }

    /**
     * Метод занимающийся отрисовкой билета на холсте.
     *
     * @param g  графический контекст.
     */
    public void paint(Graphics g) {
        super.paint(g);  // вызов аналогичного метода у суперкласса для базовой отрисовки.

        Font defaultFont = g.getFont();  // шрифт по умолчанию.

        // Шрифт для главного заголовка:
        Font titleFont = new Font(defaultFont.getFontName(), defaultFont.getStyle(), 24);

        // Шрифт для ключей:
        Font keyFont = new Font(defaultFont.getFontName(), Font.ITALIC, defaultFont.getSize());

        // Шрифт для значений:
        Font valueFont = new Font(defaultFont.getFontName(), Font.BOLD, defaultFont.getSize());

        g.drawRect(10,10,590,380);  // рамка.

        // Отрисовка текста на билете:

        g.setColor(Color.BLUE);

        g.setFont(titleFont);
        g.drawString("Проездной билет", 220, 50);

        g.setColor(Color.BLACK);

        g.setFont(keyFont);
        g.drawString("Номер маршрута: ", 400, 100);
        g.setFont(valueFont);
        g.drawString(route.getRouteNumberAsString(), 525, 100);

        g.setFont(keyFont);
        g.drawString("Начальный пункт отправления: ", 20, 175);
        g.setFont(valueFont);
        g.drawString(route.getStartPoint(), 20, 200);

        g.setFont(keyFont);
        g.drawString("Конечный пункт прибытия: ", 20, 250);
        g.setFont(valueFont);
        g.drawString(route.getEndPoint(), 20, 275);

        g.setFont(keyFont);
        g.drawString("Время отправления: ", 350, 175);
        g.setFont(valueFont);
        g.setColor(Color.RED);
        g.drawString(route.getLeaveTimeAsString(), 350, 200);

        g.setColor(Color.BLACK);

        g.setFont(keyFont);
        g.drawString("Время прибытия: ", 350, 250);
        g.setFont(valueFont);
        g.drawString(route.getArrivalTimeAsString(), 350, 275);

        g.setFont(keyFont);
        g.drawString("Стоимость билета (руб.): ", 20, 350);
        g.setFont(valueFont);
        g.drawString(route.getTicketPriceAsString(), 200, 350);

        g.setFont(keyFont);
        g.drawString("Дата/время: ", 400, 350);
        g.setFont(valueFont);
        g.drawString(now, 490, 350);
    }

    /**
     * Установка маршрута для которого рисуется билет.
     *
     * @param route  маршрут.
     */
    public void setRoute(Route route) {
        this.route = route;
        now = Utils.getNowDateAsString();
    }
}
