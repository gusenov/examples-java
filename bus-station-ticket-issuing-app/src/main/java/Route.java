import java.math.BigDecimal;
import java.util.Date;

/** Маршрут. */
public class Route {

    /** Начальный пункт отправления. */
    private String startPoint;

    /** Конечный пункт прибытия. */
    private String endPoint;

    /** Время отправления. */
    private Date leaveTime;

    /** Время прибытия. */
    private Date arrivalTime;

    /** Стоимость билета. */
    private Integer ticketPrice;

    /** Номер маршрута. */
    private Integer routeNumber;

    /** Уникальный идентификатор. */
    private Integer id;

    /**
     * Конструктор.
     *
     * @param startPoint   начальный пункт отправления.
     * @param leaveTime    время отправления.
     * @param endPoint     конечный пункт прибытия.
     * @param arrivalTime  время прибытия.
     * @param routeNumber  номер маршрута.
     * @param ticketPrice  стоимость билета.
     */
    public Route(String startPoint, Date leaveTime,
                 String endPoint, Date arrivalTime,
                 Integer routeNumber,
                 Integer ticketPrice) {
        this.startPoint = startPoint;
        this.leaveTime = leaveTime;
        this.endPoint = endPoint;
        this.arrivalTime = arrivalTime;
        this.routeNumber = routeNumber;
        this.ticketPrice = ticketPrice;
    }

    /**
     * Конструктор с уникальным идентификатором.
     *
     * @param id           уникальный идентификатор.
     * @param startPoint   начальный пункт отправления.
     * @param leaveTime    время отправления.
     * @param endPoint     конечный пункт прибытия.
     * @param arrivalTime  время прибытия.
     * @param routeNumber  номер маршрута.
     * @param ticketPrice  стоимость билета.
     */
    public Route(Integer id,
                 String startPoint, Date leaveTime,
                 String endPoint, Date arrivalTime,
                 Integer routeNumber,
                 Integer ticketPrice) {
        this.id = id;
        this.startPoint = startPoint;
        this.leaveTime = leaveTime;
        this.endPoint = endPoint;
        this.arrivalTime = arrivalTime;
        this.routeNumber = routeNumber;
        this.ticketPrice = ticketPrice;
    }

    /**
     * Создать новый пустой экземпляр класса Route.
     *
     * @return  незаполненный экземпляр класса Route.
     */
    public static Route createNewEmptyRoute() {
        Date now = new Date();  // текущая дата и время.

        Route newEmptyRoute = new Route("", now, "", now, 0, 0);

        newEmptyRoute.setId(-1);  // идентификатор не задан.

        return newEmptyRoute;
    }

    /**
     * Получить начальный пункт отправления.
     *
     * @return  начальный пункт отправления.
     */
    public String getStartPoint() {
        return startPoint;
    }

    /**
     * Установить начальный пункт отправления.
     *
     * @param startPoint  начальный пункт отправления.
     */
    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * Получить конечный пункт прибытия.
     *
     * @return  конечный пункт прибытия.
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * Установить конечный пункт прибытия.
     *
     * @param endPoint  конечный пункт прибытия.
     */
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Получить время отправления.
     *
     * @return  время отправления.
     */
    public Date getLeaveTime() {
        return leaveTime;
    }

    /**
     * Получить время отправления в виде строки.
     *
     * @return  время отправления в виде строки.
     */
    public String getLeaveTimeAsString() {
        return Utils.DATE_FORMAT.format(leaveTime);
    }

    /**
     * Установить время отправления.
     *
     * @param leaveTime  время отправления.
     */
    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    /**
     * Получить время прибытия.
     *
     * @return  время прибытия.
     */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Получить время прибытия в виде строки.
     *
     * @return  время прибытия в виде строки.
     */
    public String getArrivalTimeAsString() {
        return Utils.DATE_FORMAT.format(arrivalTime);
    }

    /**
     * Установить время прибытия.
     *
     * @param arrivalTime  время прибытия.
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Получить стоимость билета.
     *
     * @return  стоимость билета.
     */
    public Integer getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Получить стоимость билета в виде строки.
     *
     * @return  стоимость билета в виде строки.
     */
    public String getTicketPriceAsString() {
        return Integer.toString(ticketPrice);
    }

    /**
     * Установить стоимость билета.
     *
     * @param ticketPrice  стоимость билета.
     */
    public void setTicketPrice(Integer ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * Получить номер маршрута.
     *
     * @return  номер маршрута.
     */
    public Integer getRouteNumber() {
        return routeNumber;
    }

    /**
     * Получить номер маршрута в виде строки.
     *
     * @return  номер маршрута в виде строки.
     */
    public String getRouteNumberAsString() {
        return Integer.toString(routeNumber);
    }

    /**
     * Установить номер маршрута.
     *
     * @param routeNumber  номер маршрута в виде строки.
     */
    public void setRouteNumber(Integer routeNumber) {
        this.routeNumber = routeNumber;
    }

    /**
     * Получить уникальный идентификатор.
     *
     * @return  уникальный идентификатор.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Установить уникальный идентификатор.
     *
     * @param id  уникальный идентификатор.
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
