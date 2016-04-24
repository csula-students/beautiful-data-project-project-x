package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import me.jhenrique.manager.TweetManager;
import me.jhenrique.manager.TwitterCriteria;
import me.jhenrique.model.Tweet;
import java.util.Collection;
import java.util.List;

public class TwitterSource implements Source<Tweet> {
	private Boolean flag;
	private Mongo mongo;
	private DB db;
	private DBCursor curs;
	private String address;
	private String restaurant_name;
	private DBCollection table;

	@SuppressWarnings("deprecation")
	public TwitterSource() {
		this.mongo = new Mongo("localhost", 27017);
		this.db = mongo.getDB("bigdata");
		this.setFlag(true);
		this.address = null;
		this.restaurant_name = null;
		this.table = db.getCollection("business");
		this.curs = this.table.find();
	}

	@Override
	public boolean hasNext() {
		return flag;
	}

	@Override
	public Collection<Tweet> next() {
		List<Tweet> list = Lists.newArrayList();
		if (curs.hasNext()) {

			DBObject o = curs.next();

			this.address = (String) o.get("full_address");
			this.restaurant_name = (String) o.get("name");
			this.restaurant_name = restaurant_name.replaceAll(" ", "");

			String city = "";
			if (address.contains("PA")) {
				city = "#Pittsburgh";
			} else {
			}
			
			//source : https://github.com/Jefferson-Henrique/GetOldTweets-java
			TwitterCriteria criteria = TwitterCriteria.create().setQuerySearch("#" + this.restaurant_name + city)
					.setMaxTweets(50);
			try {
				list.addAll(TweetManager.getTweets(criteria));
			} catch (Exception e) {
				System.out.println("corrupted response from server");
			}
		} else {
			this.setFlag(false);
		}
		return list;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}
}
