package com.deroussenicolas.batch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.TimerTask;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class MyTask extends TimerTask {

	private static final String baseUrl = "https://deroussenicolas.fr/";
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
				List<HtmlElement> items = page.getByXPath("//*[@id=\"projects_title\"]");
				for (HtmlElement htmlElement : items) {
					
					System.out.println(htmlElement.getFirstByXPath("//*[@id=\"projects_title\"]/li[1]"));
				}
				//System.out.println(page.asXml());
				incremental++;
			}
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			client.close();
		}
	}
}
