package edu.csula.datascience.acquisition;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import me.jhenrique.model.Tweet;

public class TwitterMockData {
	private static List<Tweet> list = Lists.newArrayList();

	public static List<Tweet> getData() {
		

		for (int i = 0; i < 3; i++) {
			Tweet t = new Tweet();
			if (i != 2) {
				t.setText("jcndjs");
				t.setDate(new Date());
				t.setUsername("chbdfsc");
				t.setHashtags("#bcsdmhbcjsd");
				list.add(t);
				
			} else {

				t.setText("cbdjsb");
				t.setDate(null);
				t.setUsername(null);
				t.setHashtags("#bcsdmhbcjsd");
				list.add(t);
			}
		}

		return list;
	}
	
	public static List<Tweet> getExpectedData() {

		for (int i = 0; i < 2; i++) {
			Tweet t = new Tweet();
				t.setText("jcndjs");
				t.setDate(new Date());
				t.setUsername("chbdfsc");
				t.setHashtags("#bcsdmhbcjsd");
				list.add(t);
		}

		return list;
	}


}
