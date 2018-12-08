package com.example.api.utilities;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class DateTime {

	public static  String getCurrentDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	public static  String getCurrentDateTimeForID() {
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   String[] date= dtf.format(now).split(" ")[0].split("/");
		   String[] time= dtf.format(now).split(" ")[1].split(":");
		   String ID=date[0]+date[1]+date[2]+time[0]+time[1]+time[2];
		return ID;
	}

}
