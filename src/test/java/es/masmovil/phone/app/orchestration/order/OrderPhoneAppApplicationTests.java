package es.masmovil.phone.app.orchestration.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import es.masmovil.lib.catalog.integration.client.CatalogFeignClient;
import es.masmovil.lib.customer.client.CustomerFeignClient;
import es.masmovil.lib.orchestration.order.CustomerRSDTO;
import es.masmovil.lib.orchestration.order.OrderRSDTO;
import es.masmovil.lib.orchestration.order.PhoneRSDTO;
import es.masmovil.lib.orchestration.order.client.OrchestrationOrderFeignClient;
import es.masmovil.lib.order.client.OrderFeignClient;
import feign.FeignException.BadRequest;

@ComponentScan("es.masmovil")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/test.properties")
class OrderPhoneAppApplicationTests {


	@Autowired
	private OrchestrationOrderFeignClient orchestrationOrderFeignClient;

	@MockBean
	private CustomerFeignClient customerFeignClient;
	
	@MockBean
	private CatalogFeignClient catalogFeignClient;
	
	@MockBean
	private OrderFeignClient orderFeignClient;
	
	private static final String NAME = "Jose";
	private static final String SURNAME = "Perez";
	private static final String EMAIL = "google@gmail.es";
	private static final String CUSTOMER_ID = "1234-aaaa";

	private static final String PHONE_ID1 = "abc";
	private static final String PHONE_ID2 = "def";
	private static final String PHONE_ID3 = "qwe";
	
	private static final String PHONE_PRICE1 = "100";
	private static final String PHONE_PRICE2 = "200";
	
	private static final String ORDER_ID = "order1234";
	


	@Test
	void testCreateOrder() {

		preparteTestCreateOrder();
		
		OrderRSDTO orderRSDTO = new OrderRSDTO();
		CustomerRSDTO customerDto = new CustomerRSDTO();
		customerDto.setName(NAME);
		customerDto.setSurname(SURNAME);
		customerDto.setEmail(EMAIL);
		orderRSDTO.setCustomerDto(customerDto);
		
		List<PhoneRSDTO> listPhoneDto = new ArrayList<>();
		listPhoneDto.add(new PhoneRSDTO(PHONE_ID1, PHONE_PRICE1));
		listPhoneDto.add(new PhoneRSDTO(PHONE_ID2, PHONE_PRICE2));
		
		orderRSDTO.setListPhoneDto(listPhoneDto);
		ResponseEntity<OrderRSDTO> response = orchestrationOrderFeignClient.orchestrateOrder(orderRSDTO);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ORDER_ID, response.getBody().getIdentifier());
		assertEquals(BigDecimal.valueOf(300), response.getBody().getTotalPrice());
		assertEquals(CUSTOMER_ID, response.getBody().getCustomerDto().getIdentifier());


	}
	
	
	@Test
	void testEmptyOrder() {

		preparteTestEmptyOrder();
		
		OrderRSDTO orderRSDTO = new OrderRSDTO();
		CustomerRSDTO customerDto = new CustomerRSDTO();
		customerDto.setName(NAME);
		customerDto.setSurname(SURNAME);
		customerDto.setEmail(EMAIL);
		orderRSDTO.setCustomerDto(customerDto);
		
		List<PhoneRSDTO> listPhoneDto = new ArrayList<>();
		
		orderRSDTO.setListPhoneDto(listPhoneDto);
		try {
			orchestrationOrderFeignClient.orchestrateOrder(orderRSDTO);
		}catch (BadRequest e) {
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.status());

		}
	

	}

	
	@Test
	void testInvalidPhoneId() {

		preparteTestInvalidPhone();
		
		OrderRSDTO orderRSDTO = new OrderRSDTO();
		CustomerRSDTO customerDto = new CustomerRSDTO();
		customerDto.setName(NAME);
		customerDto.setSurname(SURNAME);
		customerDto.setEmail(EMAIL);
		orderRSDTO.setCustomerDto(customerDto);
		
		List<PhoneRSDTO> listPhoneDto = new ArrayList<>();
		listPhoneDto.add(new PhoneRSDTO(PHONE_ID3, BigDecimal.valueOf(0).toString()));

		orderRSDTO.setListPhoneDto(listPhoneDto);
		try {
			orchestrationOrderFeignClient.orchestrateOrder(orderRSDTO);
		}catch (BadRequest e) {
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.status());

		}
	

	}
	
	@Test
	void testInvalidPhonePrice() {

		preparteTestInvalidPhone();
		
		OrderRSDTO orderRSDTO = new OrderRSDTO();
		CustomerRSDTO customerDto = new CustomerRSDTO();
		customerDto.setName(NAME);
		customerDto.setSurname(SURNAME);
		customerDto.setEmail(EMAIL);
		orderRSDTO.setCustomerDto(customerDto);
		
		List<PhoneRSDTO> listPhoneDto = new ArrayList<>();
		listPhoneDto.add(new PhoneRSDTO(PHONE_ID1, BigDecimal.valueOf(0).toString()));

		orderRSDTO.setListPhoneDto(listPhoneDto);
		try {
			orchestrationOrderFeignClient.orchestrateOrder(orderRSDTO);
		}catch (BadRequest e) {
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.status());

		}
	

	}
	
	
	private void preparteTestInvalidPhone() {
		
		//Mock create Customer
		 mockCreateCustomer();

		 //Mock delete customer
		 mockDeleteCustomer();
		 
		 mockVerifyPhone();
		 

		
	}
	
	private void preparteTestEmptyOrder() {
		
		//Mock create Customer
		 mockCreateCustomer();

		 //Mock delete customer
		 mockDeleteCustomer();
		
	}
	
	
	private void preparteTestCreateOrder() {
		
		 mockCreateCustomer();
		 
		 mockVerifyPhone();
		 
		//Mock create Order
		es.masmovil.lib.order.OrderRSDTO orderRSDTO = new es.masmovil.lib.order.OrderRSDTO();
		orderRSDTO.setCustomerIdentifier(CUSTOMER_ID);
		List<es.masmovil.lib.order.PhoneRSDTO> listPhone = new ArrayList<>();
		listPhone.add(new es.masmovil.lib.order.PhoneRSDTO(PHONE_ID1, PHONE_PRICE1));
		listPhone.add(new es.masmovil.lib.order.PhoneRSDTO(PHONE_ID2, PHONE_PRICE2));
		orderRSDTO.setListPhone(listPhone);
		
		es.masmovil.lib.order.OrderRSDTO orderRSResponseDTO = new es.masmovil.lib.order.OrderRSDTO();
		orderRSResponseDTO.setCustomerIdentifier(CUSTOMER_ID);
		orderRSResponseDTO.setListPhone(listPhone);
		orderRSResponseDTO.setTotalPrice("300");
		orderRSResponseDTO.setIdentifier(ORDER_ID);
		
		ResponseEntity<es.masmovil.lib.order.OrderRSDTO> value = new ResponseEntity<>(orderRSResponseDTO,HttpStatus.CREATED);
		Mockito.when(orderFeignClient.createOrder(orderRSDTO)).thenReturn(value);
		
		
	}
	
	private void mockVerifyPhone() {

		//Mock verify Phone
		es.masmovil.lib.catalog.integration.PhoneRSDTO phoneRSDTO1 = new es.masmovil.lib.catalog.integration.PhoneRSDTO(
				PHONE_ID1, null, PHONE_PRICE1, null, null);
		Mockito.when(catalogFeignClient.getPhoneById(PHONE_ID1))
		.thenReturn(new ResponseEntity<es.masmovil.lib.catalog.integration.PhoneRSDTO>(phoneRSDTO1,HttpStatus.OK));
		
		es.masmovil.lib.catalog.integration.PhoneRSDTO phoneRSDTO2 = new es.masmovil.lib.catalog.integration.PhoneRSDTO(
				PHONE_ID2, null, PHONE_PRICE2, null, null);
		Mockito.when(catalogFeignClient.getPhoneById(PHONE_ID2))
		.thenReturn(new ResponseEntity<es.masmovil.lib.catalog.integration.PhoneRSDTO>(phoneRSDTO2,HttpStatus.OK));
		
		Mockito.when(catalogFeignClient.getPhoneById(PHONE_ID3))
		.thenReturn(new ResponseEntity<es.masmovil.lib.catalog.integration.PhoneRSDTO>(HttpStatus.NOT_FOUND));

	}


	private void mockCreateCustomer() {
		
		//Mock create Customer
		es.masmovil.lib.customer.CustomerRSDTO customerRSDTO  = new es.masmovil.lib.customer.CustomerRSDTO();
		customerRSDTO.setName(NAME);
		customerRSDTO.setSurname(SURNAME);
		customerRSDTO.setEmail(EMAIL);
		
		es.masmovil.lib.customer.CustomerRSDTO customerResponseRSDTO  = new es.masmovil.lib.customer.CustomerRSDTO();
		customerResponseRSDTO.setName(NAME);
		customerResponseRSDTO.setSurname(SURNAME);
		customerResponseRSDTO.setEmail(EMAIL);
		customerResponseRSDTO.setIdentifier(CUSTOMER_ID);
		Mockito.when(customerFeignClient.createCustomer(customerRSDTO))
			.thenReturn(new ResponseEntity<>(customerResponseRSDTO,HttpStatus.CREATED));
		
	}
	
	
	private void mockDeleteCustomer() {
		
		//Mock delete Customer
		Mockito.when(customerFeignClient.deleteCustomer(CUSTOMER_ID))
			.thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		
	}
}
