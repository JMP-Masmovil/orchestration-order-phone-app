package es.masmovil.phone.app.orchestration.order.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDTO {

	private String identifier;

	private CustomerDTO customerDto;
	
	private List<PhoneDTO> listPhone;
	
	private BigDecimal totalPrice;
}
