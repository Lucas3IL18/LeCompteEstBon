package ressources;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeTools {
	
	private TimeTools() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static String convertTempsMS (int tmp) {
		int minutes = tmp/60;
		int secondes = tmp%60;
		return String.format("%02d:%02d", minutes, secondes);
	}
	
	public static String realTimeHMS () {
		LocalDateTime heure = LocalDateTime.now();
		return heure.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
}
