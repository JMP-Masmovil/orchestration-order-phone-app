package es.masmovil.phone.app.orchestration.order.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {

	private String identifier;
	
	private BigDecimal price;
		
}
