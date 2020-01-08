package com.betstat.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndBetStatApplication {

	/**
	 * Istanza di Log4j2
	 */
	
	
	
	final static Logger logger = LogManager.getLogger(BackEndBetStatApplication.class);

	private static final CloseableHttpClient httpClient = HttpClients.createDefault();

	public static void main(String[] args) throws ClientProtocolException, IOException {
		logger.info("Prova");
		logger.error("Prova");

		SpringApplication.run(BackEndBetStatApplication.class, args);
//		logger.info("Prova");
//		logger.error("Prova");

		HttpGet request = new HttpGet("https://play.newaleabet.net/print-share/1/PWHZP8-4AI-5857XK");
		try (CloseableHttpResponse response = httpClient.execute(request)) {

			// Get HttpResponse Status
			System.out.println(response.getStatusLine().toString());
		}

		URL urlObject = new URL("file:///C:/Users/nicol/Downloads/a.html");
		URLConnection urlConnection = urlObject.openConnection();
		urlConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

		//return toString(urlConnection.getInputStream());
		StringBuilder stringBuilder =  new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")))
        {
            String inputLine;
            //StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                if(inputLine.contains("Data coupon")) {
                	int n = inputLine.indexOf("data-value data");
                	String a = inputLine.substring(n+17,inputLine.length());
                	String[] b = a.split("<");
                	String c = b[0];
                }
            	//stringBuilder.append(inputLine);
            }

            //return stringBuilder.toString();
        }

		System.out.println(stringBuilder.toString());
		
	}

}
