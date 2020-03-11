import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that parse information from existing test cases
 * 
 * @author Javaria Imtiaz
 * @version 1.0
 */
public class TestParser {


	public static void parseTestCases() throws IOException {
		
		

        BufferedReader br = new BufferedReader(new FileReader("TestCases/addNewContact.txt")); //read test cases to convert them into test models
        BufferedWriter writer = new BufferedWriter (new FileWriter ("TestCases/map.txt")); //parse information from test cases and store them into map file
            String line;
           
           	 String strLine;
                ArrayList<String> lines = new ArrayList<String>();
                try {
                    while ((strLine = br.readLine()) != null) {
                                            	 
                   	 if (strLine.contains("public"))
                   	 {
                       	 System.out.println( strLine.substring(strLine.indexOf("test"), strLine.indexOf('(') + 0));
                       	 String TestCaseName= strLine.substring(strLine.indexOf("test"), strLine.indexOf('(') + 0);
                       	 writer.write("testCaseName= " + TestCaseName);
                       	 writer.newLine();
                   	 }
                   	 
                   	 if (strLine.contains("driver"))
                   			 {
                   		 
                   		 		if (strLine.contains("By.xpath") && !strLine.contains("assert") )
                   		 		{
                   		 			
                   		 			String locator= "xpath";
                   		 			String locatorValue= strLine.substring(strLine.indexOf("xpath(") + 6, strLine.indexOf("))") + 0);
                   		 			writer.write("locator= " + locator);
                   		 			writer.write(",");
                   		 			writer.write("locatorValue= " + locatorValue);
                   		 			
		                    		 			if (strLine.contains("click()"))
		                    		 			{
		                    		 				writer.write(",");
		                    		 				writer.write("Action= " + "click");
		                    		 			}
		                    		 			else if (strLine.contains("sendKeys"))
		                    		 			{
		                    		 				writer.write(",");
		                    		 				writer.write("Action= " + "sendKeys");
		                    		 				writer.write(",");
		                                            String actionValue= strLine.substring(strLine.indexOf(".sendKeys(") + 10, strLine.indexOf(");") + 0);
		                        		 			writer.write("Action Value= " + actionValue);
		                    		 			}
		                    		 			else if (strLine.contains("selectByValue"))
		                    		 			{
		                    		 				writer.write(",");
		                    		 				writer.write("Action= " + "selectByValue");
		                    		 				writer.write(",");
		                                            String actionValue= strLine.substring(strLine.indexOf(".selectByValue(") + 15, strLine.indexOf(");") + 0);
		                        		 			writer.write("Action Value= " + actionValue);
		                    		 			}
                   		 			
		                    		 			else if (strLine.contains("clear()"))
		                    		 			{
		                    		 				writer.write(",");
		                    		 				writer.write("Action= " + "clear");
		                    		 			}
		                    		 			else if (strLine.contains("getText()"))
		                    		 			{
		                    		 				writer.write(",");
		                    		 				writer.write("Action= " + "getText");
		           
		                    		 			}
                   		 			
                   		 			writer.newLine();
                   		 			
                   		 		}
                   		 
                   		 		else if (strLine.contains("By.id"))
                   		 		{
                   		 			String locator= "id";
                   		 			String locatorValue= strLine.substring(strLine.indexOf("By.id") + 6, strLine.indexOf(')') + 0);
                   		 			writer.write("locator= " + locator);
                   		 			writer.write(",");
                   		 			writer.write("locatorValue= " + locatorValue);
                   		 			writer.write(",");
                   		 			if (strLine.contains("click()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "click");
                   		 			}
                   		 			else if (strLine.contains("sendKeys"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "sendKeys");
                   		 				writer.write(",");
                                           String actionValue= strLine.substring(strLine.indexOf(".sendKeys(") + 10, strLine.indexOf(");") + 0);
                       		 			writer.write("Action Value= " + actionValue);
                   		 			}
                   		 			else if (strLine.contains("selectByValue"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "selectByValue");
                   		 				writer.write(",");
                                           String actionValue= strLine.substring(strLine.indexOf(".selectByValue(") + 15, strLine.indexOf(");") + 0);
                       		 			writer.write("Action Value= " + actionValue);
                   		 			}
           		 			
                   		 			else if (strLine.contains("clear()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "clear");
                   		 			}
           		 			
                   		 			else if (strLine.contains("getText()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "getText");
          
                   		 			}
           		 			writer.newLine();
                   		 		}
                   		 		
                   		 		else if (strLine.contains("By.css"))
                   		 		{
                   		 			String locator= "css";
                   		 			String locatorValue= strLine.substring(strLine.indexOf("By.css") + 6, strLine.indexOf(')') + 0);
                   		 			writer.write("locator= " + locator);
                   		 			writer.write(",");
                   		 			writer.write("locatorValue= " + locatorValue);
                   		 			writer.newLine();
                   		 			writer.write(",");
                   		 			if (strLine.contains("click()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "click");
                   		 			}
                   		 			else if (strLine.contains("sendKeys"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "sendKeys");
                   		 				writer.write(",");
                                           String actionValue= strLine.substring(strLine.indexOf(".sendKeys(") + 10, strLine.indexOf(");") + 0);
                       		 			writer.write("Action Value= " + actionValue);
                   		 			}
                   		 			else if (strLine.contains("selectByValue"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "selectByValue");
                   		 				writer.write(",");
                                           String actionValue= strLine.substring(strLine.indexOf(".selectByValue(") + 15, strLine.indexOf(");") + 0);
                       		 			writer.write("Action Value= " + actionValue);
                   		 			}
           		 			
                   		 			else if (strLine.contains("clear()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "clear");
                   		 			}
                   		 			else if (strLine.contains("getText()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "getText");
          
                   		 			}
           		 			
           		 			writer.newLine();
                   		 		}
                   		 		
                   		 		else if (strLine.contains("By.name"))
                   		 		{
                   		 			String locator= "name";
                   		 			String locatorValue= strLine.substring(strLine.indexOf("By.name") + 6, strLine.indexOf(')') + 0);
                   		 			writer.write("locator= " + locator);
                   		 			writer.write(",");
                   		 			writer.write("locatorValue= " + locatorValue);
                   		 			writer.newLine();
                   		 			writer.write(",");
                   		 			if (strLine.contains("click()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "click");
                   		 			}
                   		 			else if (strLine.contains("sendKeys"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "sendKeys");
                   		 				writer.write(",");
                                           String actionValue= strLine.substring(strLine.indexOf(".sendKeys(") + 10, strLine.indexOf(");") + 0);
                       		 			writer.write("Action Value= " + actionValue);
                   		 			}
                   		 			else if (strLine.contains("selectByValue"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "selectByValue");
                   		 				writer.write(",");
                                           String actionValue= strLine.substring(strLine.indexOf(".selectByValue(") + 15, strLine.indexOf(");") + 0);
                       		 			writer.write("Action Value= " + actionValue);
                   		 			}
           		 			
                   		 			else if (strLine.contains("clear()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "clear");
                   		 			}
                   		 			else if (strLine.contains("getText()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "getText");
          
                   		 			}
           		 			
           		 			writer.newLine();
                   		 		}
                   		 		
                   		 		else if (strLine.contains("By.className"))
                   		 		{
                   		 			String locator= "className";
                   		 			String locatorValue= strLine.substring(strLine.indexOf("By.className") + 13, strLine.indexOf(')') + 0);
                   		 			writer.write("locator= " + locator);
                   		 			writer.write(",");
                   		 			writer.write("locatorValue= " + locatorValue);
                   		 			
                   		 			if (strLine.contains("click()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "click");
                   		 			}
                   		 			else if (strLine.contains("sendKeys"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "sendKeys");
                   		 				writer.write(",");
                                           String actionValue= strLine.substring(strLine.indexOf(".sendKeys(") + 10, strLine.indexOf(");") + 0);
                       		 			writer.write("Action Value= " + actionValue);
                   		 			}
                   		 			else if (strLine.contains("selectByValue"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "selectByValue");
                   		 				writer.write(",");
                                           String actionValue= strLine.substring(strLine.indexOf(".selectByValue(") + 15, strLine.indexOf(");") + 0);
                       		 			writer.write("Action Value= " + actionValue);
                   		 			}
           		 			
                   		 			else if (strLine.contains("clear()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "clear");
                   		 			}
                   		 			else if (strLine.contains("getText()"))
                   		 			{
                   		 				writer.write(",");
                   		 				writer.write("Action= " + "getText");
                   		 				if (strLine.contains("String"));
                   		 				{
                   		 					writer.write(",");
                                           String StringValue= strLine.substring(strLine.indexOf("String") + 7, strLine.indexOf("=") + 0);
                                           writer.write("Action Value= " + StringValue);
                   		 				}
                   		 			}
           		 			
           		 			writer.newLine();
                   		 		}
                   		 
                   			 }
                   	  if (strLine.contains("assert"))
                   	 {
                   		 if (strLine.contains( "assertEquals"))
                   		 {
                   			String assertType= "assertEquals";
                   		    writer.write("AssertType= " + assertType);
            		 			writer.write(",");
            		 			
                  			    String AssertValue= strLine.substring(strLine.indexOf("findElement") +15, strLine.indexOf(");") + 0);
                  		     	 String[] extractVal = AssertValue.split(",");
          		                String actualValue = extractVal[0]; 
          		                String expectedValue = extractVal[1];
          		             writer.write("Actual Value= " + actualValue);
         		 			writer.write(",");
         		 		 writer.write("Expected Value= " + expectedValue);
            		 			
                   		 }
                   		 else if (strLine.contains( "assertTrue"))
                   				 {
                   			 
                   			 String assertType= "assertTrue";
                   			 String Assertvalue= strLine.substring(strLine.indexOf("assertTrue") +11, strLine.indexOf(");") + 0);
                   			
                   			 writer.write("AssertType= " + assertType);
             		 			 writer.write(",");
             		 			 writer.write("AssertValue= " + Assertvalue);
                   				 }
        		 			writer.newLine();

                   	 }
                   	  
                   	  if (strLine.contains("Thread"))
                    	 {
                   		  String waitValue= strLine.substring(strLine.indexOf("Thread") +0, strLine.indexOf(")") + 1);
                   		  
          		 			 writer.write("wait value= " + waitValue);
         		 			writer.newLine();

                    	 }
                    }
                }
                
                finally {
               	    br.close();
               	}
                writer.close();
                
  }    
}
