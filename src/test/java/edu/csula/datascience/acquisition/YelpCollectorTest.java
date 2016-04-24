package edu.csula.datascience.acquisition;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class YelpCollectorTest {
	private Collector<String, String> collector;
	private Source<String> source;

	@Before
	public void setup() {
		collector = new YelpMockCollector();
		source = new YelpMockSource();
	}

	@Test
	public void mungee() throws Exception {
		List<String> list = (List<String>) collector.mungee(source.next());
		List<String> expectedList = Lists.newArrayList(
				"\"id\": \"1\", \"state\": \"AZ\", \"categories\": \"Restaurants\"",
				"\"id\": \"2\", \"state\": \"NV\", \"categories\": \"Restaurants\"");

		Assert.assertEquals(list.size(), 2);

		for (int i = 0; i < 2; i++) {
			Assert.assertEquals(list.get(i), expectedList.get(i));
			Assert.assertEquals(list.get(i), expectedList.get(i));
		}
	}

}
