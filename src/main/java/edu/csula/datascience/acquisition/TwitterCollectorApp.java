package edu.csula.datascience.acquisition;


import java.util.Collection;

import me.jhenrique.model.Tweet;

public class TwitterCollectorApp {
	public static void main(String[] args) {

		TwitterSource source = new TwitterSource();
		TwitterCollector collector = new TwitterCollector();

		while (source.hasNext()) {
			Collection<Tweet> tweets = source.next();
			Collection<Tweet> cleanedTweets = collector.mungee(tweets);
			collector.save(cleanedTweets);
		}
	}
}
