package com.example;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ListImageProcessor implements Processor {

	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Value("${imagepath}")
	private String imagepath;

	static String hostname;

	// ...
	static public String getHostName() {
		if (StringUtils.isEmpty(hostname)) {
			try {
				hostname = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hostname;

	}

	@Override
	public void process(Exchange exchange) throws Exception {

		StringBuffer sb = new StringBuffer();
		listFile(imagepath)
				.forEach(
						filename -> sb
								.append("<a href=\"http://"+getHostName()+":10000/camel-rest/image/"
										+ filename
										+ "\">"
										+ filename
										+ "</a><br>"));
		exchange.getIn().setBody(sb.toString());
		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.TEXT_HTML);
	}

	public List<String> listFile(String dir) {
		List<String> a = new ArrayList<String>();
		Collection<File> files = FileUtils.listFiles(FileUtils.getFile(dir),
				TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		files.forEach(p -> a.add(p.getName()));
		return a;
	}

}
