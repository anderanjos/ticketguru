package br.dev.a2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Ticket {
    @Id
    @GeneratedValue
    private String destination;
    private BigDecimal price;

    public Ticket() {}

    public Ticket(String destination, BigDecimal price) {
        this.destination = destination;
        this.price = price;
    }

    public String getDestination() {
        return destination;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
