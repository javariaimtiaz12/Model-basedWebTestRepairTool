# Model-based Test Case Repair Approach for Evolving Web Applications

This paper has proposed an automated model-based approach to repair the Capture–Replay test scripts that are broken due to such changes. This approach is based on a DOM-based strategy and is independent of the underlying Capture–Replay tool. A tool is developed to demonstrate the applicability of the approach. The tool consists of five major components, (i) Difference Detector (ii) Model Generator (iii) Test Classifier (iv) Repairer, and (v) Transformer

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

# Experiment Data

The repository contains the following material used in the experiment:
 -  It contains all the test scripts for the six subject application written in Selenium Framework. <a href="https://github.com/javariaimtiaz12/Selenium-Test-Scripts-for-Web">(Test Scripts)</a>
 -  It contains the Capture Replay Testing Profile. <a href="https://github.com/javariaimtiaz12/Capture-ReplayTestProfile">(UML CRTP)</a>
 -  It contains the complete working tool. <a href="https://github.com/javariaimtiaz12/Model-basedWebTestRepairTool">(Tool)</a>
