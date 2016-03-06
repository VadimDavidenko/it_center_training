package taxi.domain;

import javax.persistence.*;
import java.util.Date;
import taxi.util.Utils;

/**
 * Created by Вадим on 28.02.2016.
 *
 * Date orderDate, Client client, Double amount, String addressFrom, String addressTo
 *
 */

@Entity
@Table(name = "ORDERS")
@SequenceGenerator(name = "ORDERS_SEQ", sequenceName = "ORDERS_SEQ",
        allocationSize = 1, initialValue = 1)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ")
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "AMOUNT", columnDefinition = "NUMBER(10, 2) DEFAULT 0")
    private Double amount;

    @Column(name = "ADDRESS_FROM", length = 100)
    private String addressFrom;

    @Column(name = "ADDRESS_TO", length = 100)
    private String addressTo;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    public Order() {}

    public Order(Client client, Date orderDate, Double amount, String addressFrom, String addressTo) {
        this.orderDate = orderDate;
        this.amount = amount;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (addressFrom != null ? !addressFrom.equalsIgnoreCase(order.addressFrom) : order.addressFrom != null) return false;
        if (addressTo != null ? !addressTo.equalsIgnoreCase(order.addressTo) : order.addressTo != null) return false;
        if (amount != null ? !amount.equals(order.amount) : order.amount != null) return false;
        if (client != null ? !client.equals(order.client) : order.client != null) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderDate != null ? orderDate.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (addressFrom != null ? addressFrom.hashCode() : 0);
        result = 31 * result + (addressTo != null ? addressTo.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", amount=" + amount +
                ", addressFrom='" + addressFrom + '\'' +
                ", addressTo='" + addressTo + '\'' +
                ", client='" + client + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public String getOrderDateStr() {
        if (orderDate != null) {
            return Utils.DATEFORMAT_COMMON.get().format(orderDate);
        }
        return "";
    }
     public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
