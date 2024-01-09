package com.api.sistema.helpdesk.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.sistema.helpdesk.models.TicketModel;




public interface TicketRepository extends JpaRepository<TicketModel, UUID>{

     @Query("SELECT t FROM UserModel u JOIN u.requestedTickets t WHERE u.id = :userId AND t.status = 'open'  or t.status = 'atende'")
     List<TicketModel> findOpenTicketsByUserId(@Param("userId") UUID userId);

     @Query("SELECT t FROM UserModel u JOIN u.requestedTickets t WHERE u.id = :userId AND t.status = 'closed'")
     List<TicketModel> findCloseTicketsByUserId(@Param("userId") UUID userId);

     @Query("SELECT t FROM TicketModel t  WHERE  t.status = 'open' or  t.status = 'atende' ")
     List<TicketModel> findAllTicketsOpen();

}
