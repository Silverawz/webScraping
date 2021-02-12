package com.deroussenicolas.batch;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;
import java.util.TimerTask;

import com.deroussenicolas.entities.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class MyTask extends TimerTask {

	private static final String baseUrl = "https://losangeles.craigslist.org/search/sss?query=iphone%208&sort=rel";
	private int incremental = 0;
	
	public MyTask() {
		super();
	}
	
	@Override
	public void run() {
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setUseInsecureSSL(true);	
		try {
			if(incremental == 0) {
				HtmlPage page = client.getPage(baseUrl);
				List<HtmlElement> items = page.getByXPath("//Li[@class='result-row']");
				if(items.isEmpty()) {
					System.err.println("items not found");
				}else {
					int increment=1;
					for (HtmlElement htmlElement : items) {
						HtmlAnchor itemAnchor = htmlElement.getFirstByXPath("/html/body/section/form/div[4]/ul/li["+increment+"]/div/h3/a");
						increment++;
						HtmlElement spanPrice = htmlElement.getFirstByXPath(".//a/span[@class='result-price']");					
						String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();
						Item item = new Item();
						item.setTitle(itemAnchor.asText());
						item.setUrl(itemAnchor.getHrefAttribute());
						item.setPrice(new BigDecimal(itemPrice.replace("$", "")));						
						ObjectMapper mapper = new ObjectMapper();
						String jsonString = mapper.writeValueAsString(item);
						System.out.println(jsonString);
						
					}
				}			  
				incremental++;
			}
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
		if(client != null) client.close();
	}
}
