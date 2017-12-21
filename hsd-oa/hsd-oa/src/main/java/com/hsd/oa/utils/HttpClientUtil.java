package com.hsd.oa.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.hsd.oa.Exception.BizException;


/**
 * 模拟Http客户端访问网络地址，支持get/post两种方式<br>
 * <p>
 * get
 * <p>
 * <pre>
 * HttpClientUtil h = new HttpClientUtil();
 * try {
 * 	h.open(&quot;http://news.sina.com.cn&quot;, &quot;get&quot;);
 * 	int status = h.send();
 * 	h.getResponseBodyAsString(&quot;GBK&quot;);
 * }
 * catch (IOException e) {
 * 	e.printStackTrace();
 * }
 * finally {
 * 	h.close();
 * }
 *
 * HttpClientUtil h = new HttpClientUtil();
 * try {
 * 	h.open(&quot;http://oa.com/something.do&quot;, &quot;post&quot;);
 *
 * 	h.addParameter("name", "ta");
 * 	h.addParameter("password", "123456");
 *
 * 	h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
 *
 * 	int status = h.send();
 * 	h.getResponseBodyAsString(&quot;GBK&quot;);
 * }
 * catch (IOException e) {
 * 	e.printStackTrace();
 * }
 * finally {
 * 	h.close();
 * }
 * </pre>
 */
public class HttpClientUtil {
    private static final Log log = LogFactory.getLog(HttpClientUtil.class);

    private HttpClient client = null;
    private HttpMethodBase httpMethod = null;

    public HttpClientUtil() {
        client = new HttpClient();

        setProxy(client);
    }

    /**
     * @param timeoutInMilliseconds 超时时间，单位毫秒
     */
    public HttpClientUtil(int timeoutInMilliseconds) {
        this();
        HttpConnectionManagerParams ps = client.getHttpConnectionManager().getParams();

        ps.setSoTimeout(timeoutInMilliseconds);
        ps.setConnectionTimeout(timeoutInMilliseconds);
    }

    /**
     * 向指定地址发送一个HTTP请求
     *
     * @param url    绝对地址，如http://a8.seeyon.com/seeyon/logs/v3x.log
     * @param method 方式 : get post
     */
    public void open(String url, String method) {
        if ("get".equalsIgnoreCase(method)) {
            httpMethod = new GetMethod(url);
        } else if ("post".equalsIgnoreCase(method)) {
            httpMethod = new PostMethod(url);
        } else {
            throw new IllegalArgumentException("Unsupport method : " + method);
        }
    }

    /**
     * 在open方法之后、send()之前设置
     *
     * @param name
     * @param value
     */
    public void setRequestHeader(String name, String value) {
        httpMethod.setRequestHeader(name, value);
    }

    /**
     * 添加参数
     *
     * @param name
     * @param value
     * @throws IllegalArgumentException
     */
    public void addParameter(String name, String value) throws IllegalArgumentException {
        if ((name == null) || (value == null)) {
            throw new IllegalArgumentException(
                    "Arguments to addParameter(String, String) cannot be null");
        }

        if (httpMethod instanceof GetMethod) {
            String q = httpMethod.getQueryString();
            if (q == null) {
                httpMethod.setQueryString(name + "=" + value);
            } else {
                httpMethod.setQueryString(q + "&" + name + "=" + value);
            }
        } else if (httpMethod instanceof PostMethod) {
            ((PostMethod) httpMethod).addParameter(name, String.valueOf(value));
        }
    }

    /**
     * 发送Get请求
     *
     * @return 请求状态 200-正常，404-页面不存在，403-禁止访问，500-服务器错误等等
     * @throws IOException
     */
    public int send() throws IOException {
        httpMethod.setRequestHeader("Connection", "close");

        return client.executeMethod(httpMethod);
    }

    /**
     * 获取response header
     *
     * @return
     */
    public Map<String, String> getResponseHeader() {
        Map<String, String> r = new HashMap<String, String>();
        Header[] h = httpMethod.getResponseHeaders();
        for (Header header : h) {
            r.put(header.getName(), header.getValue());
        }

        return r;
    }

    /**
     * 获取Cookie
     *
     * @return
     */
    public Map<String, String> getCookies() {
        Map<String, String> r = new HashMap<String, String>();
        Cookie[] cs = client.getState().getCookies();
        for (Cookie c : cs) {
            r.put(c.getName(), c.getValue());
        }

        return r;
    }

    /**
     * Returns the response body of the HTTP method
     *
     * @return
     * @throws IOException
     */
    public InputStream getResponseBodyAsStream() throws IOException {
        return httpMethod.getResponseBodyAsStream();
    }

    /**
     * 获取网页内容
     *
     * @param contentCharset
     * @return
     * @throws IOException
     */
    public String getResponseBodyAsString(String contentCharset) throws IOException {
        InputStream instream = httpMethod.getResponseBodyAsStream();
        ByteArrayOutputStream outstream = new ByteArrayOutputStream(4096);
        byte[] buffer = new byte[4096];
        int len;
        while ((len = instream.read(buffer)) > 0) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();

        byte[] rawdata = outstream.toByteArray();

        if (contentCharset != null) {
            return new String(rawdata, contentCharset);
        } else {
            return new String(rawdata);
        }
    }

    /**
     * Releases the connection being used by this HTTP method.<br>
     * 释放连接，千万不要忘记。
     */
    public void close() {
        if (httpMethod != null) {
            try {
                httpMethod.releaseConnection();
            } catch (Exception e) {
                System.out.println(e);
                // ignore exception
            }
        }
    }

    private static void setProxy(HttpClient client) {
        /*String proxyHost = SystemEnvironment.getHttpProxyHost();
        if(Strings.isNotBlank(proxyHost)){
			int proxyPort = SystemEnvironment.getHttpProxyPort();
			
			if(proxyPort > 0){
				client.getHostConfiguration().setProxy(proxyHost, proxyPort);
			}
		}*/
    }

    /**
     * 简易快捷的方法，直接获得页面的内容
     *
     * @param url 需要访问的地址
     * @return
     */
    public static String getContent(String url) {
        if (url != null) {
            HttpClient client = new HttpClient();
            GetMethod get = new GetMethod(url);

            setProxy(client);

            get.setRequestHeader("Connection", "close");

            try {
                client.executeMethod(get);
                return get.getResponseBodyAsString();
            } catch (Exception e) {
                log.error("", e);
            } finally {
                get.releaseConnection();
            }
        }

        return null;
    }
    
   /* public static InputStream InputStream(String filename,String media_id){
    	  HttpClientUtil h = new HttpClientUtil();
    	  InputStream inputstrem = null;
    	  try {
			h.open("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+SignUtil.getkoken()+"&media_id="+media_id, "get");
			  h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			   int status = h.send();
			    inputstrem =   h.getResponseBodyAsStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	  return inputstrem;
    }*/
    
  /*  public String postcsatvote(Integer gameid, Integer userid, int num,String type){
    	  HttpClientUtil h = new HttpClientUtil();
          try {
              h.open("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=OYI3RiYJ1HoPLg87Qp88vDMUXsaYwO8LxrI4EKUI5xmwV3XK55K5hVYYwb3ia2tW7wnSxNB5j7hw41baGtDDzBeyXCOw0ZgXenacXNtSeTJzCPGzUTj1tSIUjfo22gURRAMbAFACLX&media_id=gvJofKwH_unzhNWUUQTOleoqtOoaW4eWjE836ClxXCpzTGMg_BRxygYpdccmOTCy", "get");
              h.addParameter("gameid", gameid);
              h.addParameter("userid", userid);
              h.addParameter("num", value);
              h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
              
              int status = h.send();
              String context = h.getResponseBodyAsString("utf-8");
              File storeFile = new File("d:/2008sohu.gif");  
              InputStream inputstrem =   h.getResponseBodyAsStream();
               
              System.out.println(context);
          } catch (Exception e) {
              e.printStackTrace();
          } finally {
              h.close();
          }
    	
    	
    	return null;
    }*/
    
    public void addentity(HttpEntity entity){
    	
    /*	httpMethod.a*/
    }
    
    public static String getfileurl(String fileid){
    	 String context =null;
    	  try {
			HttpClientUtil h = new HttpClientUtil();
			  h.open("http://58.42.252.155:5433/attachement/get_url/?file_id="+fileid, "get");
			  h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			  int status = h.send();
			   context = h.getResponseBodyAsString("utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		//	throw  BizException.HTTP_YICHANG;
		}
    	  
    	return context;
    }
    
    
    
    
    

    public static void main(String[] args) {
        HttpClientUtil h = new HttpClientUtil();
        try {
            h.open("http://58.42.252.155:5433/android/sf_sms/", "post");
           /*  h.addParameter("memberid", "1");
             h.addParameter("orderno", "ORDd3352d59bd9a23a59633eb6f636eaa32");
			 h.addParameter("ORDd3352d59bd9a23a59633eb6f636eaa32", "1");
			 h.addParameter("channel", "alipay_wap");
			 h.addParameter("subject","%E5%B8%AE%E5%B8%AE%E4%B9%90%E6%8A%95%E7%A5%A8");
			 h.addParameter("successurl","www.leyou8.net");
			 h.addParameter("amount", "1");*/
            
            List formParams = new ArrayList();  
            
            formParams.add(new BasicNameValuePair("telephone", "18683697583"));  
            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");  
            
           // h.addParameter("telephone", "18683697583");
            h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
            
            int status = h.send();
            String context = h.getResponseBodyAsString("utf-8");
             
            System.out.println(context);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            h.close();
        }
    }
    
    public static byte[] readStream(InputStream inStream) throws Exception {  
        int count = 0;  
        while (count == 0) {  
            count = inStream.available();  
        }  
        byte[] b = new byte[count];  
        inStream.read(b);  
        return b;  
    }  

}
