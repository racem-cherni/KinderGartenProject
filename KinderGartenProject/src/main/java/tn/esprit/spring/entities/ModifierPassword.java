package tn.esprit.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifierPassword {
	private String username;
	private String password;
	private String cfpassword;
	
	
}
