package com.charles;

import com.alibaba.excel.EasyExcel;
import com.charles.domain.HistoryDto;
import com.charles.mapping.FieldMethod;
import com.charles.mapping.Mapping;
import com.charles.mapping.MappingField;
import com.charles.util.HttpClientUtils;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author charles
 * @date 2021/3/21 23:47
 */
public class DanJuanTest {

    @Test
    public void t1() throws Exception {
        String code = "161725";
        String size = "365";
        String url = "https://danjuanapp.com/djapi/fund/nav/history/" + code + "?size=" + size + "&page=1";
        String res = HttpClientUtils.doGet(url, "", "utf-8");
        System.out.println(res);
        new Gson().toJson(res);

//        s = s['data']['items']
        //1.月初和月末对比
        //2.当月最高涨和最低跌
        //3.当月波动值（最高涨和最低跌之差）
        //4.月差值(月末减月初，该月是否盈亏）
        // 5. 熟悉一下echarts
    }

    @Test
    public void t2() throws Exception {
//         {"股票型":1,"混合型":3,"债券型":2,"指数型":5,"QDII型":11}
        //dict_time={'近一周':'1w','近一月':'1m','近三月':'3m','近六月':'6m','近1年':'1y','近2年':'2y','近3年':'3y','近5年':'5y'}
        String url1 = "https://danjuanapp.com/djapi/v3/filter/fund?type=" + 1 + "&order_by=1m&size=20&page=1";
        String res = HttpClientUtils.doGet(url1, "", "utf-8");
        JSONObject jsonObject = JSONObject.fromObject(res);
        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("items");
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
//            System.out.println(obj.getString("fd_code") + "," + obj.getString("fd_name"));
            map.put(obj.getString("fd_code"), obj.getString("fd_name"));
        }
    }

    @Test
    public void t3() throws Exception {
        String url2 = "https://danjuanapp.com/djapi/fund/nav/history/%s?page=1&size=90";
//        String[] keys = "110011,005445,008099,005937,570001,166005,166019,163406,005267,260108".split(",");
        Map<String, Method> annoMethods = Mapping.getAnnoMethods(HistoryDto.class);
        //以日期为单位
        Map<String, HistoryDto> historyDtoMap = new LinkedHashMap<>();
        for (String key : annoMethods.keySet()) {
            Method method = annoMethods.get(key);
            String url = String.format(url2, key);
            String historyData = HttpClientUtils.doGet(url, "", "utf-8");
            JSONArray historyArray = JSONObject.fromObject(historyData).getJSONObject("data").getJSONArray("items");
            double data = 0d;
            for (int i = historyArray.size() - 1; i >= 0; i--) {
                JSONObject item = historyArray.getJSONObject(i);
                String date = item.getString("date");
                data += item.optDouble("percentage");
                HistoryDto model;
                if (historyDtoMap.containsKey(date)) {
                    model = historyDtoMap.get(date);
                } else {
                    model = HistoryDto.builder().day(date).build();
                    historyDtoMap.put(date, model);
                }
                method.invoke(model, String.format("%.2f", data));
            }
        }
        List<HistoryDto> arr = new ArrayList<>();
        arr.addAll(historyDtoMap.values());
        EasyExcel.write("D://history_" + UUID.randomUUID().toString().substring(0, 6) + ".xlsx", HistoryDto.class).needHead(true).sheet("收益").doWrite(arr);
    }

    @Test
    public void t4() throws Exception {
//        HistoryDto model = HistoryDto.builder().day("2021-03-23").code1(String.format("%.2f", 21)).build();
        List<FieldMethod> fieldMethods = Mapping.getFieldMethods(HistoryDto.class);
        HistoryDto historyDto = HistoryDto.builder().build();
        for (FieldMethod fieldMethod : fieldMethods) {
            System.out.println(fieldMethod.getField().getName());
            MappingField annotation = fieldMethod.getField().getAnnotation(MappingField.class);
            if (annotation != null) {
                System.out.println(annotation.code() + "," + annotation.name());
                fieldMethod.getMethod().invoke(historyDto, annotation.code());
            }
        }
        System.out.println(historyDto);
    }

    @Test
    public void t5() throws Exception {
        Map<String, Method> annoMethods = Mapping.getAnnoMethods(HistoryDto.class);
        String key = "110011";
        Method method = annoMethods.get(key);
        HistoryDto historyDto = HistoryDto.builder().build();
        method.invoke(historyDto, "123");
        System.out.println(historyDto);
    }

    @Test
    public void t6() {
        //如何根据focus.txt生成java文件
    }


}
