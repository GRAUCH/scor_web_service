package com.scor.global;

import java.util.Collection;
import java.util.Map;


public class ValorUtils {

	private ValorUtils () {
		
	}
	
	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public static boolean isValid (Object parameter) {
		boolean result = true;
		
		if (parameter == null) {
			result = false;
		
		} else {
			result = isValid(parameter.toString());
		} 
		
		return result; 
	}
	
	////////////////////////////////////////
	////// OPERACIONES STRING //////////////
	////////////////////////////////////////
	/**
	 * 
	 * @param valorA
	 * @param valorB
	 * @return
	 */
	public static boolean  igualQue (String valorA, String valorB) {
		return valorA.equals(valorB);
	}
	
	/**
	 * 
	 * @param valorA
	 * @param valorB
	 * @return
	 */
	public static boolean mayorQue (String valorA, String valorB) {
		int result = valorA.compareToIgnoreCase(valorB);
		return (result > 0);
	}
	
	/**
	 * 
	 * @param valorA
	 * @param valorB
	 * @return
	 */
	public static boolean mayorIgualQue (String valorA, String valorB) {
		int result = valorA.compareToIgnoreCase(valorB);
		return (result > -1);
	}
	
	/**
	 * 
	 * @param valorA
	 * @param valorB
	 * @return
	 */
	public static boolean menorQue (String valorA, String valorB) {
		int result = valorA.compareToIgnoreCase(valorB);
		return (result < 0);
	}
	
	/**
	 * 
	 * @param valorA
	 * @param valorB
	 * @return
	 */
	public static boolean menorIgualQue (String valorA, String valorB) {
		int result = valorA.compareToIgnoreCase(valorB);
		return (result < 1);
	}
	
	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public static boolean isValid (String parameter) {
		return (parameter != null && !parameter.trim().isEmpty()); 
	}
	
	///////////////////////////////////////
	//////  OPERACIONES INT  //////////////
	///////////////////////////////////////
	public static boolean  igualQue (int valorA, int valorB) {
		return valorA == valorB;
	}
	
	public static boolean mayorQue (int valorA, int valorB) {
		return (valorA > valorB);
	}
	
	public static boolean mayorIgualQue (int valorA, int valorB) {
		return (valorA >= valorB);
	}
	
	public static boolean menorQue (int valorA, int valorB) {
		return (valorA < valorB);
	}
	
	public static boolean menorIgualQue (int valorA, int valorB) {
		return (valorA <= valorB);
	}
	
	///////////////////////////////////////
	////// OPERACIONES SHORT //////////////
	///////////////////////////////////////	
	public static boolean  igualQue (short valorA, short valorB) {
		return valorA == valorB;
	}
	
	public static boolean mayorQue (short valorA, short valorB) {
		return (valorA > valorB);
	}
	
	public static boolean mayorIgualQue (short valorA, short valorB) {
		return (valorA >= valorB);
	}
	
	public static boolean menorQue (short valorA, short valorB) {
		return (valorA < valorB);
	}
	
	public static boolean menorIgualQue (short valorA, short valorB) {
		return (valorA <= valorB);
	}
	
	///////////////////////////////////////
	////// OPERACIONES FLOAT //////////////
	///////////////////////////////////////	
	public static boolean  igualQue (float valorA, float valorB) {
		return valorA == valorB;
	}
	
	public static boolean mayorQue (float valorA, float valorB) {
		return (valorA > valorB);
	}
	
	public static boolean mayorIgualQue (float valorA, float valorB) {
		return (valorA >= valorB);
	}
	
	public static boolean menorQue (float valorA, float valorB) {
		return (valorA < valorB);
	}
	
	public static boolean menorIgualQue (float valorA, float valorB) {
		return (valorA <= valorB);
	}
	
	///////////////////////////////////////
	////// OPERACIONES LONG ///////////////
	///////////////////////////////////////
	public static boolean igualQue (long valorA, long valorB) {
		return (valorA == valorB);
	}
	
	public static boolean  mayorQue (long valorA, long valorB) {
		return valorA > valorB;
	}
	
	public static boolean mayorIgualQue (long valorA, long valorB) {
		return (valorA >= valorB);
	}
	
	public static boolean menorQue (long valorA, long valorB) {
		return (valorA < valorB);
	}
	
	public static boolean menorIgualQue (long valorA, long valorB) {
		return (valorA <= valorB);
	}
	
	
	///////////////////////////////////////
	/// OPERACIONES COLLECTION y ARRAYS ///
	///////////////////////////////////////
	/**
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean hasElements (Collection <?> collection) {
		return (collection != null && !collection.isEmpty());
	}
	
	public static boolean hasElements (Map <?, ?> map) {
		return (map != null && !map.isEmpty());
	}
	
	public static boolean hasElements (Object[] array) {
		return (array != null && array.length > 0 && array[0] != null);
	}
	
	public static boolean hasElements (byte[] array) {
		return (array != null && array.length > 0);
	}
}
