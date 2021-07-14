package com.authetication.session.Authetication.Session.errorExceptions;

import lombok.Data;

@Data
public class ErrorObject {

	private String message;
	private String field;
	private Object parameter;
}
