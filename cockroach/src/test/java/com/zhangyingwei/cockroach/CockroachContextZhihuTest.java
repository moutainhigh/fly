package com.zhangyingwei.cockroach;

import com.zhangyingwei.cockroach.config.CockroachConfig;
import com.zhangyingwei.cockroach.executer.Task;
import com.zhangyingwei.cockroach.queue.TaskQueue;
import com.zhangyingwei.cockroach.http.client.okhttp.COkHttpClient;
import com.zhangyingwei.cockroach.store.ZhihuStore;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Created by zhangyw on 2017/8/10.
 */
public class CockroachContextZhihuTest {
    public static void main(String[] args) throws Exception {
        String cockie = "";
        CockroachConfig config = new CockroachConfig()
                .setAppName("haha")
                .setThread(1)
                .setHttpClient(COkHttpClient.class)
                .setCookie(cockie)
                .setAutoClose(true)
                .addHttpHeader("Host","www.zhihu.com")
                .addHttpHeader("Upgrade-Insecure-Requests","1")
                .addHttpHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .setStore(ZhihuStore.class);
        CockroachContext context = new CockroachContext(config);
        TaskQueue queue = TaskQueue.of();
//        queue.push(new Task("https://www.zhihu.com/people/wmhsr/activities"));
        queue.push(new Task("https://www.zhihu.com/api/v4/members/excited-vczh/followees?offset=0&limit=20"));
        context.start(queue);
    }
}