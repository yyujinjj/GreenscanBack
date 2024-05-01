package com.io.greenscan.repository;

import com.io.greenscan.entity.ExchangerTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangerTicketRepository extends JpaRepository<ExchangerTicket,Long> {
}
