package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class YelpCollector implements Collector<String, String> {

	static long count = 0;
	private Mongo mongo;
	private DB db;

	@SuppressWarnings("deprecation")
	public YelpCollector() {
		this.mongo = new Mongo("localhost", 27017);
		this.db = mongo.getDB("bigdata");
	}

	@Override
	public Collection<String> mungee(Collection<String> src) {
		List<String> list = Lists.newArrayList();

		for (Iterator<String> iterator = src.iterator(); iterator.hasNext();) {
			String line = (String) iterator.next();

			if (line.contains("\"type\": \"business\"")) {
				if (line.contains("Restaurants")
						&& (line.contains("\"state\": \"AZ\"") || line.contains("\"state\": \"PA\"")
								|| line.contains("\"state\": \"WI\"") || line.contains("\"state\": \"NC\"")
								|| line.contains("\"state\": \"IL\"") || line.contains("\"state\": \"NV\""))) {
					list.add(line);
					System.out.println(line);
				}
			} else {
				list.add(line);
			}
		}

		return list;
	}

	@Override
	public void save(Collection<String> data) {

		DBCollection collection = null;
		for (Iterator<String> iterator = data.iterator(); iterator.hasNext();) {
			String line = (String) iterator.next();

			if (line.contains("\"type\": \"business\"")) {
				collection = this.getDb().getCollection("business");
			} else if (line.contains("\"type\": \"tip\"")) {
				collection = this.getDb().getCollection("tip");
			} else if (line.contains("\"type\": \"review\"")) {
				collection = this.getDb().getCollection("review");
			} else if (line.contains("\"type\": \"user\"")) {
				collection = this.getDb().getCollection("user");
			} else if (line.contains("\"type\": \"checkin\"")) {
				collection = this.getDb().getCollection("checkin");
			} else {

			}
			// convert JSON to DBObject directly
			DBObject dbObject = (DBObject) JSON.parse(line);

			try {
				collection.insert(dbObject);
			} catch (Exception e) {
				System.out.println(">>>>>>>>>>No collection found to insert<<<<<<<<<<<");
			}

		}

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
