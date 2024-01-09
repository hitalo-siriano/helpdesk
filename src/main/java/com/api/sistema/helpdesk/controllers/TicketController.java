package com.api.sistema.helpdesk.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.sistema.helpdesk.dtos.TicketPostRecordDtos;
import com.api.sistema.helpdesk.dtos.TicketRecordDtos;

import com.api.sistema.helpdesk.dtos.UpdateTicketAtenteDtos;
import com.api.sistema.helpdesk.models.TicketModel;
import com.api.sistema.helpdesk.models.UserModel;

import com.api.sistema.helpdesk.repositories.TicketRepository;
import com.api.sistema.helpdesk.repositories.UserRepository;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


//Tarefa do dia
/*-organizar melhor a logica dos if
  - colocar retornos status code dos metodos corretamente
  - melhora os repositorios
 *
 */

@RestController
public class TicketController {
	@Autowired
    TicketRepository ticketRepository;
	@Autowired
	UserRepository userRepository;
	

	@PostMapping("/api/v1/ticket")
	public ResponseEntity<Object> saveTicket(@RequestBody @Valid TicketPostRecordDtos ticketPostRecordDtos) {
		
		var optionalUser = userRepository.findById(ticketPostRecordDtos.userId());

		if (optionalUser.isPresent()) {
			
			TicketModel ticket = new TicketModel();
			ticket.setOpeningDate(LocalDate.now());
			ticket.setOpeningTime(LocalTime.now());
			ticket.setStatus("open");
			ticket.setLocal(ticketPostRecordDtos.local());
			ticket.setContact(ticketPostRecordDtos.contact());
			ticket.setTitle(ticketPostRecordDtos.title());
			ticket.setDescription(ticketPostRecordDtos.description());
			ticket.setRequesterID(optionalUser.get());
            var resultRepository = ticketRepository.save(ticket);
           
			

			return ResponseEntity.status(HttpStatus.CREATED).body(resultRepository);


		}else{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	
		}
	   
	   
		
	@GetMapping("/api/v1/ticket/{id}")
	public ResponseEntity<Object> getAllTicketUser(@PathVariable(value = "id") UUID id) {

		Optional<UserModel> optional =userRepository.findById(id);

		if (optional.isPresent()) {
			
			return ResponseEntity.status(HttpStatus.OK).body(optional.get().getRequestedTickets());
		}
			return ResponseEntity.notFound().build();
		

		

	}

	@GetMapping("/api/v1/ticket/open/{id}")
	public ResponseEntity<Object> getOpenTicketUser(@PathVariable(value = "id") UUID id) {
        
        var optionalUser = userRepository.findById(id);


		if (optionalUser.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		var openTickets = ticketRepository.findOpenTicketsByUserId(optionalUser.get().getIdUser());

		if (openTickets.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.status(HttpStatus.OK).body(openTickets);

	}
	
	@GetMapping("/api/v1/ticket/close/{id}")
	public ResponseEntity<Object> getCloseTicketUser(@PathVariable(value = "id") UUID id) {
        
		var optionalUser = userRepository.findById(id);


		if (optionalUser.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		var openTickets = ticketRepository.findCloseTicketsByUserId(optionalUser.get().getIdUser());

		if (openTickets.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.status(HttpStatus.OK).body(openTickets);

	}
	
	@GetMapping("/api/v1/ticket/all/{id}")
	public ResponseEntity<Object> getAllTicket(@PathVariable(value = "id") UUID id) {

		var optionalUser = userRepository.findById(id);


		if (optionalUser.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
        var user = optionalUser.get();
		if(user.getJob().equals( "Support Help Desk")){
          
        	return ResponseEntity.status(HttpStatus.OK).body(ticketRepository.findAllTicketsOpen());
		}else{
		     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	
	
       
	}

	@GetMapping("/api/v1/ticket/all/technician/{id}")
	public ResponseEntity<Object> getAllTechnicianTicket(@PathVariable(value = "id") UUID id) {

		var optionalUser = userRepository.findById(id);


		if (optionalUser.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
        var user = optionalUser.get();
		if(user.getJob().equals( "Support Help Desk")){
          
        	return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get().getTechnicianResponsibilityTickets());
		}else{
		     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	
	
       
	}
   
	@PutMapping("/api/v1/ticket/{id}")
	public ResponseEntity<Object> updateTicketUser(@PathVariable(value = "id") UUID id,@RequestBody @Valid TicketRecordDtos ticketRecordDtos) {

		var optionalUser =userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		var optionalTicket = ticketRepository.findById(ticketRecordDtos.ticketId());
		if(optionalTicket.isEmpty()){
				return ResponseEntity.notFound().build();
		}else{
			


			var ticket = optionalTicket.get();
			ticket.setTitle(ticketRecordDtos.title());
			ticket.setLocal(ticketRecordDtos.local());
			ticket.setContact(ticketRecordDtos.contact());
			ticket.setDescription(ticketRecordDtos.description());


			return ResponseEntity.status(HttpStatus.OK).body(ticketRepository.save(ticket));
		}
      
		

	}


    @PutMapping("/api/v1/ticket/update/status/atende/{id}")
	public ResponseEntity<Object> updateStatusAtendeTicketUser(@PathVariable(value = "id") UUID id,@RequestBody @Valid UpdateTicketAtenteDtos updateTicketAtenteDtos) {

		var optionalUser =userRepository.findById(id);
		
		if (optionalUser.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
        var user = optionalUser.get();
		if(user.getJob().equals( "Support Help Desk")){
          
           var optionalTicket = ticketRepository.findById(updateTicketAtenteDtos.ticketId());
		
		   if(optionalTicket.isEmpty()){
				 return ResponseEntity.notFound().build();

			}	
		  var ticket = optionalTicket.get();
		
		  ticket.setStatus("atende");
		  ticket.setSupportMessage(updateTicketAtenteDtos.message());
		  ticket.setTechnicianId(optionalUser.get());

		return ResponseEntity.status(HttpStatus.OK).body(ticketRepository.save(ticket));
		}
	

		
		
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
    
    @PutMapping("/api/v1/ticket/update/status/closed/{id}")
	public ResponseEntity<Object> updateStatusClosedTicketUser(@PathVariable(value = "id") UUID id,@RequestBody @Valid UpdateTicketAtenteDtos updateTicketAtenteDtos) {

		var optionalUser =userRepository.findById(id);
		
		if (optionalUser.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
        var user = optionalUser.get();
		if(user.getJob().equals( "Support Help Desk")){
          
           var optionalTicket = ticketRepository.findById(updateTicketAtenteDtos.ticketId());
		
		   if(optionalTicket.isEmpty()){
				 return ResponseEntity.notFound().build();

			}	
		  var ticket = optionalTicket.get();
		
		  ticket.setStatus("closed");
		  ticket.setSupportMessage(updateTicketAtenteDtos.message());
		  ticket.setClosingDate(LocalDate.now());
		  ticket.setClosingTime(LocalTime.now());
		  ticket.setTechnicianId(optionalUser.get());

		return ResponseEntity.status(HttpStatus.OK).body(ticketRepository.save(ticket));
		}
	

		
		
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

	 
	@PutMapping("/api/v1/ticket/update/status/open/{id}")
	public ResponseEntity<Object> updateStatusOpenTicketUser(@PathVariable(value = "id") UUID id,@RequestBody @Valid UpdateTicketAtenteDtos updateTicketAtenteDtos) {
		

		var optionalUser =userRepository.findById(id);
		
		if (optionalUser.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
        var user = optionalUser.get();
		if(user.getJob().equals( "Support Help Desk")){
          
           var optionalTicket = ticketRepository.findById(updateTicketAtenteDtos.ticketId());
		
		   if(optionalTicket.isEmpty()){
				 return ResponseEntity.notFound().build();

			}	
		  var ticket = optionalTicket.get();
		
		  ticket.setStatus("open");
		  ticket.setSupportMessage(updateTicketAtenteDtos.message());
		  ticket.setClosingDate(null);
		  ticket.setClosingTime(null);
		  ticket.setTechnicianId(optionalUser.get());

		return ResponseEntity.status(HttpStatus.OK).body(ticketRepository.save(ticket));
		}

		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

	@DeleteMapping("/api/v1/ticket/{id}/delete/{idTicket}")
	public ResponseEntity<Object> deleteTicket(@PathVariable(value = "id") UUID id,@PathVariable(value = "idTicket") UUID idTicket){

			var optionalUser =userRepository.findById(id);
		
			if (optionalUser.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
        	var user = optionalUser.get();
			if(user.getJob().equals( "Support Help Desk")){

			   var optionalTicket = ticketRepository.findById(idTicket);
		
		       if(optionalTicket.isEmpty()){
				 return ResponseEntity.notFound().build();

			   }	
               ticketRepository.deleteById(idTicket);
			   return ResponseEntity.status(HttpStatus.OK).body("successfully deleted");

			}

			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();


		}
	

}