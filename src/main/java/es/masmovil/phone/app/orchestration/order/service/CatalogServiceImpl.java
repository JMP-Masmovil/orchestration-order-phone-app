package es.masmovil.phone.app.orchestration.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.masmovil.lib.catalog.integration.PhoneRSDTO;
import es.masmovil.lib.catalog.integration.client.CatalogFeignClient;
import es.masmovil.phone.app.orchestration.order.dto.PhoneDTO;

@Service
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private CatalogFeignClient catalogFeignClient;

	@Override
	public boolean validateCatalogPhone(List<PhoneDTO> listPhoneDto) {

		if(Optional.ofNullable(listPhoneDto).filter(List::isEmpty).isPresent()) {
			return false;
		}
		
		for (PhoneDTO phoneDTO : listPhoneDto) {

			ResponseEntity<PhoneRSDTO> responseEntity = catalogFeignClient.getPhoneById(phoneDTO.getIdentifier());
			if (responseEntity.getStatusCode() != HttpStatus.OK
					|| phoneDTO.getPrice().compareTo(new BigDecimal(responseEntity.getBody().getPrice())) != 0) {
				return false;
			}

		}

		return true;

	}

}
