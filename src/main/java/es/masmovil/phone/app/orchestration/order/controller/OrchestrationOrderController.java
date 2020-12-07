package es.masmovil.phone.app.orchestration.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.masmovil.lib.orchestration.order.OrderRSDTO;
import es.masmovil.phone.app.orchestration.order.controller.transformer.RestDtoTransformer;
import es.masmovil.phone.app.orchestration.order.dto.OrderDTO;
import es.masmovil.phone.app.orchestration.order.exception.BusinessException;
import es.masmovil.phone.app.orchestration.order.service.OrchestrationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("${ms.api.basepath}")
public class OrchestrationOrderController {

	@Autowired
	private RestDtoTransformer orderControllerTransformer;

	@Autowired
	private OrchestrationService orderService;

	
	@PostMapping
	public ResponseEntity<OrderRSDTO> orchestrateOrder(@RequestBody OrderRSDTO orderRSDTO) {

		log.debug("inicio - orchestrateOrder");
		OrderDTO orderDTO = orderControllerTransformer.toOrderDto(orderRSDTO);

		try {
			orderDTO = orderService.orchestrateOrder(orderDTO);
		} catch (BusinessException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		OrderRSDTO orderResponseRSDTO = orderControllerTransformer.toOrderRSDTO(orderDTO);

		log.debug("fin - orchestrateOrder");

		return new ResponseEntity<>(orderResponseRSDTO, HttpStatus.CREATED);
	}

}
