package com.cavassoni.vettoripay.config.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
	private String message;
	private String rootCause;
	private List<String> errorDetail;
}
