package com.indusnet.ums.model;
import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements UserDetails {
	
	private String id;

	private String name;
	
	@NotBlank(message = "Email cannot be empty")
	@Email
	private String email;

	@NotBlank(message = "Password cannot be empty")
	@Size(min = 5, max = 16, message = "Password should be between 5 to 16 characters")
	private String password;
	
	private Long dateCreated;

	private String otp;

	private Date createdTime;

	private Date expirationTime;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


}
