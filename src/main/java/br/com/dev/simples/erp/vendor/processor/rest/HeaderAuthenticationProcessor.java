package br.com.dev.simples.erp.vendor.processor.rest;

import java.util.Base64;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HeaderAuthenticationProcessor implements Processor{

	@Value("${erp.rest.username}")
	private String username;
	
	@Value("${erp.rest.password}")
	private String password;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		in.setHeader("Authorization", "Basic " + getBinaryEncodeUsrPass(username, password));
	}
	
	private String getBinaryEncodeUsrPass(String username, String password) {
		final String credentials = username + ":" + password;
		return Base64.getEncoder().encodeToString(credentials.getBytes());
	}
	
}
