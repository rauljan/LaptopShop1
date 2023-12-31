package by.element.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Availabilities")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "quantity")
    private int quantity;

    @Basic
    @Column(name = "price")
    private int price;

    @Basic
    @Column(name = "dateStart")
    private Date dateStart;

    @Basic
    @Column(name = "dateEnd")
    private Date dateEnd;

    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "laptopId")
    private Laptop laptop;

    public Availability() {
    }

    public Availability(int quantity, int price,Shop shop, Laptop laptop) {
        this.quantity = quantity;
        this.price = price;
        this.shop = shop;
        this.laptop = laptop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int fullPrice) {
        this.price = fullPrice;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }
}