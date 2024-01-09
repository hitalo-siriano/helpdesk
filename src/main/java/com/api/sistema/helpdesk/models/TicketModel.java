package com.api.sistema.helpdesk.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_TICKET")
public class TicketModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID ticketId;

	@Size(min = 5 , max = 255, message = "The position cannot be longer than 255 characters")
    @Column(nullable = false, length = 255)
    private String title;

	@Size(min = 15,max = 255, message = "The position cannot be longer than 255 characters")
    @Column(nullable = false, length = 255)
    private String description;

	@NotNull(message = "Opening date cannot be null")
    private LocalDate openingDate;
    
	
    private LocalDate closingDate;

	@NotNull(message = "Opening date cannot be null")
    private LocalTime openingTime;

	
    private LocalTime closingTime;

	@NotNull(message = "Opening date cannot be null")
    private String status;

	@Size(max = 255, message = "The position cannot be longer than 255 characters")
    @Column(length = 255)
	private String supportMessage;

	@Digits(integer = 10, fraction = 1)
	@Column(nullable = false, length = 50)
	private Long contact;


	@Size(min = 5 , max = 50, message = "The position cannot be longer than 255 characters")
	@Column(nullable = false, length = 255)
	private String local;

	

	@Transient
    private String technicianName;

	@Transient
	private String requesterName;

   

	
	

	@ManyToOne
    @JoinColumn(name = "requesterID")
    @JsonBackReference
    private UserModel requesterID;

    @ManyToOne
    @JoinColumn(name = "technicianID")
    @JsonBackReference
    private UserModel technicianID;

	public UserModel getRequesterID() {
		return requesterID;
	}

	public void setRequesterID(UserModel requesterID) {
		this.requesterID = requesterID;
	}

	public UserModel getTechnicianID() {
		return technicianID;
	}

	public void setTechnicianId(UserModel technicianId) {
		this.technicianID = technicianId;
	}

	public UUID getTicketId() {
		return ticketId;
	}

	public void setTicketId(UUID ticketId) {
		this.ticketId = ticketId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}

	public LocalDate getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	public LocalTime getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(LocalTime openingTime) {
		this.openingTime = openingTime;
	}

	public LocalTime getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(LocalTime closingTime) {
		this.closingTime = closingTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

 public String getSupportMessage() {
		return supportMessage;
	}

	public void setSupportMessage(String supportMessage) {
		this.supportMessage = supportMessage;
	}

	
	public Long getContact() {
		return contact;
	}

	public void setContact(Long contact) {
		this.contact = contact;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	 
	public String getTechnicianName() {
		if (technicianID != null) {
            return technicianID.getName();
        } else {
            return null;  // ou uma string padrão se preferir
        }
	}

	public String getRequesterName() {
		return requesterID.getName();
	}

	
	
	

}
