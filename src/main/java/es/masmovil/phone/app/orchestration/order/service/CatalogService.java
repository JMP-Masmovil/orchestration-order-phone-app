package es.masmovil.phone.app.orchestration.order.service;

import java.util.List;

import es.masmovil.phone.app.orchestration.order.dto.PhoneDTO;

public interface CatalogService {

	
	boolean validateCatalogPhone(List<PhoneDTO> listPhoneDto);
}
