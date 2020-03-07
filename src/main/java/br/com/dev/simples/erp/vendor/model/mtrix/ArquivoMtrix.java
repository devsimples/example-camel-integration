package br.com.dev.simples.erp.vendor.model.vendor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArquivoVendor {

	private final Logger log = LoggerFactory.getLogger(ArquivoVendor.class);
	
	private String nomeArquivo;
	private StringBuilder header;
	private StringBuilder body;
	
	public ArquivoVendor() {
		this.header = new StringBuilder(2000);
		this.body = new StringBuilder(2000);
	}
	
	public ArquivoVendor addHeader(final int tamanho, final String valor) {
		header.append(padWithSpaces(tamanho, valor));
		return this;
	}
	
	public ArquivoVendor addBody(final int tamanho, final String valor) {
		body.append(padWithSpaces(tamanho, valor));
		return this;
	}
	
	public ArquivoVendor addBody(final int tamanho, final Integer valor) {
		body.append(padWithSpaces(tamanho, (valor == null) ? "" : valor.toString()));
		
		return this;
	}
	
	private String padWithSpaces(final int tamanho, String valor) {
	    if (valor != null) {
            valor = valor.trim();
            if(valor.length() > tamanho) {
            	String warn = String.format("Tamanho esperado: %s, recebido:%s, valor:%s", tamanho, valor.length(), valor);
            	log.warn(warn);
            	
                valor = valor.substring(0, tamanho);
            }
        } else {
	    	valor = "";
		}
		
		return StringUtils.rightPad(valor, tamanho, "");
	}
	
	public ArquivoVendor newLine() {
		body.append("\n");
		return this;
	}
	
	public String getHeader() {
		return header.toString();
	}
	
	public String getBody() {
		return body.toString();
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String arquivo, String fornecedor, String data) {
		this.nomeArquivo = arquivo + fornecedor + data + ".txt";
	}
	
	@Override
	public String toString() {
		return getHeader() + getBody();
	}
	
}
