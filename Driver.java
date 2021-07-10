
// Title: Driver class
// Author: Doruk Arslan
// Description: This class uses minimum priority queue that we implemented  in order to organize and run given tasks in the input file.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
public class Driver {

	public static void main(String[] args) {
		
		
		//In order to keep elements of schedule as objects we create schedule class 
		class schedule{
			String type;
			int deadline;
			int duration;
			
			public schedule(String type, int deadline , int duration) {
				this.type = type;
				this.deadline = deadline;
				this.duration = duration;
			}
		}
		
		
		// To keep the elements read from the file in an array list.
		// Since we do not know the number of task we prefer to use Arraylist insted of an array
		ArrayList<schedule> arr = new ArrayList<schedule>();
		
		
		
		// to keep data of number of lines for schedule and run in order to control our loop
		int capacity = 0;
		int loopCount = 0;
		
		try {
			 
			 // Path of the file 
			File file = new File("/Users/doruk/Desktop/Academic Projects/sampleinput1.txt");
		    // to scan  file
			Scanner key = new Scanner(file);
		    
			
			
		    while (key.hasNextLine()) {
		    	
		    	
		        String data = key.nextLine();
		       
		        String temp [] = data.split(" ");
		        
		        switch(temp[0]) {
		        
		        
		        	case "schedule":
		        		// we are creating a schedule object with the line we read from the file.
		        		capacity ++;
		        		loopCount ++;
		        		String type = temp[1];
		        		int deadline = Integer.parseInt(temp[2]);
		        		int duration = Integer.parseInt(temp[3]);
		        		schedule s = new schedule(type,deadline,duration);
		        		// then we are adding it to our list
		        		arr.add(s);
		        		break;
		        		
		        		
		        	
		        		
		       
		        	
		        	case "run":
		        		// we are also creating schedule object from run commands as but this time we are setting its type at run in order to distinguish.
		        		
		        		loopCount ++;
		        		String command = temp[0];
		        		int deadl = Integer.parseInt(temp[1]);
		        		int durat = 0;
		        		schedule r = new schedule(command,deadl,durat);
		        		// Then we are adding it to arraylist.
		        		arr.add(r);
		        		break;
		        }
		        
		        
		      }
		    
		      key.close();
		      
		     
		 }
		 
		 catch (FileNotFoundException e){
			
			 System.out.println("File not found");
			 
		 }
		
		// Sınce we are considering deadline as key, we are cretting a min priort queue type of Integer
		
		PQ<Integer> pq  = new PQ<Integer>(capacity);
		
		
		
		// to keep data of total time with respect to the tasks 
		long time = 0;
		
		int index = 0;
		
		
		// to initilaze it 
		
		schedule current = arr.get(index);
		
		for(int i=0;i< loopCount ; i++){
			
			// To check if the object is schedule command 
			if(!current.type.equals("run") && !current.type.equals("null")) {
			
				
				// We are inserting it into our min priorty queue 
				pq.insert(current.deadline);
				
				// To print intended data 
				System.out.println(time+": adding "+current.type+" with deadline "+current.deadline+" and duration "+current.duration);
				index ++;
				// To get next element in the list in order to add it to priorty queue and continue the same operation.
				current = arr.get(index);			
			}
			else {
				
				if(current.type.equals("run")) {
					
					
					
					// If the current object is a run command we first keep its data in a variable name duration
					int dur = current.deadline;
					
					
					// to compare the time spent on the tasks in order to compare it with variable duration(dur)
					int totalDuration = 0;
					
					
					
					
					for(int j = 0; j<capacity; j++) {
						
						
						// Since command is run we are gettin elements from one by one from queue
						
						
						int currentMin = (int) pq.peekMin();
						
						schedule temp;
						// To find first object of the queue we create a temp schedule object 
						
						
						
						int indextoDelete = 0;
						int counter = 0;
						for(schedule s : arr) {
							
							// With this loop we are trying to find accurate object which have the deadline equal to currentMin variable
							// Means that it is the first element of the queue 
							
							
							if(s.deadline == currentMin && !s.type.equals("run") ) {
								temp = s;
								indextoDelete = counter;
								
								
								// To check if we will pass the total duration time after performing the schedule with whole duration.
								if(dur - totalDuration - temp.duration >= 0) {
									
									// To print intended information message
									System.out.println(time+": busy with "+ temp.type+" with deadline "+temp.deadline+" and duration "+ temp.duration);
									
									
									// Add the duration of the schedule that ran to our total time 
									totalDuration += temp.duration;
									
								
									// Since we finished the task we are removing it from the queue 
									pq.delMin();
									
									// We are also removing it from the arraylist since there is no more need
									arr.remove(temp);
									
									time = totalDuration;
									
									
									// Again, to print intended information message to user 
									System.out.println(time+ ": done with "+ temp.type);
									break;
									
									
									
								}
								else {
									
									if(!s.type.equals("run")) {
										
										// If we pass the duration limit  when we perform the given task,
										// We will perform it as long as possible depending on duration,
										// Then we will remove it and insert new version of it with same deadline and tipe but with remained duration. 
										System.out.println(time+": busy with"+ temp.type+" with deadline "+temp.deadline+" and duration "+ temp.duration);
										
										
										
										// To find longest period we can execute the operation.
										int available = dur-totalDuration;
										
										
										// then we are keeping remained time in variable difference in order to ceeare new object
										int diffirence = temp.duration-available;
										
										
										 
										totalDuration = dur;
										
										// to  set total duration to time
										time = totalDuration;
										
										// to delete used task from the queue 
										pq.delMin();
										
									
										// also we are removing it from arraylist since no more needed 
										
										arr.remove(temp);
										
										
										// With remaining time we are creating a new object with chaning duration in order to insert it to queue again
										schedule recent = new schedule(temp.type,temp.deadline, diffirence);
										
										
										// to add it to our arraylist 
										arr.add(recent);
										
										
										// Lastly, we are adding newset version of it with inserting into queue 
										pq.insert(recent.deadline);
										
										
										//We are priting intended information message 
										System.out.println(time+" adding "+recent.type+" with deadline "+ recent.deadline+ " and duration " + recent.duration);
										break;
										
									}
									
							
									
									
									
									
									
									
									
								}
									
								
							}
							
							
							
						
						}
						
						
							
							
							
							
							
							
							
							
						
						
						
						
					}
					
				}
				else {
					continue;
				}
				
				
				
				
			}
			
			
			
			
		}
		
		
		

	}
	 

}
