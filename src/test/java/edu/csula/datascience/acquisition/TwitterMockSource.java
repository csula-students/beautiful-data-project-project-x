package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import me.jhenrique.model.Tweet;

public class TwitterMockSource implements Source<Tweet> {
	int index = 0;

	@Override
	public boolean hasNext() {
		return index < 1;
	}

	@Override
	public Collection<Tweet> next() {
//		List<Tweet> list = Lists.newArrayList();
//
//		for (int i = 0; i < 3; i++) {
//			Tweet t = new Tweet();
//			if (i < 2) {
//				t.setText("jcndjs");
//				t.setDate(new Date());
//				t.setUsername("chbdfsc");
//				t.setHashtags("#bcsdmhbcjsd");
//			} else {
//
//				t.setText("cbdjsb");
//				t.setDate(null);
//				t.setUsername(null);
//				t.setHashtags("#bcsdmhbcjsd");
//			}
//		}

		return TwitterMockData.getData();
	}

}
