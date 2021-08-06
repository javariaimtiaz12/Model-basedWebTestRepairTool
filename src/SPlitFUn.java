import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SPlitFUn 
{
		
		
		
			public static void splitTest() throws IOException {

			 BufferedReader br = new BufferedReader(new FileReader("TestCases/TestInfo.txt"));  //Extract information from 
             String line="";
             
	          while ((line = br.readLine()) != null)
	          {

				       
				        if (line.contains("assert"))
				        {
				        	String[] aux= line.split(",");
				            System.out.println(aux[0].trim());

				        }
					        

				           
			  }
	          
	          line = br.readLine();
	          br.close();

         }
}