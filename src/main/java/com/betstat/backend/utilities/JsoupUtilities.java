package com.betstat.backend.utilities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtilities {

	/**
	 * Prende in ingresso una stringa contenente un html e restituisce il Document
	 * 
	 * @param docElementString: stringa contenente html
	 * @return Document
	 */
	public static Document getDocumentElement(String docElementString) {
		return Jsoup.parse(docElementString);
	}

}
