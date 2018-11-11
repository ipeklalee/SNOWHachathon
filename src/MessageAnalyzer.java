import java.util.List;
import java.util.Map;
import java.io.IOException; 	    
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

public class MessageAnalyzer 
{
	public static void main (String[] Args) throws IOException
	{
		System.out.println( "Please enter the header: ");
		
		//All headers must be stored in this array
		String headerArray[] = {"Return-Path", "Delivered-To","Received", "X-ServiceNow-Spam-Flag", "X-ServiceNow-Spam-Score", "X-ServiceNow-Spam-Level", "X-ServiceNow-Spam-Status", "Authentication-Results", "Received", "Received", "Received", "DKIM-Signature", "Authentication-Results", "Received", "Received", "Received", "From", "To", "Subject", "Thread-Topic", "Thread-Index", "Date", "Message-ID", "Accept-Language", "Content-Language", "X-MS-Has-Attach", "X-MS-TNEF-Correlator", "x-originating-ip", "x-ms-publictraffictype", "x-microsoft-exchange-diagnostics", "x-ms-exchange-antispam-srfa-diagnostics", "x-ms-office365-filtering-ht", "x-microsoft-antispam", "x-ms-traffictypediagnostic", "x-microsoft-antispam-prvs", "x-exchange-antispam-report-test", "x-ms-exchange-senderadcheck", "x-exchange-antispam-report-cfa-test", "x-forefront-prvs", "x-forefront-antispam-report", "received-spf", "x-microsoft-antispam-message-info", "spamdiagnosticoutput", "spamdiagnosticmetadata", "Content-Type", "MIME-Version", "X-MS-Office365-Filtering-Correlation-Id", "X-OriginatorOrg", "X-MS-Exchange-CrossTenant-Network-Message-Id", "X-MS-Exchange-CrossTenant-originalarrivaltime", "X-MS-Exchange-CrossTenant-fromentityheader", "X-MS-Exchange-CrossTenant-id", "X-MS-Exchange-Transport-CrossTenantHeadersStamped", "X-Proofpoint-SPF-Result", "X-Proofpoint-SPF-Record", "X-Proofpoint-Virus-Version", "X-Proofpoint-Spam-Details", "X-ServiceNow-Source", "X-ServiceNow-SysEmail-Version", "MultipartContentTypes"};
		//Input from the console
       Scanner input = new Scanner(System.in);
       String lineNew, text=""; 
       List<String> separatedText = new ArrayList<>();

        while (input.hasNextLine()) 
        {
            lineNew = input.nextLine();
            text = text + '\n' + lineNew;
            //All text will be stored word by word in below list
            separatedText.addAll(Arrays.asList(lineNew.split("\\s+")));
            
            if (lineNew.isEmpty()) 
            {
                break;
            }
        }
        
        //Count to catch two headers cnt=2 means we have hit two headers
        int cnt = 0;
        
        //When we get two headers it is time to get the content between headers
        String[] currHeaders = new String[2];
        
        //Headers and contents will be hold in a linkedhashmap
        LinkedHashMap <String, String> headerContentMap = new LinkedHashMap<>();
        
        //Each word in the text is traced
        for(int i=0; i<separatedText.size(); i++)
        {
        		//Each header is traced
        		for(String s:headerArray)
        		{
        			//if the word contains any of the headers
				 if(separatedText.get(i).contains(s))
				 {
					 //the header is hold in current headers array
					 currHeaders[cnt] = separatedText.get(i);
					 cnt++;
					 if(cnt != 2)
						 break;
				 }
				 //if two headers are hit,it is time to truncate the content between headers which will belong to the first header
				 //sample; currHeaders[0] = X-ServiceNow-Spam-Score ; currHeaders[1] = X-ServiceNow-Spam-Status
				 //content = -3.01
				 //Text: X-ServiceNow-Spam-Score CONTENT: :-3.01 X-ServiceNow-Spam-Status: ...
				 if(cnt==2)
				 {
					String headerContent = StringUtils.substringBetween(text, currHeaders[0], currHeaders[1]);	
					headerContentMap.put(currHeaders[0], headerContent);						
					currHeaders[0] = separatedText.get(i);
					cnt=1;
					break;
				 }
        		}
        }
        
        //iterate through the header content map
        Iterator it = headerContentMap.entrySet().iterator();
		System.out.println("HEADERS");
	    while (it.hasNext()) 
	    {
	    		l++;
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println("HEADER: " + pair.getKey() + " CONTENT: " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
}
