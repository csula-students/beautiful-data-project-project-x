package edu.csula.datascience.acquisition;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class YelpCollectorApp {

	public static void main(String[] args) {

		String[] files = { "business.json", "review.json", "tip.json", "user.json", "checkin.json" };

		//String[] files = { "business.json"};
		
		for (int i = 0; i < files.length; i++) {

			String filename = files[i];
			// initialize source with a filename
			YelpSource source = null;
			try {
				source = new YelpSource("/Users/dhruvparmar91/desktop/" + filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// initialize collector
			YelpCollector collector = new YelpCollector();

			while (source.hasNext()) {
				System.out.println("1");
				// get the next records
				Collection<String> data = source.next();
				System.out.println("2");
				// get the clean data through collector
				Collection<String> cleanedData = collector.mungee(data);
				System.out.println("3");
				// save the clean data
				collector.save(cleanedData);
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>");

			}
		}
	}
}
