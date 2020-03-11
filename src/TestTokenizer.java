import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class that tokenizes the test fragments
 * 
 * @author Javaria Imtiaz
 * @version 1.0
 */

public class TestTokenizer {
	
	public static void main(String[] args) throws IOException {

	
	
	 BufferedReader br = new BufferedReader(new FileReader("TestCases/map.txt"));  //Extract information from 
     BufferedWriter writer = new BufferedWriter (new FileWriter ("TestCases/TestInfo.txt")); 
        String lineRead=br.readLine();
        String locator = "";
        String locatorValue ="";
        String actionType ="";
        String actionValue ="";

        
		     while ((lineRead = br.readLine()) != null)
		    	 {
		    	 String[] st1 = lineRead.split(",");

		             if(st1.length> 0 )
		            	 locator = st1[0].trim();
		                 String[] loc = locator.split("= ");
		                 String  l1 = loc[1].trim();
		                 writer.write(l1);
        		 		 writer.write(","); 
        		 		 
		             if(st1.length> 1 )
		            	 locatorValue= st1[1].trim();
		                 String[] Value = locatorValue.split("= ");
	                     String  v1 = Value[1].trim();
	                     writer.write(v1);
        		 		 writer.write(","); 
        		 	
		             if(st1.length> 2 )
		            	 actionType= st1[2].trim();
		             	 String[] act = actionType.split("= ");
                         String  a1 = act[1].trim();
                         writer.write(a1);
        		 		 writer.write(","); 
                     
		             
		             if(st1.length> 3 )
		            	 actionValue= st1[3].trim();
		             	 String[] actVal = actionValue.split("= ");
	                     String  ac1 = actVal[1].trim();
	                     writer.write(ac1);
			         
		    
	            writer.newLine();

     }

         	    br.close();
         
          writer.close();


}
}