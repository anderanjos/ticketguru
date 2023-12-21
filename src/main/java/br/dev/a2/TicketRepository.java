package br.dev.a2;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@RequestScoped
public class TicketRepository {

    private final EntityManager em;

    public TicketRepository(final EntityManager entityManager) {
        this.em = entityManager;
    }

    @Transactional
    public void saveTicket(Ticket ticket){
        em.persist(ticket);
    }
}
