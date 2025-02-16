package hiber.app;

import org.hibernate.annotations.Type;
import javax.persistence.*;

@Entity

@Table(name="Apartment")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(nullable = false)
    private int rooms;

    @Column(precision=10, scale=2, nullable = false)
    private double square;

    @Column(length=200, unique=true, nullable = false)
    private String address;

    @Column(length=100)
    private String district;

    @Column(nullable = false)
    private int price;

    @Column(name="is_sold")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isSold;

    public Apartment() {}

    public Apartment(int rooms, double square, String address, int price) {
        this.rooms = rooms;
        this.square = square;
        this.address = address;
        this.price = price;
        isSold = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
            return price;
    }

    public void setPrice(int price) {
            this.price = price;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void markSold() {
        isSold = true;
    }

    public boolean getSold() {
        return isSold;
    }

    @Override
    public String toString() {
        return String.format(
                "%d : %d room(s) in area of %.2f sq.m : %s, %s : price = %d, %s",
                id, rooms, square, address, district, price,
                isSold ? "sold" : "unsold"
        );
    }
}
