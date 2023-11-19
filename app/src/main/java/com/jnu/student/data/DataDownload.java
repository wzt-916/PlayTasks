package com.jnu.student.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataDownload{

    public String download(String url_) {
        try {
            // 创建URL对象
            URL url = new URL(url_);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法（GET、POST等）
            connection.setRequestMethod("GET");
            // 设置连接和读取超时时间（可选）
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 发起连接
            connection.connect();
            // 获取响应码
            int responseCode = connection.getResponseCode();
            // 如果响应码为200表示请求成功
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 获取输入流
                InputStream inputStream = connection.getInputStream();
                // 读取输入流中的数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String responseData = stringBuilder.toString();
                // 关闭输入流和连接
                reader.close();
                inputStream.close();
                connection.disconnect();
                Log.v("download",responseData);
                // 返回下载的数据
                return responseData;
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public ArrayList<ShopLocation> parseJsonObjects(String content) {
        // 创建一个用于存储解析后数据的ArrayList
        ArrayList<ShopLocation> locations = new ArrayList<>();
        try {
            // 将传入的JSON字符串转换为JSONObject对象
            JSONObject jsonObject = new JSONObject(content);
            // 从JSONObject中获取名为“shops”的JSON数组
            JSONArray jsonDatas = jsonObject.getJSONArray("shops");
            // 获取JSON数组的长度，用于循环遍历每个商店信息
            int length = jsonDatas.length();

            // 遍历JSON数组中的每个元素
            for (int i = 0; i < length; i++) {
                // 获取数组中当前索引位置的JSONObject，表示一个商店的信息
                JSONObject shopJson = jsonDatas.getJSONObject(i);
                // 创建一个新的ShopLocation对象，用于存储当前商店的信息
                ShopLocation shop = new ShopLocation();

                // 从JSON对象中获取商店的名称并设置到ShopLocation对象中
                shop.setName(shopJson.getString("name"));
                // 从JSON对象中获取商店的纬度信息并设置到ShopLocation对象中
                shop.setLatitude(shopJson.getDouble("latitude"));
                // 从JSON对象中获取商店的经度信息并设置到ShopLocation对象中
                shop.setLongitude(shopJson.getDouble("longitude"));
                // 从JSON对象中获取商店的备注信息并设置到ShopLocation对象中
                shop.setMemo(shopJson.getString("memo"));

                // 将填充了商店信息的ShopLocation对象添加到locations ArrayList中
                locations.add(shop);
            }
        } catch (Exception e) {
            // 捕获可能发生的异常并打印出异常信息
            e.printStackTrace();
        }
        // 返回包含解析后商店信息的ShopLocation对象ArrayList
        return locations;
    }

}
