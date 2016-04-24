package edu.csula.datascience.acquisition;

import twitter4j.Status;

import java.util.Collection;

import me.jhenrique.manager.TweetManager;
import me.jhenrique.manager.TwitterCriteria;
import me.jhenrique.model.Tweet;

/**
 * A simple example of using Twitter
 */
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
