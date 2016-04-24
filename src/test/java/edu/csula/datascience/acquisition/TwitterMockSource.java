package edu.csula.datascience.acquisition;

import java.util.Collection;
import me.jhenrique.model.Tweet;

public class TwitterMockSource implements Source<Tweet> {
	int index = 0;

	@Override
	public boolean hasNext() {
		return index < 1;
	}

	@Override
	public Collection<Tweet> next() {
		return TwitterMockData.getData();
	}

}
