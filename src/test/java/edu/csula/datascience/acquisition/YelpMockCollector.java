package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class YelpMockCollector implements Collector<String, String> {

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
			}
		}

		return list;
	}

	@Override
	public void save(Collection<String> data) {

	}

}
