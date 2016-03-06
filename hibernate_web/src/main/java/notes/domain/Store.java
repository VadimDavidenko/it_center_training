package notes.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Вадим on 14.02.2016.
 *
 * тип ноутбука, количество, цена
 */

@Entity
@Table(name = "STORE")
public class Store {
    @Id
    @SequenceGenerator(name = "STORE_SEQ", sequenceName = "STORE_SEQ",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_SEQ")
    @Column(name = "STORE_ID")
    private Long id;

    @Column(name = "AMOUNT", length = 20)
    private Integer amount;

    @Column(name = "PRICE", length = 20)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "NOTEBOOK_ID")
    private Notebook notebook;

    @OneToMany(mappedBy = "store", cascade = CascadeType.DETACH)
    private Set<Sales> sales = new HashSet<Sales>();

    public Store() {}

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Notebook getNotebook() {
        return notebook;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public Set<Sales> getSales() {
        return sales;
    }

    public void setSales(Set<Sales> sales) {
        this.sales = sales;
    }
}
