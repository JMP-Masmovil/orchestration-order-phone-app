package es.masmovil.phone.app.orchestration.order.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.masmovil.lib.order.OrderRSDTO;
import es.masmovil.lib.order.client.OrderFeignClient;
import es.masmovil.phone.app.orchestration.order.controller.transformer.RestDtoTransformer;
import es.masmovil.phone.app.orchestration.order.dto.OrderDTO;
import es.masmovil.phone.app.orchestration.order.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderFeignClient orderFeignClient;
	
	@Autowired
	private RestDtoTransformer restDtoTransformer;
	
	@Override
	public OrderDTO createOrder(OrderDTO orderDto) throws BusinessException {

		OrderRSDTO orderRSDTO = restDtoTransformer.toOrderRSDto(orderDto);
		ResponseEntity<OrderRSDTO> responseEntity = orderFeignClient.createOrder(orderRSDTO);
		
		if(responseEntity.getStatusCode() != HttpStatus.CREATED) {
			log.error("Error create customer" + responseEntity.getStatusCode());
			throw new BusinessException("Error create customer");
		}
		
		orderDto.setTotalPrice(new BigDecimal(responseEntity.getBody().getTotalPrice()));
		orderDto.setIdentifier(responseEntity.getBody().getIdentifier());
		
		return orderDto;
		
		
	}

}
