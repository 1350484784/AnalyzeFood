package com.cs.analyzefood.config;

import com.cs.analyzefood.exception.SystemFailedException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(createHttpRequestFactory());
        return restTemplate;
    }

    private HttpComponentsClientHttpRequestFactory createHttpRequestFactory() {
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true).build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory)
                    .build();
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolingHttpClientConnectionManager.setMaxTotal(3000);
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100);
            CloseableHttpClient httpClient = httpClientBuilder
                    .setSSLContext(sslContext)
                    .evictExpiredConnections()
                    .setConnectionManager(poolingHttpClientConnectionManager)
                    .setRetryHandler(createRetryHandler())
                    .build();
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            clientHttpRequestFactory.setConnectTimeout(20000);
            clientHttpRequestFactory.setReadTimeout(35000);
            clientHttpRequestFactory.setConnectionRequestTimeout(20000);
            return clientHttpRequestFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new SystemFailedException("Unable to initialize http connection pool");
        }
    }

    private HttpRequestRetryHandler createRetryHandler() {
        return (exception, executionCount, httpContext) -> {
            if (executionCount >= 3) {
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                return false;
            }
            if (exception instanceof SSLHandshakeException) {
                return false;
            }
            if (exception instanceof SocketException) {
                return true;
            }
            return httpContext.getAttribute(ExecutionContext.HTTP_REQUEST) instanceof HttpEntityEnclosingRequest;
        };
    }

}
