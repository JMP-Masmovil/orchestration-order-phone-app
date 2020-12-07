package es.masmovil.phone.app.orchestration.order.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.masmovil.phone.app.orchestration.order.dto.CustomerDTO;
import es.masmovil.phone.app.orchestration.order.dto.OrderDTO;
import es.masmovil.phone.app.orchestration.order.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrchestrationServiceImpl implements OrchestrationService {

	@Autowired
	private CatalogService catalogService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	@Override
	public OrderDTO orchestrateOrder(OrderDTO order) throws BusinessException {

		try {
			// Create the customer
			CustomerDTO customerDto = customerService.createCustomer(order.getCustomerDto());
			order.getCustomerDto().setIdentifier(customerDto.getIdentifier());

			// Validate the phone Catalog
			if (!catalogService.validateCatalogPhone(order.getListPhone())) {
				throw new BusinessException("Error invalid phones");
			}

			// Create the Order
			orderService.createOrder(order);

		} catch (Exception e) {
			log.error("Rollaback order",e);
			if (Optional.ofNullable(order.getCustomerDto().getIdentifier()).isPresent()) {
				customerService.deleteCustomer(order.getCustomerDto().getIdentifier());
			}
			throw e;
		}

		log.info(toJsonFormat(order));

		return order;

	}

	public static String toJsonFormat(Object object) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(object);
	}
}
