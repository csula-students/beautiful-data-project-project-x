package edu.csula.datascience.acquisition;

import java.util.Collection;

import com.google.common.collect.Lists;

public class YelpMockSource implements Source<String> {
	int index = 0;

	@Override
	public boolean hasNext() {
		return index < 1;
	}

	@Override
	public Collection<String> next() {
		return Lists.newArrayList(
				"\"id\": \"1\", \"state\": \"AZ\", \"categories\": \"Restaurants\", \"type\": \"business\"",
				"\"id\": \"2\", \"state\": \"NV\", \"categories\": \"Restaurants\", \"type\": \"business\"",
				"\"id\": \"3\", \"state\": \"PA\", \"categories\": \"Something else\""
				);
	}
}
