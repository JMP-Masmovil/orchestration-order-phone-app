package es.masmovil.phone.app.orchestration.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

	private String identifier;
	
	private String name;
	
	private String surname;
	
	private String email;
}
