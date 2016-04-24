package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import me.jhenrique.model.Tweet;

public class TwitterMockCollector implements Collector<Tweet, Tweet> {

	@Override
	public Collection<Tweet> mungee(Collection<Tweet> src) {
		List<Tweet> list = Lists.newArrayList();
		for (Iterator<Tweet> iterator = src.iterator(); iterator.hasNext();) {
			Tweet t = iterator.next();
			if (t.getText() != null && t.getUsername() != null && t.getHashtags() != null && t.getDate() != null) {
				list.add(t);
			}
		}

		return list;
	}

	@Override
	public void save(Collection<Tweet> data) {

	}

}
