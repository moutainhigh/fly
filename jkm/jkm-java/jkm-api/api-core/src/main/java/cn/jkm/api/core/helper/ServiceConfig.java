package cn.jkm.api.core.helper;


/**
 * Created by werewolf on 2017/7/15.
 */
public class ServiceConfig {

    private String service;
    private Boolean request;
    private Boolean response;


    public ServiceConfig() {

    }

    public ServiceConfig(String service, Boolean request, Boolean response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Boolean getRequest() {
        return request;
    }

    public void setRequest(Boolean request) {
        this.request = request;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }



}
