package com.mideros.diceGame.exception;

public class PlayerExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlayerExistsException(String message) {
		super(message);
	}
}
