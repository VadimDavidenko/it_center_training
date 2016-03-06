package taxi.domain;

import taxi.util.Utils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Вадим on 28.02.2016.
 */

@Entity
@Table(name = "CLIENTS")
@SequenceGenerator(name = "CLIENTS_SEQ", sequenceName = "CLIENTS_SEQ",
        allocationSize = 1, initialValue = 1)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENTS_SEQ")
    @Column(name = "CLIENT_ID")
    private Long id;

    @Column(name = "FIRST_NAME", length = 50)
    private String name;

    @Column(name = "LAST_NAME", length = 50)
    private String surname;

    @Column(name = "PHONE", length = 20)
    private String phone;

    @Column(name = "ADDRESS", length = 100)
    private String address;

    @Column(name = "LAST_ORDER_DATE")
    private Date lastOrderDate;

    @Column(name = "AMOUNT", columnDefinition = "NUMBER(10, 2) DEFAULT 0")
    private Double amount;

    @OneToMany(mappedBy = "client")
    private Set<Order> orders = new HashSet<Order>();

    public Client() {}

    public Client(String name, String surname, String phone, String address) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", lastOrderDate=" + lastOrderDate +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        if (address != null ? !address.equalsIgnoreCase(client.address) : client.address != null) return false;
        if (name != null ? !name.equalsIgnoreCase(client.name) : client.name != null) return false;
        if (phone != null ? !phone.equalsIgnoreCase(client.phone) : client.phone != null) return false;
        if (surname != null ? !surname.equalsIgnoreCase(client.surname) : client.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Transient
    public String getLastOrderDateStr() {
        if (lastOrderDate != null) {
            return Utils.DATEFORMAT_COMMON.get().format(lastOrderDate);
        }
        return "";
    }

    public Date getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(Date lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Transient
    public String  getFullName(){
        return name + " " + surname;
    }
}
