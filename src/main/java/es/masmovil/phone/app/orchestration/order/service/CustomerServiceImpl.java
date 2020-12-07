package es.masmovil.phone.app.orchestration.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.masmovil.lib.customer.CustomerRSDTO;
import es.masmovil.lib.customer.client.CustomerFeignClient;
import es.masmovil.phone.app.orchestration.order.controller.transformer.RestDtoTransformer;
import es.masmovil.phone.app.orchestration.order.dto.CustomerDTO;
import es.masmovil.phone.app.orchestration.order.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerFeignClient customerFeignClient;

	@Autowired
	private RestDtoTransformer restDtoTransformer;

	@Override
	public CustomerDTO createCustomer(CustomerDTO customerDTO) throws BusinessException {

		CustomerRSDTO customerRSDTO = restDtoTransformer.toCustomerRSDTO(customerDTO);
		ResponseEntity<CustomerRSDTO> responseEntity = customerFeignClient.createCustomer(customerRSDTO);

		if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
			log.error("Error create customer" + responseEntity.getStatusCode());
			throw new BusinessException("Error create customer");
		}

		return restDtoTransformer.toCustomerDTO(responseEntity.getBody());
	}

	@Override
	public void deleteCustomer(String customerIdentifier) throws BusinessException {

		ResponseEntity<Void> responseEntity = customerFeignClient.deleteCustomer(customerIdentifier);

		if (responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
			log.error("Error delete customer" + responseEntity.getStatusCode());
			throw new BusinessException("Error create customer");
		}
	}

}
