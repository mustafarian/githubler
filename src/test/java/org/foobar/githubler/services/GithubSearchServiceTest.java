package org.foobar.githubler.services;

import org.foobar.githubler.config.RestTemplateFactory;
import org.foobar.githubler.data.User;
import org.foobar.githubler.data.UserSearchResults;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * Created by mustafa on 20/04/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestPropertySource(locations="classpath:application.properties")
public class GithubSearchServiceTest {

    @Configuration
    static class GithubSearchServiceTestContextConfiguration {

        @Bean
        public GithubSearchService githubSearchService() {
            return new GithubSearchService();
        }

        @Bean
        public RestTemplateFactory restTemplateFactory() {
            RestTemplateFactory restTemplateFactory = new RestTemplateFactory();
            return restTemplateFactory;
        }
    }

    @Autowired
    protected GithubSearchService githubSearchService;

    @Test
    public void search() {
        ResponseEntity<UserSearchResults> results = githubSearchService.doSearch("java", 10, 0);
        Assert.assertTrue(results.hasBody());
        Assert.assertEquals(results.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(results.getBody().getLoginNameHolders().size(), 10);
    }

    @Test
    public void searchForBogusLanguage() {
        try {
            githubSearchService.doSearch("funckyclasses", 10, 0);
        } catch (HttpClientErrorException e) {
            return;
        }
        Assert.fail("Failed to catch expected exception.");
    }

    @Test
    public void getUsers() {
        List<User> users = githubSearchService.getUsers("java", 10, 0);
        Assert.assertEquals(10, users.size());
    }

    @Test
    public void getUsersIllegalArguments() {

        Integer expectedExceptions = 0;
        try {
            githubSearchService.getUsers(null, null, null);
        } catch (IllegalArgumentException e) {
            expectedExceptions++;
        }

        try {
            githubSearchService.getUsers("java", null, null);
        } catch (IllegalArgumentException e) {
            expectedExceptions++;
        }

        try {
            githubSearchService.getUsers("java", 10, null);
        } catch (IllegalArgumentException e) {
            expectedExceptions++;
        }

        Assert.assertEquals("Failed to catch expected Exception.", new Integer(3), expectedExceptions);
    }

    @Test
    public void getSearchUrl() {
        try {
            String userSearchUrl = githubSearchService.getSearchUrl("java", 10, 0);
            Assert.assertEquals("https://api.github.com/search/users?page=0&per_page=10&q=language:java",
                    userSearchUrl);
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }
    }

    @Test
    public void getUserUrl() {
        try {
            String userSearchUrl = githubSearchService.getUserUrl("mustafa");
            Assert.assertEquals("https://api.github.com/users/mustafa", userSearchUrl);
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }
    }

}
