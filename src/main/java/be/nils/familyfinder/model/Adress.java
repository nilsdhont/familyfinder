package be.nils.familyfinder.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Adress {
    private int id;
    private String street;
    private int number;
    private String numberAddition;
    private String city;
    private String postalCode;
    private String countryCode;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "number_addition")
    public String getNumberAddition() {
        return numberAddition;
    }

    public void setNumberAddition(String numberAddition) {
        this.numberAddition = numberAddition;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "country_code")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adress adress = (Adress) o;
        return id == adress.id &&
                number == adress.number &&
                Objects.equals(street, adress.street) &&
                Objects.equals(numberAddition, adress.numberAddition) &&
                Objects.equals(city, adress.city) &&
                Objects.equals(postalCode, adress.postalCode) &&
                Objects.equals(countryCode, adress.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, number, numberAddition, city, postalCode, countryCode);
    }
}
