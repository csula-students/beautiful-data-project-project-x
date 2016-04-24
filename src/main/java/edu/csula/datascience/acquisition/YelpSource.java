package edu.csula.datascience.acquisition;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Source implementation using Yelp Dataset
 * 
 */
public class YelpSource implements Source<String> {
	private String filename;
	private InputStream inputStream;
	private InputStreamReader inputStreamReader;
	private BufferedReader br;
	private Boolean flag;

	public YelpSource(String filename) throws IOException {
		this.setFilename(filename);
		this.setFlag(true);
		this.inputStream = new FileInputStream(filename);
		this.inputStreamReader = new InputStreamReader(inputStream);
		this.setBr(new BufferedReader(inputStreamReader));

	}

	@Override
	public boolean hasNext() {
		return flag;
	}

	@Override
	public Collection<String> next() {
		
		List<String> list = Lists.newArrayList();
		
		try {
			String l;
			if((l = br.readLine()) == null) {
				this.setFlag(false);
			} else {
				list.add(l);
		
		String line = null;

		try {
			long count = 0;
			
			while ((line = br.readLine()) != null && count < 1000) {
				
				//System.out.println(line);
				try {
				list.add(line);
				} catch(Exception e) {
					this.setFlag(true);
					break;
					
				}
				count++;
				//System.out.println(count);
			}
			System.out.println(">>>>>>>>>>>>" + "    " + count + "     <<<<<<<<<<<");
		} catch (IOException e) {
			System.out.println("Error raeding the yelp file");
		}
		}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return list;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InputStreamReader getInputStreamReader() {
		return inputStreamReader;
	}

	public void setInputStreamReader(InputStreamReader inputStreamReader) {
		this.inputStreamReader = inputStreamReader;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}
