package cdg.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.security.crypto.bcrypt.BCrypt;

import cdg.domain.generation.MapGenerator;

@Entity // This tells Hibernate to make a table out of this class
public class User {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String firstName;
    private String lastName;
    
    @Column(unique=true)
    private String email;
    
    private String password;

    @Transient
    private MapGenerator generator;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String name) {
		this.lastName = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void encryptPassword() {
		this.password = BCrypt.hashpw(this.password, BCrypt.gensalt());
	}
	
	public boolean validatePassword(String plainText) {
		return BCrypt.checkpw(plainText, this.password);
	}
	
	public MapGenerator getGenerator() {
		return generator;
	}
	
	public void setGenerator(MapGenerator generator) {
		this.generator = generator;
	}

}