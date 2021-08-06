import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * A class which is responsible for creating test fragments
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
		    	 String[] st1 = lineRead.split(",");  //locator= xpath,locatorValue= "(//INPUT[@type='text'])[1]",Action= sendKeys,Action Value= "Muhammad"

		             if(st1.length> 0 )
		             {
		            	 locator = st1[0].trim();  //locator= xpath
		                 String[] loc = locator.split("= "); 
		                 String  l1 = loc[1].trim();  //xpath
		                 writer.write(l1);
        		 		 writer.write(","); 
        		 		 System.out.println(l1);
		             }
        		 		 
		             if(st1.length> 1 )
		             {
		            	 locatorValue= st1[1].trim();
		            	 if (locatorValue.contains("locator")) //locatorValue= "(//INPUT[@type='text'])[1]"
		            	 	{
		            		 String []splitterString=locatorValue.split("= \"");
		                     String  v1 = splitterString[1].trim(); // (//INPUT[@type='text'])[1]
	                         writer.write(v1);
	                          
		            	 	}
		            	 else if (locatorValue.contains("wait")) //wait value= Thread.sleep(2000)
		            	 	{
		            		 String []splitterString=locatorValue.split("= ");
		                     String  v1 = splitterString[1].trim(); 
	                         writer.write(v1);
	                         writer.write(","); 
	                       
		            	 }
		            	 else 
		            	 {
		            		 String []splitterString=locatorValue.split("= ");
		                     String  v1 = splitterString[1].trim(); 
	                         writer.write(v1);
		            	 }
        		 		 
		             }
        		 	
		             if(st1.length> 2 )
		             {
		            	 writer.write(","); 
		            	 actionType= st1[2].trim();  //Action= sendKeys 
		             	 String[] act = actionType.split("= ");
                         String  a1 = act[1].trim(); //sendKeys 
                         writer.write(a1);
                      
		             }
		             
		             if(st1.length> 3 )
		             {
        		 		 writer.write(","); 
		            	 actionValue= st1[3].trim(); //Action Value= "Muhammad"
		             	 String[] actVal = actionValue.split("= ");
	                     String  ac1 = actVal[1].trim();
                         writer.write(ac1);
		             }
	                     
	            writer.newLine();

     }

         	    br.close();
         
          writer.close();


}
}