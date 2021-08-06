# Model-based Test Case Repair Approach for Evolving Web Applications

Capture–Replay tools are widely used for the automated testing of web applications The scripts written
for these Capture–Replay tools are strongly coupled with the web elements of web applications.
These test scripts are sensitive to changes in web elements and require repairs as the web pages
evolve. In this paper, we propose an automated model-based approach to repair the Capture–Replay
test scripts that are broken due to such changes. Our approach repairs the test scripts that may be
broken due to the breakages (e.g., broken locators, missing web elements) reported in the existing
test breakage taxonomy. Our approach is based on a DOM-based strategy and is independent of the
underlying Capture–Replay tool. We developed a tool to demonstrate the applicability of the approach.
We perform an empirical study on seven subject applications. The results show that the approach
successfully repairs the broken test scripts while maintaining the same DOM coverage and fault-finding
capability. We also evaluate the usefulness of the repaired test scripts according to the opinion of
professional testers. We conduct an experiment to compare our approach with the state-of-the-art
DOM-based test repair approach, WATER. The comparison results show that our approach repairs more
test breakages than WATER.

# Paper Reference
Javaria Imtiaz, Muhammad Zohaib Iqbal, and Muhammad Uzair Khan. "An automated model-based approach to repair test suites of evolving web applications." Journal of Systems and Software 171 (2021): 110841.



<object data="http://yoursite.com/the.pdf" type="application/pdf" width="750px" height="750px">
    <embed src="http://yoursite.com/the.pdf" type="application/pdf">
        <p> Please download the PDF to view it: <a href="https://github.com/javariaimtiaz12/Model-basedWebTestRepairTool/blob/master/JSS_TestingMethodogyforEvolvingWebApplication.pdf">Download PDF</a>.</p>
    </embed>
</object>

# Basic Requirements

- Machine: minimum 4GB RAM and 4-core processor
- OS: Windows 8/10
- Java: JDK 1.8 or higher
  
 # Using Toolset
 
Step: 1
 
  - Clone the repository using the following command.
    git clone https://github.com/javariaimtiaz12/Model-basedWebTestRepairTool.git

Step: 2
 
   - Start the Eclipse and run 'DifferenceFinder.java' class to extract the differences from orinal and evolved web applications.
 
Step: 3

-  For testing platform independent representation of the test scripts, Run "ActivityTestModelCreator.java" file to develop test model corresponding for test cases and apply profile. For this purpose, this class will execute the following classes:
    -  "TestTokenizer" class to format the extracted elements present in MAP file
    -  "CRTPTestModelCreator.java" to apply stereotypes and display information on console
    
Step: 4 
 -  Execute DomDiff class to fix the test breakages
 
Step: 5
 -  "CRTP_Model_Transformer.java" to transform updated test models into test scripts of your choice.


