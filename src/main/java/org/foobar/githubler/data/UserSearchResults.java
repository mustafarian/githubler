package org.foobar.githubler.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by mustafa on 20/04/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSearchResults {

    @JsonProperty("total_count")
    protected Integer totalCount;

    @JsonProperty("incomplete_results")
    protected Boolean incompleteResults;

    @JsonProperty("items")
    protected List<LoginNameHolder> loginNameHolders;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<LoginNameHolder> getLoginNameHolders() {
        return loginNameHolders;
    }

    public void setLoginNameHolders(List<LoginNameHolder> loginNameHolders) {
        this.loginNameHolders = loginNameHolders;
    }
}
