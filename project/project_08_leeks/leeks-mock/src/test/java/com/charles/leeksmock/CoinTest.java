package com.charles.leeksmock;

import com.charles.leeksmock.bean.FundBean;
import com.charles.leeksmock.handler.SinaCoinHandler;
import com.charles.leeksmock.handler.TianTianFundHandler;
import com.charles.leeksmock.utils.HttpClientPool;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * @author charles
 * @date 2021/3/21 9:57
 */
public class CoinTest {

    @Test
    public void t1() {
        String codes = "sh600151,sz000830,s_sh000001,s_sz399001,s_sz399106";
        String[] strs = codes.split(",");
        new SinaCoinHandler().handle(Arrays.asList(strs));
    }

    @Test
    public void t2() throws Exception {
        String codes = "sh600151,sz000830,s_sh000001,s_sz399001,s_sz399106";
        String res = HttpClientPool.getHttpClient().get(SinaCoinHandler.URL + codes);
        new SinaCoinHandler().handleResponse(res);
        System.out.println(res);
    }

    @Test
    public void t3() throws Exception {
        String codes = "110011";
        new TianTianFundHandler().handle(Arrays.asList(codes.split(",")));
        for (String code : codes.split(",")) {
            String result = HttpClientPool.getHttpClient().get(TianTianFundHandler.URL + code + ".js?rt=" + System.currentTimeMillis());
            System.out.println(result);
            String json = result.substring(8, result.length() - 2);
            System.out.println(json);
            if (!json.isEmpty()) {
                FundBean bean = new Gson().fromJson(json, FundBean.class);
                System.out.println(bean);
            }
        }
    }

    @Test
    public void t4() throws Exception {
        String codes = "sh600151,sz000830,s_sh000001,s_sz399001,s_sz399106";
        String result = HttpClientPool.getHttpClient().get("http://qt.gtimg.cn/q=" + codes);
        System.out.println(result);
    }

}
