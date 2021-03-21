package com.charles;

import com.charles.util.HttpClientUtils;
import com.google.gson.Gson;
import org.junit.Test;

/**
 * @author charles
 * @date 2021/3/21 23:47
 */
public class DanJuanTest {

    @Test
    public void t1() throws Exception{
        String code = "161725";
        String size = "365";
        String url = "https://danjuanapp.com/djapi/fund/nav/history/"+code+"?size="+size+"&page=1";
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
}
