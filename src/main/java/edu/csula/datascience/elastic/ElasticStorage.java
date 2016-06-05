package edu.csula.datascience.elastic;

import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.node.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class ElasticStorage {
	private final static String indexName = "project-data";
	private final static String typeName = "business";

	public static void main(String[] args) throws URISyntaxException, IOException {
		Node node = nodeBuilder()
				.settings(Settings.builder().put("cluster.name", "dhruvparmar").put("path.home", "elasticsearch-data"))
				.node();
		Client client = node.client();

		File json = new File(ClassLoader.getSystemResource("business.json").toURI());

		// create bulk processor
		BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				System.out.println("Facing error while importing data to elastic search");
				failure.printStackTrace();
			}
		})
				// .setBulkActions(10000)
				// .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
				.setFlushInterval(TimeValue.timeValueSeconds(5)).setConcurrentRequests(1)
				.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

		try {

			InputStream stream = new FileInputStream(json);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			String line = reader.readLine();
			while (line != null) {
				if (line.contains("\"type\": \"business\"")) {
					if (line.contains("Restaurants")
							&& (line.contains("\"state\": \"AZ\"") || line.contains("\"state\": \"PA\"")
									|| line.contains("\"state\": \"WI\"") || line.contains("\"state\": \"NC\"")
									|| line.contains("\"state\": \"IL\"") || line.contains("\"state\": \"NV\""))) {
						bulkProcessor.add(new IndexRequest(indexName, typeName).source(line));
					}
				} else {
					// line = line.replace("$", "");
					bulkProcessor.add(new IndexRequest(indexName, typeName).source(line));
				}
				line = reader.readLine();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
