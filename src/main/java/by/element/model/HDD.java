package by.element.model;

import javax.persistence.*;

@Entity
@Table(name = "HDDs")
public class HDD {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "model")
    private String model;

    @Basic
    @Column(name = "memory")
    private int memory;

    @Basic
    @Column(name = "price")
    private int price;

    public HDD() {
    }

    public HDD(String model, int memory, int price) {
        this.model = model;
        this.memory = memory;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }
}