package notes.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Вадим on 14.02.2016.
 *
 * имя
 */

@Entity
@Table(name = "VENDOR")
public class Vendor {
    @Id
    @SequenceGenerator(name = "VENDOR_SEQ", sequenceName = "VENDOR_SEQ",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VENDOR_SEQ")
    @Column(name = "VENDOR_ID")
    private Long id;

    @Column(name = "NAME", length = 50)
    private String name;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.REFRESH)
    private Set<CPU> cpus = new HashSet<CPU>();

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.REFRESH)
    private Set<Memory> memories = new HashSet<Memory>();

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.REFRESH)
    private Set<Notebook> notebooks = new HashSet<Notebook>();

    public Vendor() {}

    @Override
    public String toString() {
        return "Vendor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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

    public Set<Notebook> getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(Set<Notebook> notebooks) {
        this.notebooks = notebooks;
    }

    public Set<CPU> getCpus() {
        return cpus;
    }

    public void setCpus(Set<CPU> cpus) {
        this.cpus = cpus;
    }

    public Set<Memory> getMemories() {
        return memories;
    }

    public void setMemories(Set<Memory> memories) {
        this.memories = memories;
    }
}
