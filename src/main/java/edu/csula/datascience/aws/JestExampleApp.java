package edu.csula.datascience.aws;

import com.google.common.collect.Lists;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Collection;

/**
 * A quick example app to send data to elastic search on AWS
 */
public class JestExampleApp {
	public static void main(String[] args) throws URISyntaxException, IOException {
		String indexName = "project-data";
		String typeName = "business";
		String awsAddress = "https://search-shrey-fgg3zjvsc2zjcdomoqojsn6qum.us-west-2.es.amazonaws.com/";
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(awsAddress).multiThreaded(true).build());
		JestClient client = factory.getObject();
		File json = new File(ClassLoader.getSystemResource("business.json").toURI());

		try {
			InputStream stream = new FileInputStream(json);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			String line = reader.readLine();

			int count = 0;

			Collection<String> restaurants = Lists.newArrayList();

			while (line != null) {
				if (line.contains("\"type\": \"business\"")) {
					if (line.contains("Restaurants") && (line.contains("Restaurants")
							&& (line.contains("\"state\": \"AZ\"") || line.contains("\"state\": \"PA\"")
									|| line.contains("\"state\": \"WI\"") || line.contains("\"state\": \"NC\"")
									|| line.contains("\"state\": \"IL\"") || line.contains("\"state\": \"NV\"")))) {

						if (count < 500) {
							restaurants.add(line);
							count++;
						} else {
							try {
								Collection<BulkableAction> actions = Lists.newArrayList();
								restaurants.stream().forEach(res -> {
									actions.add(new Index.Builder(res).build());
								});
								Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName)
										.addAction(actions);
								client.execute(bulk.build());
								count = 0;
								restaurants = Lists.newArrayList();
								System.out.println("Inserted 500 documents to cloud");

							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				line = reader.readLine();
			}

			Collection<BulkableAction> actions = Lists.newArrayList();
			restaurants.stream().forEach(res -> {
				actions.add(new Index.Builder(res).build());
			});
			Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName).addAction(actions);
			client.execute(bulk.build());
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("We are done! Yay!");
	}
}
