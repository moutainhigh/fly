package cn.jkm.test.api.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19.
 */
public class HttpClientUtil {

    final static Logger log = LoggerFactory.getLogger(HttpClientUtil.class.getName());
    public static final String default_charset = "UTF-8";

    private RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
//            .setRedirectsEnabled(false)//禁止重定向
            .build();

    private static HttpClientUtil instance = null;

    private HttpClientUtil() {
    }

    public static HttpClientUtil getInstance() {
        if (instance == null) {
            instance = new HttpClientUtil();
        }
        return instance;
    }


    public HttpResponse post(String httpUrl, String params, String charset) throws IOException {
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return execute(httpPost, charset);
    }


    public HttpResponse post(String httpUrl, Map<String, Object> maps) throws IOException {
        return post(httpUrl, maps, default_charset);
    }

    public HttpResponse post(String httpUrl, Map<String, Object> maps, String charset) throws IOException {
        HttpPost httpPost = new HttpPost(httpUrl);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        maps.forEach((key, v) -> nameValuePairs.add(new BasicNameValuePair(key, String.valueOf(v))));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return execute(httpPost, charset);
    }

    public HttpResponse get(String httpUrl, Map params) throws IOException {
        return get(httpUrl, params, default_charset);
    }

    public String httpUrlParams(String httpUrl, Map params) {
        final URIBuilder build = new URIBuilder();
        build.setPath(httpUrl);
        params.forEach((k, v) -> build.addParameter(String.valueOf(k), String.valueOf(v)));
        try {
            return build.build().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpResponse get(String httpUrl, Map params, String charset) throws IOException {
        final URIBuilder build = new URIBuilder();
        build.setPath(httpUrl);
        params.forEach((k, v) -> build.addParameter(String.valueOf(k), String.valueOf(v)));
        try {
            log.info("--get--" + build.build().toString());
            HttpGet httpGet = new HttpGet(build.build());// 创建get请求
            return execute(httpGet, charset);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpResponse get(String httpUrl) throws IOException {

        return get(httpUrl, default_charset);
    }

    public HttpResponse get(String httpUrl, String charset) throws IOException {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return execute(httpGet, charset);
    }

    private HttpResponse execute(HttpRequestBase httpRequestBase, String charset) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        HttpResponse responseContent = new HttpResponse();
        // Map<String, Object> responseContent = new HashMap<>();
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpRequestBase.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpRequestBase);
            entity = response.getEntity();
            responseContent.setBody(EntityUtils.toString(entity, charset)).setCode(response.getStatusLine().getStatusCode());
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }


}

