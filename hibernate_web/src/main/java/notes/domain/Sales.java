package notes.domain;

import notes.util.Utils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Вадим on 14.02.2016.
 *
 * склад ноутбуков, дата продажи, количество
 */

@Entity
@Table(name = "SALES")
public class Sales {
    @Id
    @SequenceGenerator(name = "SALES_SEQ", sequenceName = "SALES_SEQ",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALES_SEQ")
    @Column(name = "SALES_ID")
    private Long id;

    @Column(name = "SALE_DATE")
    private Date date;

    @Column(name = "AMOUNT", length = 20)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    public Sales() {}

    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    @Transient
    public String getDateStr() {
        if (date != null) {
            return Utils.DATEFORMAT_COMMON.get().format(date);
        }
        return "";
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
