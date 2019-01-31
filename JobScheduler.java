import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class JobScheduler {
    /**
     * startScheduling method reads a trace file of name 
     * tracefilename, parses its content line by line. 
     * Each line represents data related to an arriving job,
     * Before scheduling any job (aka placing it in the list),
     * this method cleans the list and displays the number 
     * of removed jobs.
     * @param tracefilename
     */
    JobLList jobList = new JobLList();
    public void startScheduling(String tracefilename) {
        FileReader fr = null; // reference to a FileReader object
        BufferedReader br = null; // reference to a BufferedReader object
        try{
            // Open the file
            String fileFolder = "files";
            fr = new FileReader(fileFolder+ File.separatorChar + tracefilename);
            br = new BufferedReader(fr);
            String line = null;
            // Parse the content of the trace file line by line
            while( (line = br.readLine()) != null){
                // extract the data related to the arriving job
                String[] contents = line.split(" ");
                float arrivalTime = Float.parseFloat(contents[0]);
                int userId = Integer.parseInt(contents[1]);
                int priority = Integer.parseInt(contents[2]);
                int timeToLive = Integer.parseInt(contents[3]);
                StringBuilder str = new StringBuilder("");
                for(int i = 4; i < contents.length; i++){
                    str.append(contents[i]);
                    str.append(" ");
                }           
                String description = str.toString().trim();
                
                //create a new job instanceof JobNode class
                JobNode objJob = new JobNode(arrivalTime, userId, 
                        priority, timeToLive, description);             
                            
                System.out.println("Job Waiting List Status at " 
                        + objJob.getArrivalTime() + "s");
                System.out.println("*************************************");    
                    
                // Before adding the new job, let's remove the obsolete jobs 
                // in the list and display the number of cleaned jobs
                int cleanedJobCount = jobList.clean(arrivalTime);
                System.out.println(cleanedJobCount + 
                            " obsolete job(s) removed from the list.");
                
                // Schedule the created new job
                jobList.schedule(objJob); // add the new JobNode to the list
                                          // according to the scheduling policy
                System.out.println("Event: Job #" + objJob.getJobId() +
                                 " added to the list.");
                    
                // Print the content of the list
                System.out.println(jobList.toString());
            }
                
            br.close();
        }
        catch(IOException e){
            e.printStackTrace();
    }
        finally { // close open resources
            if (br != null)
                try {
                    br.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
        }
    }
    public JobLList getJobList() {
        return jobList;
    }
    public static void main(String args []) {
        // Display Welcome Message
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("        Job Scheduler version 1.0");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                
        // Create a JobScheduler object
        JobScheduler myJobScheduler = new JobScheduler();
         
        // Print the list
        System.out.println("Initial Job Waiting List Status");
        System.out.println("*******************************");
        System.out.println(myJobScheduler.getJobList().toString());
                
        // Parse the trace file and start scheduling simulation
        myJobScheduler.startScheduling("jobs.txt");
                
        // Duplicate List
        JobLList copyJobList = (JobLList)myJobScheduler.getJobList().duplicate();
                
        // Display Duplicated list content
        System.out.println("Duplicate List Status");
        System.out.println("*********************");
        System.out.println(copyJobList.toString());     
                
        // Clear the original list
        System.out.println("Clear Original List");
        System.out.println("*******************");
        myJobScheduler.getJobList().clear();
                
        // Display the content of the original list
        System.out.println(myJobScheduler.getJobList().toString());
                
        // END
        System.out.println(" -------- END Scheduling Simulation --------");
    }
}
