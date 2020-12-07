package es.masmovil.phone.app.orchestration.order.service;

import es.masmovil.phone.app.orchestration.order.dto.CustomerDTO;
import es.masmovil.phone.app.orchestration.order.exception.BusinessException;

public interface CustomerService {

	/**
	 * Create a new Customer
	 * @param customerDTO The CustomerDTO
	 * @return CustomerDTO 
	 * @throws BusinessException 
	 */
	CustomerDTO createCustomer(CustomerDTO customerDTO)  throws BusinessException;
	
	/**
	 * Delete a Customer
	 * @param customerIdentifier The customer id
	 * @throws BusinessException
	 */
	void deleteCustomer(String customerIdentifier) throws BusinessException;

}
