package _BankingApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import javax.sql.DataSource;

public class Main extends TimerTask{
    public static void main(String[] args) throws Exception {
    	

    	
    	
    	Connection conn = DB.getConnection();
    	ScheduledThreadPoolExecutor.executeSchedulingOnceAMonth(conn);
    	ScheduledThreadPoolExecutor.executeScheduling(conn);
    	Account.showmenu(conn);
    	
    	
    }


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	   } 	
    	
    }

    	
    	
    	
    	
    	
    	
    	
    	
    	

