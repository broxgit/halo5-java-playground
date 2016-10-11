
package com.broxhouse.h5api.models.stats.matchevents;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class StatsMatchDetails {

    @SerializedName("AuthorityId")
    @Expose
    private String authorityId;
    @SerializedName("Path")
    @Expose
    private String path;
    @SerializedName("QueryString")
    @Expose
    private Object queryString;
    @SerializedName("RetryPolicyId")
    @Expose
    private String retryPolicyId;
    @SerializedName("TopicName")
    @Expose
    private String topicName;
    @SerializedName("AcknowledgementTypeId")
    @Expose
    private Integer acknowledgementTypeId;
    @SerializedName("AuthenticationLifetimeExtensionSupported")
    @Expose
    private Boolean authenticationLifetimeExtensionSupported;

    /**
     * 
     * @return
     *     The authorityId
     */
    public String getAuthorityId() {
        return authorityId;
    }

    /**
     * 
     * @param authorityId
     *     The AuthorityId
     */
    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    /**
     * 
     * @return
     *     The path
     */
    public String getPath() {
        return path;
    }

    /**
     * 
     * @param path
     *     The Path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 
     * @return
     *     The queryString
     */
    public Object getQueryString() {
        return queryString;
    }

    /**
     * 
     * @param queryString
     *     The QueryString
     */
    public void setQueryString(Object queryString) {
        this.queryString = queryString;
    }

    /**
     * 
     * @return
     *     The retryPolicyId
     */
    public String getRetryPolicyId() {
        return retryPolicyId;
    }

    /**
     * 
     * @param retryPolicyId
     *     The RetryPolicyId
     */
    public void setRetryPolicyId(String retryPolicyId) {
        this.retryPolicyId = retryPolicyId;
    }

    /**
     * 
     * @return
     *     The topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * 
     * @param topicName
     *     The TopicName
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     * 
     * @return
     *     The acknowledgementTypeId
     */
    public Integer getAcknowledgementTypeId() {
        return acknowledgementTypeId;
    }

    /**
     * 
     * @param acknowledgementTypeId
     *     The AcknowledgementTypeId
     */
    public void setAcknowledgementTypeId(Integer acknowledgementTypeId) {
        this.acknowledgementTypeId = acknowledgementTypeId;
    }

    /**
     * 
     * @return
     *     The authenticationLifetimeExtensionSupported
     */
    public Boolean getAuthenticationLifetimeExtensionSupported() {
        return authenticationLifetimeExtensionSupported;
    }

    /**
     * 
     * @param authenticationLifetimeExtensionSupported
     *     The AuthenticationLifetimeExtensionSupported
     */
    public void setAuthenticationLifetimeExtensionSupported(Boolean authenticationLifetimeExtensionSupported) {
        this.authenticationLifetimeExtensionSupported = authenticationLifetimeExtensionSupported;
    }

}
