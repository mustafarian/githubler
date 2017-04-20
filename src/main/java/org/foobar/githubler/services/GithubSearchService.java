package org.foobar.githubler.services;

import org.apache.http.client.utils.URIBuilder;
import org.foobar.githubler.config.RestTemplateFactory;
import org.foobar.githubler.data.User;
import org.foobar.githubler.data.UserSearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 20/04/2017.
 */

@Service
public class GithubSearchService {

    static final private String SCHEME = "https";
    static final private String API_HOST = "api.github.com";
    static final private String USER_SEARCH_PATH = "/search/users";
    static final private String USER_PATH = "/users";

    @Autowired
    private RestTemplateFactory restTemplateFactory;

    public List<User> getUsers(String language, Integer pageSize, Integer page) {

        if (language == null || pageSize == null || page == null) {
            throw new IllegalArgumentException("Method arguments can't be null.");
        }

        ResponseEntity<UserSearchResults> results = doSearch(language, pageSize, page);

        RestTemplate restTemplate = restTemplateFactory.getObject();

        if (results.hasBody() && results.getBody().getLoginNameHolders().size() > 0) {
            List<User> users = new ArrayList<>();

            results.getBody().getLoginNameHolders().stream().forEach(loginNameHolder -> {

                ResponseEntity<User> userEntity = restTemplate
                        .getForEntity(getUserUrl(loginNameHolder.getLoginName()), User.class);

                if (userEntity.hasBody()) {
                    users.add(userEntity.getBody());
                }
            });

            return users;
        }

        return new ArrayList<>();
    }

    protected ResponseEntity<UserSearchResults> doSearch(String language, Integer pageSize, Integer page) {
        RestTemplate restTemplate = restTemplateFactory.getObject();
        return restTemplate.getForEntity(getSearchUrl(language, pageSize, page), UserSearchResults.class);
    }

    protected String getSearchUrl(String language, Integer pageSize, Integer page) {

        URIBuilder uriBuilder = new URIBuilder().setScheme(SCHEME).setHost(API_HOST).setPath(USER_SEARCH_PATH)
                .addParameter("page", page.toString()).addParameter("per_page", pageSize.toString());

        URI uri;

        try {
            uri = uriBuilder.build();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to build URI.");
        }

        return uri.toString() + "&q=language:" + language;
    }

    protected String getUserUrl(String login) {
        URIBuilder uriBuilder = new URIBuilder().setScheme(SCHEME).setHost(API_HOST).setPath(USER_PATH + "/" + login);
        URI uri;
        try {
            uri = uriBuilder.build();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to build URI.");
        }

        return uri.toString();
    }
}
