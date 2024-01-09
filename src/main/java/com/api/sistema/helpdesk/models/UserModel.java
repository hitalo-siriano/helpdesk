package com.api.sistema.helpdesk.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;




import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.sistema.helpdesk.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "TB_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="idUser")

public class UserModel  implements UserDetails , Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", updatable = false, nullable = false)
	private UUID idUser;
	
	@NotBlank(message = "The name cannot be blank")
    @Size(max = 255, message = "The name cannot be longer than 255 characters")
    @Column(name = "name", length = 255, nullable = false)

	private String name;

	@NotBlank(message = "Email cannot be blank")
    @Email(message = "The email must be in a valid format")
    @Size(max = 255, message = "The email cannot be longer than 255 characters")
    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

	@Size(max = 255, message = "The position cannot be longer than 255 characters")
    @Column(name = "job", nullable = false, length = 255)
	private String job;

	@Size(max = 255, message = "The department cannot be longer than 255 characters")
    @Column(name = "department",nullable = false , length = 255)
	private String department;

	@NotBlank(message = "Login cannot be blank")
    @Size(max = 255, message = "Login cannot be longer than 255 characters")
    @Column(name = "login", length = 255, nullable = false, unique = true)
	private String login;

	@NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "The password must be at least 8 characters long")
    @Column(name = "password", length = 255, nullable = false)
	private String password;
	


	@OneToMany(mappedBy = "requesterID", cascade = CascadeType.ALL)
	 @JsonManagedReference
	 private List<TicketModel> requestedTickets;

	 @OneToMany(mappedBy = "technicianID", cascade = CascadeType.ALL)
	 @JsonManagedReference
	 private List<TicketModel> technicianResponsibilityTickets;

	


	private UserRole role;

   

	
    public UserModel(String name , String login , String email , String password , String job , String department , UserRole role){

		this.name = name;
		this.login = login;
		this.email = email;
		this.password = password;
		this.job = job;
		this.department = department;
		this.role = role;
	}   
	


	

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
return true;	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
return true;	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
return true;	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
return true;	}

	///////////////////////////////////////////////////////////////////////////////////////////



	


}