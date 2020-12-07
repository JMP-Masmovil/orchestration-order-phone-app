package es.masmovil.phone.app.orchestration.order.service;

import es.masmovil.phone.app.orchestration.order.dto.OrderDTO;
import es.masmovil.phone.app.orchestration.order.exception.BusinessException;

public interface OrderService {

	/**
	 * Create a new Order
	 * @param orderDto The OrderDto
	 * @return The OrderDto
	 */
	OrderDTO createOrder(OrderDTO orderDto) throws BusinessException;
}
