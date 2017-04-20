package org.foobar.githubler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Created by mustafa on 20/04/2017.
 */
@SpringBootApplication
public class Githubler {
    private static final Logger log = LoggerFactory.getLogger(Githubler.class);

    public static void main(String[] args) {
        SpringApplication.run(Githubler.class);
    }
}