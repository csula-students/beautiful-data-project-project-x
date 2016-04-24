package edu.csula.datascience.acquisition;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.jhenrique.model.Tweet;

public class TwitterCollectorTest {
	private Collector<Tweet, Tweet> collector;
	private Source<Tweet> source;

	@Before
	public void setup() {
		collector = new TwitterMockCollector();
		source = new TwitterMockSource();
	}

	@Test
	public void mungee() throws Exception {
		List<Tweet> list = (List<Tweet>) collector.mungee(source.next());
		List<Tweet> expectedList = TwitterMockData.getExpectedData();

		Assert.assertEquals(list.size(), 2);

		for (int i = 0; i < 2; i++) {
			Assert.assertEquals(list.get(i).getText(), expectedList.get(i).getText());
			Assert.assertEquals(list.get(i).getUsername(), expectedList.get(i).getUsername());
		}
	}

}
