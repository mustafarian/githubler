package org.foobar.githubler.config;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by mustafa on 20/04/2017.
 */
@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {

    @Value("${git.login}")
    private String gitUsername;

    @Value("${git.oauth.token}")
    private String gitOAuthToken;

    private RestTemplate restTemplate;

    public RestTemplate getObject() {
        return restTemplate;
    }

    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() {
        HttpHost host = new HttpHost("api.github.com", 443, "https");
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactoryBasicAuth(host));
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(gitUsername, gitOAuthToken));
    }
}
