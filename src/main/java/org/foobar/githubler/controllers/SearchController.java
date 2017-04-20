package org.foobar.githubler.controllers;

import org.foobar.githubler.data.User;
import org.foobar.githubler.services.GithubSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by mustafa on 20/04/2017.
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    protected GithubSearchService githubSearchService;

    @Value("${search.page.size}")
    protected Integer defaultPageSize;

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<Collection<User>> search(@RequestParam(name = "lang") String lang,
                                            @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                            @RequestParam(name = "page", required = false) Integer page) {
        if (pageSize == null) {
            pageSize = defaultPageSize;
        }

        if (page == null) {
            page = 0;
        }

        try {
            return ResponseEntity.ok().body(githubSearchService.getUsers(lang, pageSize, page));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
