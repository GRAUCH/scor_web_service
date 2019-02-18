package com.scor.global;

import java.util.Random;

public class WSException extends Exception{
	
	private static final long serialVersionUID = -7646267726461287419L;
	private Exception exception;
	private Class <?> causeClass;	
	private String causeMethod;
	private String exceptionReferenceCode;

    
    public WSException(Class<?> causeClass, String causeMethod, Exception  exception) {
    	this.setException(exception);
        this.setCauseClass(causeClass);
        this.setCauseMethod(causeMethod);
        this.exceptionReferenceCode = generateExceptionReferenceCode();
    }
    
    public WSException(Class<?> causeClass, String causeMethod, String  exception) {
    	super(exception);
        this.setCauseClass(causeClass);
        this.setCauseMethod(causeMethod);
        this.exceptionReferenceCode = generateExceptionReferenceCode();
    }

    public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Class<?> getCauseClass() {
		return causeClass;
	}

	public void setCauseClass(Class<?> causeClass) {
		this.causeClass = causeClass;
	}

	public String getCauseMethod() {
		return causeMethod;
	}

	public void setCauseMethod(String causeMethod) {
		this.causeMethod = causeMethod;
	}

	public String getExceptionReferenceCode() {
		return exceptionReferenceCode;
	}

	public void setExceptionReferenceCode(String exceptionReferenceCode) {
		this.exceptionReferenceCode = exceptionReferenceCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	protected String generateExceptionReferenceCode () {
		Random random = new Random(System.currentTimeMillis());
		Long randomNumber = random.nextLong();
		
		if (randomNumber < 0) {
			randomNumber = randomNumber * (-1);
		}
		
		String referenceCode = randomNumber.toString();
		short numberOfZerosToAdd = (short) (20 - referenceCode.length());
		
		if (numberOfZerosToAdd > 0) {
			StringBuffer sb = new StringBuffer();
			for (short index = 0; index < numberOfZerosToAdd; index ++) {
				sb.append("0");
			}
			sb.append(referenceCode);
			
			referenceCode = sb.toString();
		}
		
		return referenceCode;
	}  
	
	public String toString(){
		String result=this.getExceptionReferenceCode() + ": " + "La clase "+this.getCauseClass() + " con método "+ this.getCauseMethod() + " ha dado el siguiente error: "+this.getMessage();
		
		return result;
	}
}
