package notes.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Вадим on 14.02.2016.
 *
 * производитель, размер
 */

@Entity
@Table(name = "MEMORY")
public class Memory {
    @Id
    @SequenceGenerator(name = "MEMORY_SEQ", sequenceName = "MEMORY_SEQ",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMORY_SEQ")
    @Column(name = "MEMORY_ID")
    private Long id;

    @Column(name = "MEMORY_SIZE", length = 20)
    private String size;

    @ManyToOne
    @JoinColumn(name = "VENDOR_ID")
    private Vendor vendor;

    @OneToMany(mappedBy = "memory", cascade = CascadeType.REFRESH)
    private Set<Notebook> notebooks = new HashSet<Notebook>();

    public Memory() {}

    @Override
    public String toString() {
        return "Memory{" +
                "id=" + id +
                ", vendor='" + vendor + '\'' +
                ", size=" + size +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Set<Notebook> getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(Set<Notebook> notebooks) {
        this.notebooks = notebooks;
    }
}
