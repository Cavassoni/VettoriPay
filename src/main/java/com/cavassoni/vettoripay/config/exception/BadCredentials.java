package com.cavassoni.vettoripay.config.exception;

import java.io.Serial;

public class BadCredentials extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	public BadCredentials(String message) {
		super(message);
	}

	public BadCredentials(String message, Object... args) {
		super(String.format(message, args));
	}
}
