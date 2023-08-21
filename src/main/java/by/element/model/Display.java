package by.element.model;

import javax.persistence.*;

@Entity
@Table(name = "Displays")
public class Display {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "model")
    private String model;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column(name = "diagonal")
    private String diagonal;

    @Basic
    @Column(name = "resolution")
    private String resolution;

    @Basic
    @Column(name = "price")
    private int price;

    public Display() {
    }

    public Display(String model, String type, String diagonal, String resolution, int price) {
        this.model = model;
        this.type = type;
        this.diagonal = diagonal;
        this.resolution = resolution;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(String diagonal) {
        this.diagonal = diagonal;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}