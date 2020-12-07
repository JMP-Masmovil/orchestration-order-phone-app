package es.masmovil.phone.app.orchestration.order.controller.transformer;

import es.masmovil.lib.customer.CustomerRSDTO;
import es.masmovil.lib.orchestration.order.OrderRSDTO;
import es.masmovil.phone.app.orchestration.order.dto.CustomerDTO;
import es.masmovil.phone.app.orchestration.order.dto.OrderDTO;
import lombok.NonNull;

/**
 * Transformer between service layer and rest layer
 * @author JMP87
 *
 */
public interface RestDtoTransformer {

	/**
	 * Transform OrderRSDTO to OrderDTO
	 * @param orderRequestDTO
	 * @return The OrderDTO
	 */
	OrderDTO toOrderDto(@NonNull OrderRSDTO orderRequestDTO);
	
	/**
	 * Transform OrderDTO to OrderRSDTO
	 * @param orderDto
	 * @return The OrderRSDTO
	 */
	OrderRSDTO toOrderRSDTO(@NonNull OrderDTO orderDto);
	
	/**
	 * Transform CustomerRSDTO to CustomerDTO
	 * @param customerRSDTO 
	 * @return The CustomerDTO
	 */
	CustomerDTO toCustomerDTO(@NonNull CustomerRSDTO customerRSDTO);
	
	/**
	 * Transform CustomerDTO to CustomerRSDTO
	 * @param customerDTO 
	 * @return The CustomerRSDTO
	 */
	CustomerRSDTO toCustomerRSDTO(@NonNull CustomerDTO customerDTO);
	
	
	
	/**
	 * Transform the OrderDTO to OrderRSDTO
	 * 
	 * @param orderDTO The OrderDTO
	 * @return The OrderRSDTO
	 */
	es.masmovil.lib.order.OrderRSDTO toOrderRSDto(@NonNull OrderDTO orderDTO);

	/**
	 * Transform the OrderRSDTO to OrderDTO
	 * 
	 * @param orderRSDTO The OrderRSDTO
	 * @return The OrderDTO
	 */
	OrderDTO toOrderDto(@NonNull es.masmovil.lib.order.OrderRSDTO orderRSDTO);
}
