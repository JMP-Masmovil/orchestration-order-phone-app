package es.masmovil.phone.app.orchestration.order.controller.transformer;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import es.masmovil.lib.customer.CustomerRSDTO;
import es.masmovil.lib.orchestration.order.OrderRSDTO;
import es.masmovil.phone.app.orchestration.order.dto.CustomerDTO;
import es.masmovil.phone.app.orchestration.order.dto.OrderDTO;
import lombok.NonNull;

@Service
public class RestDtoTransformerImpl implements RestDtoTransformer {

	@Override
	public OrderDTO toOrderDto(@NonNull OrderRSDTO orderRequestDTO) {

		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(orderRequestDTO, OrderDTO.class);

	}

	@Override
	public OrderRSDTO toOrderRSDTO(@NonNull OrderDTO orderDto) {

		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(orderDto, OrderRSDTO.class);

	}

	@Override
	public CustomerDTO toCustomerDTO(@NonNull CustomerRSDTO customerRSDTO) {

		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(customerRSDTO, CustomerDTO.class);
	}

	@Override
	public CustomerRSDTO toCustomerRSDTO(@NonNull CustomerDTO customerDTO) {

		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(customerDTO, CustomerRSDTO.class);

	}

	@Override
	public es.masmovil.lib.order.OrderRSDTO toOrderRSDto(@NonNull OrderDTO orderDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(orderDTO, es.masmovil.lib.order.OrderRSDTO.class);
	}

	@Override
	public OrderDTO toOrderDto(@NonNull es.masmovil.lib.order.OrderRSDTO orderRSDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(orderRSDTO, OrderDTO.class);
	}

}
