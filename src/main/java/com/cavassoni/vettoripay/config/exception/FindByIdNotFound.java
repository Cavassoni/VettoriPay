package com.cavassoni.vettoripay.config.exception;

import java.io.Serial;

public class FindByIdNotFound extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	public FindByIdNotFound(String message) {
		super(message);
	}

	public FindByIdNotFound(String message, Object... args) {
		super(String.format(message, args));
	}
}
