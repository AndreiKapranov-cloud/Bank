  package _BankingApp;
  import java.util.*;
  import java.util.concurrent.*;

import org.yaml.snakeyaml.Yaml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
  
  
  @Getter @Setter @NoArgsConstructor
  public class ScheduledThreadPoolExecutor {

	  static void executeSchedulingOnceAMonth(Connection conn) {
	
		  
		  final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		  Runnable task = new Runnable() {
		      @Override
		      public void run() {
		          ZonedDateTime now = ZonedDateTime.now();
		          long delay = now.until(now.plusMonths(1), ChronoUnit.MILLIS);

		          try {
		        	  //get the percents
		        		 InputStream inputStream = new FileInputStream(new File("./src/main/resources/conf.yml"));

			     		 Yaml yaml = new Yaml();
			     		 Map<Integer, Double> data = yaml.load(inputStream);
			     		
			     	    //add percents to database
			     		for (int key : data.keySet()) {
			     	      
			     		PreparedStatement updateStmt =
			     	             conn.prepareStatement("UPDATE account SET balance = balance + ? WHERE account_id = ? ");
			     	     updateStmt.setInt(1,data.get(key).intValue());
			     	     updateStmt.setInt(2, key);
			     	     updateStmt.executeUpdate();
			          }
			      
			     		//reset the percents
			      for (int key : data.keySet()) {
			    	  data.put(key, 0.0);  
			      }
			     		//put the empty map to configuration file
			     		 PrintWriter writer = new PrintWriter(new File("./src/main/resources/conf.yml"));
			     		
			     		 Yaml yaml1 = new Yaml();
			     		 yaml1.dump(data, writer);
			     		
		          } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					//schedule the next iteration
		              executor.schedule(this, delay, TimeUnit.MILLISECONDS);
		          }
		      }
		  };
		  
		  
		  int dayOfMonth = 28;

		  ZonedDateTime dateTime = ZonedDateTime.now();
		  if (dateTime.getDayOfMonth() >= dayOfMonth) {
		      dateTime = dateTime.plusMonths(1);
		  }
		  dateTime = dateTime.withDayOfMonth(dayOfMonth);
		  executor.schedule(task,
		      ZonedDateTime.now().until(dateTime, ChronoUnit.MILLIS),
		      TimeUnit.MILLISECONDS);
		  
		  
		  
	  }
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  // final ScheduledExecutorService executor1 = Executors.newScheduledThreadPool(1);
		
		 // ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		
		  
	//	  Runnable task = new Commando("task",conn);
		  
		 
		 
		//  ScheduledFuture<?> result1 = executor.scheduleAtFixedRate(task, 3000, 5000, TimeUnit.MILLISECONDS);
		  
		  
		
		  
//	  
//	  class Commando implements Runnable {
//		    String taskName;
//		    Connection conn;
//		    
//		    public Commando(String taskName,Connection conn)
//		    {
//		        this.taskName = taskName; 
//		        this.conn = conn; 
//		    }
//		    public void run()
//		    {
//		        try {
//		        	
//		        	
		        	
		        	
		        	
		        	
//		        	  ZonedDateTime now = ZonedDateTime.now();
//			          long delay = now.until(now.plusMonths(1), ChronoUnit.MILLIS);
//			          int dayOfMonth = 28;
//
//			    	  ZonedDateTime dateTime = ZonedDateTime.now();
//			    	  if (dateTime.getDayOfMonth() >= dayOfMonth) {
//			    	      dateTime = dateTime.plusMonths(1);
//			    	  }
//			    	  dateTime = dateTime.withDayOfMonth(dayOfMonth);
//			    	  executor1.schedule(task,
//			    	      ZonedDateTime.now().until(dateTime, ChronoUnit.MILLIS),
//			    	      TimeUnit.MILLISECONDS);
		        //  try {
			        	
//			     		 InputStream inputStream = new FileInputStream(new File("./src/main/resources/conf.yml"));
//
//			     		 Yaml yaml = new Yaml();
//			     		 Map<Integer, Double> data = yaml.load(inputStream);
//			     		
//			     	
//			     		 
//			     	
//			     		for (int key : data.keySet()) {
//			     	      
//			     		PreparedStatement updateStmt =
//			     	             conn.prepareStatement("UPDATE account SET balance = balance + ? WHERE account_id = ? ");
//			     	     updateStmt.setInt(1,data.get(key).intValue());
//			     	     updateStmt.setInt(2, key);
//			     	     updateStmt.executeUpdate();
//			          }
//			      
//			     		
//			      for (int key : data.keySet()) {
//			    	  data.put(key, 0.0);  
//			      }
//			     		
//			     		 PrintWriter writer = new PrintWriter(new File("./src/main/resources/conf.yml"));
//			     		
//			     		 Yaml yaml1 = new Yaml();
//			     		 yaml1.dump(data, writer);
//			     		
////					} catch (FileNotFoundException | SQLException e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////					
//			      
//		        }
//		        catch (Exception e) {
//		            e.printStackTrace();
//		        }
//		    }
//	  
//	  
//	  } 
//	  
//	  
//	  
	  
	  
	  
	  
	  
	  
	  
	 

//	  Runnable task = new Runnable() {
//	      @Override
//	      public void run() {
//	          ZonedDateTime now = ZonedDateTime.now();
//	          long delay = now.until(now.plusMonths(1), ChronoUnit.MILLIS);
//	          int dayOfMonth = 28;
//
//	    	  ZonedDateTime dateTime = ZonedDateTime.now();
//	    	  if (dateTime.getDayOfMonth() >= dayOfMonth) {
//	    	      dateTime = dateTime.plusMonths(1);
//	    	  }
//	    	  dateTime = dateTime.withDayOfMonth(dayOfMonth);
//	    	  executor1.schedule(task,
//	    	      ZonedDateTime.now().until(dateTime, ChronoUnit.MILLIS),
//	    	      TimeUnit.MILLISECONDS);
//        //  try {
//	        	
//	     		 InputStream inputStream = new FileInputStream(new File("./src/main/resources/conf.yml"));
//
//	     		 Yaml yaml = new Yaml();
//	     		 Map<Integer, Double> data = yaml.load(inputStream);
//	     		
//	     	
//	     		 
//	     	
//	     		for (int key : data.keySet()) {
//	     	      
//	     		PreparedStatement updateStmt =
//	     	             conn.prepareStatement("UPDATE account SET balance = balance + ? WHERE account_id = ? ");
//	     	     updateStmt.setInt(1,data.get(key).intValue());
//	     	     updateStmt.setInt(2, key);
//	     	     updateStmt.executeUpdate();
//	          }
//	      
//	     		
//	      for (int key : data.keySet()) {
//	    	  data.put(key, 0.0);  
//	      }
//	     		
//	     		 PrintWriter writer = new PrintWriter(new File("./src/main/resources/conf.yml"));
//	     		
//	     		 Yaml yaml1 = new Yaml();
//	     		 yaml1.dump(data, writer);
//	     		
////			} catch (FileNotFoundException | SQLException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			
////			}finally{
	             
//			 }
//	    }
//	
//	  }}

	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  

   
	  static void executeScheduling(Connection conn) throws InterruptedException{
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        // Creating two Runnable objects
        Runnable task1 = new Command("task1",conn);
        Runnable task2 = new Command("task2",conn);
        Runnable task3 = new Command("task3",conn);
        Runnable task4 = new Command("task4",conn);
        Runnable task5 = new Command("task5",conn);
        Runnable task6 = new Command("task6",conn);
        Runnable task7 = new Command("task7",conn);
        Runnable task8 = new Command("task8",conn);
        Runnable task9 = new Command("task9",conn);
        Runnable task10 = new Command("task10",conn);
      
    
        ScheduledFuture<?> result1 = executor.scheduleAtFixedRate(task1, 3000, 5000, TimeUnit.MILLISECONDS); 
        ScheduledFuture<?> result2 = executor.scheduleAtFixedRate(task2, 3500, 5000, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> result3 = executor.scheduleAtFixedRate(task3, 4000, 5000, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> result4 = executor.scheduleAtFixedRate(task4, 4500, 5000, TimeUnit.MILLISECONDS); 
        ScheduledFuture<?> result5 = executor.scheduleAtFixedRate(task5, 5000, 5000, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> result6 = executor.scheduleAtFixedRate(task6, 5500, 5000, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> resulе7 = executor.scheduleAtFixedRate(task7, 6000, 5000, TimeUnit.MILLISECONDS); 
        ScheduledFuture<?> result8 = executor.scheduleAtFixedRate(task8, 6500, 5000, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> result9 = executor.scheduleAtFixedRate(task9, 7000, 5000, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> result10 = executor.scheduleAtFixedRate(task10,7500,5000, TimeUnit.MILLISECONDS);

  
         }
	  }
  

class Command implements Runnable {
    String taskName;
    Connection conn;
    
    public Command(String taskName,Connection conn)
    {
        this.taskName = taskName; 
        this.conn = conn; 
    }
    public void run()
    {
        try {
//            System.out.println("Task name : "
//                               + this.taskName
//                               + " Current time : "
//                               + Calendar.getInstance().get(
//                                     Calendar.MILLISECOND));
           Account.processAllAccounts(conn);
            
    
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
