package com.backend.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadCredentialExc extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadCredentialExc(String message) {
        super(message);
    }

    public BadCredentialExc(String message, Throwable cause) {
        super(message, cause);
    }
	
	
}
