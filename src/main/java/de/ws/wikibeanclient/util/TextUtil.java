/**
 * 
 */
package de.ws.wikibeanclient.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Michael
 * @date 30.06.2013
 */
public class TextUtil {
	
	private static Logger log = LogManager.getLogger(TextUtil.class);
	
	/**
	 * checks if the string is set.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param s the string to check
	 * @return true if s is not null and s contains characters other than whitespace.
	 */
	public static boolean isSet(String s) {
		return (s!=null && s.trim().length()>0);
	}
	
	/**
	 * trims a string.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param s the string to trim.
	 * @return the trimmed string if s is not null; null otherwise. 
	 */
	public static String trim(String s) {
		return (s==null ? null : s.trim());
	}
	
	/**
	 * truncates a string to a maximum length.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param s
	 * @param maxSize
	 * @return
	 */
	public static String truncate(String s, int maxSize) {
		if(s!=null && s.length()>maxSize) {
			return s.substring(0, maxSize);
		} else {
			return s;
		}
	}
	
	/**
	 * compares two strings. The strings are trimmed before comparison.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param s
	 * @param t
	 * @return
	 */
	public static boolean isDifferent(String s, String t) {
		if(s==null && t==null) {
			return false;
		} else {
			s = trim(s);
			t = trim(t);
			if(s!=null && !s.equals(t)) {
				return true;
			} else if(t!=null && !t.equals(s)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * removes a certain prefix from a string
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param string
	 * @param prefix
	 * @return
	 */
	public static String removePrefixFromString(String string, String prefix) {
		
		if(string!=null && prefix!=null) {
			if(string.startsWith(prefix)) {
				return string.substring(prefix.length()).trim();
			} 
		}
		return string;
	}
	
	/**
	 * compares the length of a string
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param s
	 * @param t
	 * @return true if s.length > t.length or s is not null and t is null; false otherwise
	 */
	public static boolean isLonger(String s, String t) {
		if(s==null) {
			return false; 
		} else if (t==null) {
			return true;
		} else {
			return s.length()>t.length();
		}
	}
	
	/**
	 * returns the length of the string.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param s
	 * @return
	 */
	public static int getLength(String s) {
		return (s==null ? 0 : s.length());
	}
	
	/**
	 * returns true if it's a visible keycode.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param keycode
	 * @return
	 */
	public static boolean isVisibleKey(int keycode) {
		return (keycode >= 48 && keycode <= 90) || (keycode >= 186 && keycode <= 192) || (keycode >= 219 && keycode <= 220);
	}
	
	
	/**
	 * returns the content of the file as a string.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param filename
	 * @return
	 */
	public static String getStringFromFile(String filename) {
		String result = null;
		try {
			File file = new File(filename);
			FileInputStream fis = new FileInputStream(file);
			
			java.util.Scanner s = new java.util.Scanner(fis, "UTF-8").useDelimiter("\\A");
			result = s.hasNext() ? s.next() : "";
			
		} catch (Exception e) {
			log.error("Exception occured loading file: "+e.getMessage(), e);
		}
		
		return result;
	}
	
	/**
	 * returns the content of the stream as a string.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param inputStream
	 * @return
	 */
	public static String getStringFromInputStream(InputStream inputStream) {
		if (inputStream != null) {
			java.util.Scanner s = new java.util.Scanner(inputStream, "UTF-8")
					.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		}
		return null;
	}
	
	/**
	 * transforms an array into a single string with delimiter in between each item.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param list
	 * @param delimiter
	 * @return
	 */
	public static String getSingleStringFromList(String[] list, String delimiter) {
		String result = null;
		if(delimiter!=null && list!=null && list.length>0) {
			result = "";
			for(String string : list) {
				result += string+delimiter;
			}
			if(result.endsWith(delimiter)) {
				result = result.substring(0, result.length()-delimiter.length());
			}
		}
		
		return result;
	}
}
