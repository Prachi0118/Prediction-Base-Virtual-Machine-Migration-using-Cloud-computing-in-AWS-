package logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;

import io.netty.handler.timeout.ReadTimeoutException;

public class LaunchInstance {


	private static String accessKey = "AKIAJPUGZP2CQJDDNGZA";
	private static String secretKey = "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";
	
	public static void main(String args[]) {
		
		new LaunchInstance().readTxt();
	
	}
	
	
	public void readTxt(){
		try {
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("WebContent/doc/output.txt"));
			
			String line ;
			
			while ((line = bufferedReader.readLine())!=null) {
				String data[] = line.split(" ");
				
				for (int i = 0; i < data.length; i++) {
				
					if(Integer.parseInt(data[i]) == 1){
						System.out.println(data[i]);
					}
					
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static HashMap<String, Float> sortByValue(Map<String, Float> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Float> > list = 
               new LinkedList<Map.Entry<String, Float> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Float> >() { 
            public int compare(Map.Entry<String, Float> o1,  
                               Map.Entry<String, Float> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String, Float> temp = new LinkedHashMap<String, Float>(); 
        for (Map.Entry<String, Float> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
	
	public static void launchInstance(){
		
		System.out.println("NEW VM LAUNCH IN US_WEST_1 (NORTH_CALIFORNIA).......... ");
		
		AmazonEC2Client amazonEC2Client = 
				new AmazonEC2Client(new BasicAWSCredentials(accessKey,secretKey))
				.withRegion(Regions.US_WEST_1);

		RunInstancesRequest runInstancesRequest = 
				   new RunInstancesRequest();

		runInstancesRequest.withImageId("ami-069339bea0125f50d")
				                   .withInstanceType("t2.micro")
				                   .withMinCount(1)
				                   .withMaxCount(1)
				                   .withKeyName("N_CALIFORNIA_PRACHI");
				
		RunInstancesResult result = amazonEC2Client.runInstances(
                        runInstancesRequest);
	}
	
	public static void stopInstance(String instanceId){
		
		System.out.println("STOP INSTANCE :: " + instanceId + "......................");
		
		BasicAWSCredentials awsCredentials =
				new BasicAWSCredentials(accessKey,secretKey);
		
		AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
				.withCredentials(new StaticCredentialsProvider(awsCredentials)).withRegion(Regions.US_EAST_1).build();
		
		StopInstancesRequest request = new StopInstancesRequest()
		    .withInstanceIds(instanceId);

		ec2.stopInstances(request);
	}
}
