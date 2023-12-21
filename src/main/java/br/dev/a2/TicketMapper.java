package br.dev.a2;

import java.math.BigDecimal;

public record TicketMapper(String destination, BigDecimal price) {
    public Ticket toEntity(){
        return new Ticket(destination, price);
    }
}
