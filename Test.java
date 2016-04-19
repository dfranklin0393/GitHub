import java.net.*;
import java.io.*;
import org.json.*;
import java.net.URLConnection;
import java.util.Scanner;
import java.net.URL;
import javax.swing.*;
import java.util.HashMap;


public class Test {

	public static void main(String[] args)throws IOException, JSONException{//Beginning of class
		// TODO Auto-generated method stub
		String appID = "YWT4UP-Y9W7AREAHJ";
        String search = "ravenbird";
        
        URL wolframData = new URL("http://api.wolframalpha.com/v2/query?input="+search+"&appid="+appID);
        
        URLConnection connection = wolframData.openConnection();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //reads in the data
        
        StringBuilder str = new StringBuilder();
        //JsonObject object = Json.createObjectBuilder().build();
        
        String xmlDoc;
       
        while((xmlDoc = in.readLine()) != null){ //converts buffer reader to string
        str.append(xmlDoc);//Concatenates characters to string
        //System.out.println(xmlDoc);
        }
        in.close();
       
        //converts xml to Json
        JSONObject wolframJson = XML.toJSONObject(str.toString());
            
        
        //Creating a path through the json query
        JSONObject res =   wolframJson.getJSONObject("queryresult");
        JSONArray  pod =   res.getJSONArray("pod");
    

        //Information needed from json query
        
        String lifespan = "";
        String scientificName = "";
        //puts the values in an array associated with the taxonomy
        HashMap <String, String> taxonomy = new HashMap <String,String>();
        
        for(int i = 0; i < pod.length(); i++)//contains everything inside pod array
        {//beginning of pod array
        	
        	try{//catches any errors and prints out a message explaining 
        		//what the error is
        		
        	//searches to find the object containing Scientific name
        	if(pod.getJSONObject(i).getString("title").equals("Scientific name"))
        	{
        			    
        				JSONObject subpod = pod.getJSONObject(i).getJSONObject("subpod");
        				JSONObject img = subpod.getJSONObject("img");
        				
        				//Gets the scientific name
        				scientificName = img.getString("title");
        	}
        	//searches to find the object containing Taxonomy
        	if(pod.getJSONObject(i).getString("title").equals("Taxonomy"))
        	{
        			    
        				JSONObject subpod = pod.getJSONObject(i).getJSONObject("subpod");
        				JSONObject img = subpod.getJSONObject("img");
        				
        				//Gets the Taxonomy 
        				 String taxonomyString = img.getString("title"); //contains the full taxonomy string
        				 /*
        				  * Have to go through this process because of the output format of the taxonomy string 
        				  * does not give me the phyllum, class, order and family separately
        				  */
        				 //This code will break the string up into categories and put them into a hashmap to access them
        				 String[] taxonomyArray = new String[5];
        				 char letter = 0;  //holds current character from taxonomyString
        				 int stringIterator = 0;//iterates through the string
        				 int arrayCounter = 0;//iterates through array
        				 StringBuilder cycle = new StringBuilder(); //letters will be appended to this string
        				 //I am using a StringBuilder because it is changeable
        				 
        				 while(stringIterator < taxonomyString.length())
        					 letter = taxonomyString.charAt(stringIterator); //loads the current letter in TaxonomyString into letter to be compared
        				 {//Beginning of while loop
        					 if(!(letter == '|'))//since we are comparing characters we can use ==
        					 {
        						 cycle.append(taxonomyString.charAt(stringIterator));
        					 }
        					 if(letter == '|')
        					 {
        						 taxonomyArray[arrayCounter] = cycle.toString();//the appended letter will be added to the array list
        						 stringIterator = letter + 2; //moves two spaces in the string to skip "|" symbol
        						 arrayCounter ++;//array starts at phyllum then will iterate to class, then order and then family
        					 }
        				 }//End of while loop
        				 
        				 
        				 
        				 
        				 
       	 
        				
        	}
        	
        	
        	//searches through object 4
            if(pod.getJSONObject(i).getString("scanner").equals("Species"))
            {//Beginning of if
            	//for loop only beings if "if" statement is true
            	JSONArray subpod = pod.getJSONObject(i).getJSONArray("subpod");
       	
            	for(int ii = 0; ii < subpod.length(); i++)
            	{//Beginning of loop
            		lifespan = pod.getJSONObject(ii).getString("plaintext");
            		break;
            	}//End of loop
            }//end of if
            
        	}catch(JSONException je)
        	{
        		System.out.println(je.toString());
        	}
        	
        }//End of  pod array
       
        System.out.println(scientificName);
        int podNum = pod.length();//test for number of objects in pod
        System.out.println(podNum);//prints out number of objects in pod
              

	}//End of method
	

}//End of class
