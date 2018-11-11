import java.io.BufferedReader;
import org.apache.commons.lang.StringUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class headerAnalyzer 
{	
	public static void main (String[] Args) throws IOException
	{
		System.out.println( "Please enter the header: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		
		//All headers must be stored in this array
		String headerArray[] = {
				"Return-Path:",
				"Delivered-To:",
				"Received:",
				"Received:",
				"Received:",
				"X-ServiceNow-Spam-Flag:",
				"X-ServiceNow-Spam-Score:",
				"X-ServiceNow-Spam-Level:",
				"X-ServiceNow-Spam-Status:",
				"Authentication-Results:",
				"Received:",
				"DKIM-Signature:"};
	
		//Output from the console
       Scanner input = new Scanner(System.in);
       String lineNew, text="";

        while (input.hasNextLine()) {
            lineNew = input.nextLine();
            text = text + '\n' + lineNew;
            if (lineNew.isEmpty()) {
                break;
            }
        }
	
        //headerArray array must be converted to list to operate following functions
		List<String> headerList = Arrays.asList(headerArray);
		List <String> existingHeaders = new ArrayList<>();		
		HashMap <String, String> headerContentMap = new HashMap<>();
		
		//Check which headers exist in the text
		for(int i=0; i<headerList.size(); i++)
		{
			if(text.contains(headerList.get(i)) && !existingHeaders.contains(headerList.get(i)))
			{
				//System.out.println(headerList.get(i));
				existingHeaders.add(headerList.get(i));
			}
		}
		
		//Logic starts here
		for(int i=0; (i+1)<existingHeaders.size() ; i++)
		{
			
			String headerContent = StringUtils.substringBetween(text, existingHeaders.get(i), existingHeaders.get(i+1));	
			headerContentMap.put(existingHeaders.get(i), headerContent);
		}

		Iterator it = headerContentMap.entrySet().iterator();
		System.out.println("HEADERLAR BASLIYOOOOORRR!!!!!!!!!");
	    while (it.hasNext()) 
	    {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println("HEADER: " + pair.getKey() + " CONTENT: " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}

	
}
