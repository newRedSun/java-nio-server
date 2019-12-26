package com.qiao.nio;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author qiaojh
 */
public class HttpUtils {
    public static String postJsonContent(String url, String json) {
        String response = "";
        PostMethod postMethod = new PostMethod(url);
        HttpClient httpClient = new HttpClient();
        try {
            RequestEntity entity = new StringRequestEntity(json, "application/json", "UTF-8");
            postMethod.setRequestEntity(entity);
            System.out.println("请求路径：" + postMethod.getURI().toString());
            httpClient.executeMethod(postMethod);
            InputStream responseBodyAsStream = postMethod.getResponseBodyAsStream();
            StringBuilder buffer = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(responseBodyAsStream, StandardCharsets.UTF_8);
            char[] b = new char[4096];
            for (int n; (n = reader.read(b)) != -1; ) {
                buffer.append(new String(b, 0, n));
            }
            response = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return response;
    }

    public static void main(String[] args) {
        String url = "http://localhost:9999";
        String json = "{\"idNo\":\"310115199306026427\",\"idType\":\"0\",\"contDetailId\":\"CONID000000002714466\",\"proposalNo\":\"10021911130002718026\",\"cardCode\":\"20191113101245T6678G19055\",\"underwriteTime\":\"10:12:46\",\"applyNum\":\"1\",\"end_date\":\"2020-11-14 00:00:00\",\"isHolder\":\"1\",\"orderAmount\":\"1\",\"presentContNo\":\"8828902039120758\",\"name\":\"康佳丽\",\"elecSendMsg\":{\"policyURL\":\"/hd2-gh-elec-cont/pdf_out/2019/201911/20191113/8828902039120758_out.pdf\",\"policyPathType\":\"4\"},\"policyNo\":\"8828902039120758\",\"failReason\":\"订单：10021911130002718026承保成功\",\"payAccountId\":\"20191113101245T6678G19055\",\"start_date\":\"2019-11-14 00:00:00\",\"mobile\":\"13764941041\",\"issued_time\":\"2019-11-13 10:12:48\"}";
        String response = HttpUtils.postJsonContent(url, json);
        System.out.println(response);
    }
}
