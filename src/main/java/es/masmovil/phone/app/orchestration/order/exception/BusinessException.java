package es.masmovil.phone.app.orchestration.order.exception;

import lombok.Getter;

@Getter
public class BusinessException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8209184001730024089L;
	
	private final String error;
	
	public BusinessException(String error) {
		this.error = error;
	}



}
