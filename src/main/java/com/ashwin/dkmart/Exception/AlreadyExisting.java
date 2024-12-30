package com.ashwin.dkmart.Exception;

public class AlreadyExisting extends RuntimeException{

	public AlreadyExisting(String message) {
		super();
		System.out.println(message);
	}
	
}
