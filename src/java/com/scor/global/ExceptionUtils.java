package com.scor.global;

public class ExceptionUtils {

	private static final int MAX_DEPTH = 3;
	
	/**
	 * The default constructor is hidden (private) as the method in the class are static
	 */
	private ExceptionUtils() {}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public static String getStackTraceAsString (Throwable t) {
		StringBuilder result = new StringBuilder();
		if (ValorUtils.hasElements(t.getStackTrace())) {
			result.append("Registro del error: ");
			for (StackTraceElement stElem: t.getStackTrace()) {
				result.append(stElem.toString()).append("\n");
			}
		} 
		
		return result.toString();
	}

	/**
	 * 
	 * @param string
	 * @param e
	 * @return
	 */
	public static String composeMessage(String customMessage, Exception e) {
		StringBuilder result = new StringBuilder();
		if (ValorUtils.isValid(customMessage))  {
			result.append(customMessage).append("\n\n");
		}
		
		int initDepthLevel = 0;
		String causedBy = composeMessage(e, initDepthLevel);
		
		if (causedBy != null && !causedBy.trim().equals("")) {
			result.append ("Causa: ").append(causedBy);
		}
		
		return result.toString();
	} 
	
	/**
	 * 
	 * @param string
	 * @param e
	 * @return
	 */
	private static String composeMessage(Throwable t, int depthLevel) {
		StringBuilder result = new StringBuilder();
		
		if (t != null && depthLevel < MAX_DEPTH) {
			if (ValorUtils.isValid(t.getMessage()))  {
				result.append(t.getMessage()).append("\n\n");
			}
			
			if (ValorUtils.hasElements(t.getStackTrace())) {
				result.append(getStackTraceAsString(t));
			}
			
			String causedBy = composeMessage(t.getCause(), ++depthLevel);
			
			if (causedBy != null && !causedBy.trim().equals("")) {
				result.append ("Causa: ").append(causedBy);
			}
		}

		return result.toString();
	} 
}
