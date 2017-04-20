package org.foobar.githubler.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mustafa on 20/04/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginNameHolder {

    @JsonProperty("login")
    protected String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
