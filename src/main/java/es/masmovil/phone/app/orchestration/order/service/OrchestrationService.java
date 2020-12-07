package es.masmovil.phone.app.orchestration.order.service;

import es.masmovil.phone.app.orchestration.order.dto.OrderDTO;
import es.masmovil.phone.app.orchestration.order.exception.BusinessException;

public interface OrchestrationService {

	/**
	 * Orchestrate the steps to create an Order
	 * @param order The Order
	 * @return The Order
	 * @throws BusinessException
	 */
	OrderDTO orchestrateOrder(OrderDTO order) throws BusinessException;
	

}
