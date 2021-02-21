package com.charles;

import com.charles.domain.FundDto;
import com.charles.domain.FundModel;
import com.charles.util.DateUtil;
import com.charles.util.HttpClientUtils;
import com.charles.util.PoiUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


    @Test
    public void t1() throws Exception {
        //http://fund.eastmoney.com/trade/pg.html
        //开放基金排行
        String type = "hh";//hh 混合；pg 偏股；gp 股票；zs 指数；qdii
        String url = "https://fundapi.eastmoney.com/fundtradenew.aspx?ft=pg&sc=1n&st=desc&pi=1&pn=100&cp=&ct=&cd=&ms=&fr=&plevel=&fst=&ftype=&fr1=&fl=0&isab=1";
        String res = HttpClientUtils.doGet(url, "", "utf-8");
        String obj = res.substring(15, res.length() - 1);
        System.out.println(obj);
    }

    @Test
    public void t2() {
        System.out.println("var rankData = ".length());
    }

    @Test
    public void t3() {
        String data = "{datas:[\"005968|创金合信工业周期股票A||2021-02-10|3.7109|2.54|8.45|17.28|56.09|90.71|183.69|327.38||31.92|271.09|3|1|1|0.15|0||080|1|1|100元|1.50%|0.15%|0.15%|1\",\"005969|创金合信工业周期股票C||2021-02-10|3.6445|2.54|8.43|17.21|55.82|90.06|181.75|321.82||31.82|264.45|3|0|1|0.00|0||080|1|1|100元||0.00%||\",\"570001|诺德价值优势混合||2021-02-10|3.8995|2.32|8.40|13.16|37.27|59.17|170.12|239.75|193.14|21.11|415.50|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"161725|招商中证白酒指数(LOF)||2021-02-10|1.6198|4.92|9.17|5.96|37.92|81.80|167.77|304.00|272.47|13.54|515.37|3|1|1|0.10|0||020,050,051,054|1|1|100元|1.00%|0.10%|0.10%|1\",\"160632|鹏华酒||2021-02-10|1.2020|4.25|8.58|5.72|36.52|76.65|167.57|308.35|272.69|12.86|384.67|3|1|1|0.12|0||020,050,051,054|1|1|100元|1.20%|0.12%|0.12%|1\",\"570008|诺德周期策略混合||2021-02-10|4.7480|2.50|8.43|12.86|36.05|57.37|162.90|275.63|222.85|20.63|710.55|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"001384|东方新思路混合A||2021-02-10|2.0998|3.79|9.05|9.87|37.67|61.81|150.96|196.58|158.95|16.56|109.98|3|1|1|0.08|0||215|1|1|100元|0.80%|0.08%|0.08%|1\",\"004997|广发高端制造股票A||2021-02-10|3.2509|0.76|4.73|16.88|27.27|44.89|150.01|331.15|235.25|22.96|225.06|3|1|1|0.15|0||080|1|1|100元|1.50%|0.15%|0.15%|1\",\"001385|东方新思路混合C||2021-02-10|2.0536|3.78|9.04|9.82|37.53|61.47|149.95|194.21|155.93|16.50|105.36|3|0|1|0.00|0||215|1|1|100元||0.00%||\",\"007047|长城核心优势混合||2021-02-10|2.6140|4.16|9.62|12.29|34.04|46.02|147.37|||18.59|174.38|3|1|1|0.15|0||211,701|1|1|100元|1.50%|0.15%|0.15%|1\",\"006604|嘉实消费精选股票A||2021-02-10|2.6612|3.33|7.03|9.71|35.38|57.47|143.50|||18.25|166.12|3|1|1|0.15|0||701|1|1|100元|1.50%|0.15%|0.15%|1\",\"000529|广发竞争优势混合||2021-02-10|5.0910|3.37|9.11|14.07|36.60|56.50|142.89|261.58|198.59|19.65|409.10|3|1|1|0.15|0||215|1|1|10元|1.50%|0.15%|0.15%|1\",\"006605|嘉实消费精选股票C||2021-02-10|2.6326|3.32|7.02|9.66|35.21|57.08|142.32|||18.19|163.26|3|0|1|0.00|0||701|1|1|100元||0.00%||\",\"004995|广发品牌消费股票发起式A||2021-02-10|2.5987|3.02|7.97|13.37|35.92|49.85|141.85|246.40|172.74|20.23|159.87|3|1|1|0.15|0||080|1|1|100元|1.50%|0.15%|0.15%|1\",\"162605|景顺长城鼎益混合(LOF)||2021-02-10|3.8830|3.49|9.81|10.91|36.79|57.56|141.64|251.04|237.83|18.08|2,808.30|3|1|1|0.15|0||020,211|1|1|100元|1.50%|0.15%|0.15%|1\",\"006345|景顺长城集英两年定开混合||2021-02-10|2.5662|3.42|7.86|15.63|39.54|58.77|141.39|||21.44|156.62|0|1|1|0.15|0||070,071,211|0|6|100元|1.50%|0.15%|0.15%|1\",\"001832|易方达瑞恒灵活配置混合||2021-02-10|3.0680|3.65|8.18|12.92|35.93|63.45|140.06|271.88|226.73|20.60|206.80|3|1|1|0.06|0||215|1|1|100元|0.60%|0.06%|0.06%|1\",\"005004|交银品质升级混合||2021-02-10|2.4637|2.22|6.94|7.54|24.21|51.81|140.03|193.33|146.37|13.51|146.37|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"001679|前海开源中国稀缺资产混合A||2021-02-10|3.8110|3.53|8.48|13.36|43.22|51.95|139.84|354.77|462.09|22.15|281.10|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"002079|前海开源中国稀缺资产混合C||2021-02-10|3.9890|3.53|8.46|13.36|43.18|51.85|139.58|353.81|461.04|22.14|298.90|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"001102|前海开源国家比较优势混合||2021-02-10|4.7480|3.56|8.55|13.83|44.80|53.01|139.07|330.85|272.10|22.97|374.80|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"005827|易方达蓝筹精选混合||2021-02-10|3.5287|3.13|6.63|16.44|36.73|61.44|138.75|254.50||23.12|252.87|3|1|1|0.15|0||211|1|1|10元|1.50%|0.15%|0.15%|1\",\"000336|农银研究精选混合||2021-02-10|4.0558|1.13|2.52|3.76|32.46|50.58|138.37|310.51|218.70|10.52|305.58|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"007412|景顺长城绩优成长混合||2021-02-10|2.4974|3.32|7.74|16.06|40.07|55.65|138.26|||21.16|149.74|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"005453|前海开源医疗健康A||2021-02-10|3.4821|1.88|7.00|15.68|40.10|49.41|137.91|297.73|271.78|20.94|248.18|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"005454|前海开源医疗健康C||2021-02-10|3.4723|1.88|7.00|15.67|40.07|49.34|137.68|297.02|270.77|20.93|247.20|3|0|1|0.00|0||215|1|1|100元||0.00%||\",\"005888|华夏新兴消费混合A||2021-02-10|3.6888|3.02|7.60|11.59|32.98|58.71|137.65|262.57||17.76|268.88|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"001135|益民品质升级混合||2021-02-10|1.5100|4.86|9.42|12.02|40.07|54.24|137.42|198.42|160.34|20.80|51.00|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"519714|交银消费新驱动股票||2021-02-10|2.2670|2.16|6.88|7.44|23.68|50.23|137.13|188.83|183.81|12.90|599.36|3|1|1|0.15|0|||1|1|100元|1.50%|0.15%|0.15%|1\",\"005671|前海联合研究优选混合A||2021-02-10|2.2830|1.87|6.35|13.31|33.57|52.57|136.91|216.92||20.55|219.42|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"260104|景顺长城内需增长混合||2021-02-10|16.4860|3.54|10.16|11.87|36.68|57.05|136.55|260.52|247.98|19.24|3,083.06|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"519979|长信内需成长混合A||2021-02-10|3.8250|3.41|8.66|12.67|34.59|54.11|136.55|223.96|190.76|19.01|628.02|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"005889|华夏新兴消费混合C||2021-02-10|3.6476|3.02|7.60|11.54|32.81|58.32|136.49|258.98||17.69|264.76|3|0|1|0.00|0||211|1|1|100元||0.00%||\",\"002780|前海联合泓鑫混合A||2021-02-10|3.7992|1.77|5.96|12.27|36.53|55.39|136.27|287.63|279.16|21.16|279.92|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"004868|交银股息优化混合||2021-02-10|3.1250|2.20|6.91|7.30|23.33|51.32|136.15|220.51|197.85|12.76|212.47|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"007043|前海联合泓鑫混合C||2021-02-10|3.7954|1.77|5.95|12.23|36.39|55.07|135.32|||21.10|258.12|3|0|1|0.00|0||215|1|1|100元||0.00%||\",\"005672|前海联合研究优选混合C||2021-02-10|2.2184|1.86|6.33|13.22|33.30|51.96|135.05|211.76||20.44|212.92|3|0|1|0.00|0||215|1|1|100元||0.00%||\",\"005235|银华食品饮料量化股票发起式A||2021-02-10|2.8446|2.94|6.51|6.48|29.32|48.15|134.99|225.66|204.43|13.13|184.46|3|1|1|0.15|0||080|1|1|100元|1.50%|0.15%|0.15%|1\",\"005236|银华食品饮料量化股票发起式C||2021-02-10|2.8242|2.94|6.50|6.44|29.19|47.86|134.08|223.95|201.83|13.08|182.42|3|0|1|0.00|0||080|1|1|100元||0.00%||\",\"002959|汇添富盈泰混合||2021-02-10|2.7430|3.59|7.99|15.64|47.95|59.48|133.84|152.58|160.00|26.00|174.30|3|1|1|0.12|0||215|1|1|100元|1.20%|0.12%|0.12%|1\",\"001606|农银工业4.0混合||2021-02-10|3.7226|1.20|1.86|0.61|30.41|53.28|133.82|275.04|165.67|6.51|272.26|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"162203|泰达宏利稳定混合||2021-02-10|3.3671|2.78|8.02|12.46|33.63|54.97|133.57|234.27|195.72|18.03|1,671.76|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"000263|工银信息产业混合A||2021-02-10|4.9120|2.10|5.75|7.30|24.64|57.34|133.46|281.37|255.68|13.39|474.28|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"000993|华宝稳健回报混合||2021-02-10|2.0450|3.81|8.26|10.90|40.94|62.43|132.92|195.52|130.03|20.65|104.50|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"004987|诺德新享灵活配置混合||2021-02-10|2.7617|4.14|9.56|9.87|38.70|71.77|132.47|158.10|155.64|18.04|176.17|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"007548|易方达ESG责任投资股票||2021-02-10|2.2944|3.32|6.75|15.65|36.73|61.00|132.44|||20.87|129.44|3|1|1|0.15|0||080|1|1|100元|1.50%|0.15%|0.15%|1\",\"001857|易方达现代服务业混合||2021-02-10|2.7400|3.05|7.58|9.91|37.00|56.30|132.20|229.33|188.12|16.79|174.00|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"006121|华安双核驱动混合||2021-02-10|3.0217|3.37|8.60|16.69|39.95|52.63|132.08|||23.93|202.17|3|1|1|0.15|0||211,701|1|1|100元|1.50%|0.15%|0.15%|1\",\"270007|广发大盘成长混合||2021-02-10|2.7708|3.41|9.00|13.76|35.84|53.25|131.90|232.76|186.37|19.19|216.12|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"006977|农银汇理海棠三年定开混合||2021-02-10|2.4139|||1.45|29.40|45.66|131.80|||8.62|200.08|0|1|1|0.15|0||070,071,211|0|6|100元|1.50%|0.15%|0.15%|1\",\"260109|景顺长城内需贰号混合||2021-02-10|2.2870|3.67|10.43|11.78|36.72|55.39|131.44|251.48|236.62|18.70|1,341.99|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"100020|富国天益价值混合A||2021-02-10|3.6272|3.51|9.71|15.14|43.42|52.96|131.25|242.24|220.73|23.34|3,174.66|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"560003|益民创新优势混合||2021-02-10|1.9380|4.59|9.13|11.39|38.42|51.58|131.21|171.54|137.94|19.95|96.75|3|1|1|0.15|0||211|1|1|500元|1.50%|0.15%|0.15%|1\",\"005543|银华心诚灵活配置混合||2021-02-10|2.6077|3.30|6.49|17.75|38.42|51.07|130.57|226.70||25.56|160.77|3|1|1|0.15|0||215,701|1|1|100元|1.50%|0.15%|0.15%|1\",\"006252|永赢消费主题A||2021-02-10|4.0091|3.58|6.65|13.50|38.32|65.68|130.08|289.76||22.60|300.91|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"001716|工银新趋势灵活配置混合A||2021-02-10|3.4050|2.07|2.31|5.13|27.43|45.45|129.76|208.98|166.02|12.30|240.50|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"006253|永赢消费主题C||2021-02-10|3.9861|3.58|6.65|13.48|38.26|65.52|129.61|287.79||22.57|298.61|3|0|1|0.00|0||215|1|1|100元||0.00%||\",\"001245|工银生态环境股票||2021-02-10|1.9670|1.29|0.72|2.23|31.40|55.37|129.25|236.82|171.69|9.95|96.70|3|1|1|0.15|0|||1|1|100元|1.50%|0.15%|0.15%|1\",\"001997|工银新趋势灵活配置混合C||2021-02-10|3.1820|2.05|2.28|5.05|27.23|44.97|128.10|204.79|160.61|12.20|218.20|3|0|1|0.00|0||215|1|1|100元||0.00%||\",\"006299|恒越核心精选混合A||2021-02-10|2.5597|3.59|5.20|10.30|40.80|57.45|127.93|152.51||20.39|155.97|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"160222|国泰国证食品饮料行业(LOF)||2021-02-10|1.5384|3.17|8.40|6.35|28.23|49.26|127.85|225.93|207.75|13.74|548.54|3|0|1|0.00|0||020,050,051,054|1|1|100元||0.00%||\",\"260108|景顺长城新兴成长混合||2021-02-10|3.6800|3.37|9.72|11.12|35.42|53.65|127.53|242.78|234.63|17.85|946.58|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"007193|恒越核心精选混合C||2021-02-10|2.5649|3.59|5.20|10.28|40.72|57.30|127.51|||20.36|128.70|3|0|1|0.00|0||211|1|1|100元||0.00%||\",\"001104|华安新丝路主题股票||2021-02-10|3.2250|3.56|9.58|16.34|39.34|50.06|126.96|260.57|250.82|23.90|237.49|3|1|1|0.15|0|||1|1|100元|1.50%|0.15%|0.15%|1\",\"002190|农银新能源主题||2021-02-10|2.8987|1.47|2.36|-1.80|26.95|53.79|126.94|241.75|174.91|3.93|189.87|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"006615|工银战略新兴产业混合A||2021-02-10|3.0410|2.89|5.40|10.48|25.77|40.23|126.84|||14.13|204.10|0|1|1|0.15|0||211|0|6|100元|1.50%|0.15%|0.15%|1\",\"001054|工银新金融股票||2021-02-10|3.1030|2.17|6.27|7.67|34.45|54.61|126.50|238.02|200.68|16.13|210.30|3|1|1|0.15|0|||1|1|100元|1.50%|0.15%|0.15%|1\",\"001140|工银总回报灵活配置混合A||2021-02-10|2.4910|2.76|6.41|9.83|38.54|63.45|126.25|187.98|163.04|18.11|149.10|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"001008|工银国企改革股票||2021-02-10|2.7660|2.14|5.33|11.62|40.48|60.16|126.17|182.82|153.53|20.68|176.60|3|1|1|0.15|0|||1|1|100元|1.50%|0.15%|0.15%|1\",\"006616|工银战略新兴产业混合C||2021-02-10|3.0119|2.89|5.39|10.44|25.64|39.95|125.95|||14.08|201.19|0|0|1|0.00|0||211|0|6|100元||0.00%||\",\"001476|中银智能制造股票||2021-02-10|1.8870|2.61|4.43|7.22|27.76|52.30|125.45|247.51|163.18|17.20|88.70|3|1|1|0.15|0|||1|1|100元|1.50%|0.15%|0.15%|1\",\"005241|中欧时代智慧混合A||2021-02-10|2.8286|4.10|8.02|13.79|44.48|62.86|125.35|244.44|224.88|24.78|209.41|3|1|1|0.15|0||211,701|1|1|100元|1.50%|0.15%|0.15%|1\",\"001631|天弘中证食品饮料指数A||2021-02-10|3.9526|2.94|7.17|3.73|26.77|43.51|125.22|217.73|199.59|10.84|316.01|3|1|1|0.10|0||050,051,054,080|1|1|100元|1.00%|0.10%|0.10%|1\",\"001815|华泰柏瑞激励动力混合A||2021-02-10|4.0310|3.09|10.38|14.61|41.89|38.00|125.07|267.46|268.04|25.62|322.51|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"005812|鹏华产业精选||2021-02-08|2.7873|1.19|4.57|9.11|27.79|41.54|125.02|285.09||12.39|178.73|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"003624|创金合信资源股票发起式A||2021-02-10|2.4680|3.15|6.35|6.25|42.08|66.25|125.00|199.44|116.13|21.70|146.80|3|1|1|0.15|0||080|1|1|100元|1.50%|0.15%|0.15%|1\",\"004986|鹏华策略回报混合||2021-02-10|2.4885|3.99|7.33|8.33|28.10|43.66|124.86|228.17|148.48|14.03|203.01|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"001632|天弘中证食品饮料指数C||2021-02-10|3.9048|2.94|7.17|3.71|26.71|43.37|124.78|216.49|197.75|10.82|310.99|3|0|1|0.00|0||050,051,054,080|1|1|100元||0.00%||\",\"180012|银华富裕主题混合||2021-02-10|8.1914|3.83|9.71|14.11|37.32|52.32|124.75|246.48|210.66|21.69|1,561.11|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"002082|华泰柏瑞激励动力混合C||2021-02-10|4.4640|3.09|10.36|14.61|41.85|37.82|124.55|263.81|263.15|25.60|363.70|3|0|1|0.00|0||215|1|1|100元||0.00%||\",\"160645|鹏华精选回报三年定开混合||2021-02-10|2.4169|3.79|5.87|9.88|26.53|39.62|124.39|||14.70|141.69|0|1|1|0.15|0||070,071,211|0|6|100元|1.50%|0.15%|0.15%|1\",\"560002|益民红利成长混合||2021-02-10|0.9883|4.43|9.05|11.30|37.21|49.79|123.90|173.84|145.78|19.35|169.62|3|1|1|0.15|0||211|1|1|500元|1.50%|0.15%|0.15%|1\",\"003625|创金合信资源股票发起式C||2021-02-10|2.4138|3.15|6.34|6.20|41.90|65.84|123.89|198.92|114.50|21.64|141.38|3|0|1|0.00|0||080|1|1|100元||0.00%||\",\"002910|易方达供给改革混合||2021-02-10|2.2676|2.43|6.13|20.40|36.26|40.35|123.76|179.99|125.09|29.14|126.76|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"005242|中欧时代智慧混合C||2021-02-10|2.7529|4.10|8.00|13.71|44.20|62.24|123.59|238.60|216.73|24.68|201.56|3|0|1|0.00|0||211,701|1|1|100元||0.00%||\",\"007084|天治转型升级混合||2021-02-10|2.5226|2.10|7.13|16.43|36.06|47.55|123.56|||23.32|152.26|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"002846|泓德泓华混合||2021-02-10|2.5890|1.82|6.74|8.35|29.65|54.79|123.52|254.92|223.25|16.50|233.27|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"161837|银华大盘两年定开混合||2021-02-10|2.2390|3.52|8.07|13.63|34.23|49.18|123.43|||21.31|123.90|0|1|1|0.15|0||070,071,211|0|6|100元|1.50%|0.15%|0.15%|1\",\"004812|中欧先进制造股票A||2021-02-10|2.9731|1.73|4.47|2.94|32.91|63.43|123.36|246.43|223.34|10.13|197.31|3|1|1|0.15|0||080,701|1|1|100元|1.50%|0.15%|0.15%|1\",\"001496|工银聚焦30股票||2021-02-10|1.7750|2.36|4.35|9.50|25.80|43.03|122.99|185.37|156.50|13.64|77.50|3|1|1|0.15|0|||1|1|100元|1.50%|0.15%|0.15%|1\",\"001163|银华中国梦30股票||2021-02-10|2.7360|2.74|6.71|7.08|21.28|40.45|122.98|227.66|153.33|14.43|173.60|3|1|1|0.15|0|||1|1|100元|1.50%|0.15%|0.15%|1\",\"161818|银华消费主题混合||2021-02-10|2.2005|2.68|5.12|6.05|27.85|47.20|122.63|273.39|166.07|11.18|235.69|3|1|1|0.15|0||211,701|1|1|100元|1.50%|0.15%|0.15%|1\",\"002132|广发鑫享混合||2021-02-10|2.8620|1.60|4.99|13.98|26.81|43.17|122.55|280.08|196.27|19.95|186.20|3|1|1|0.15|0||215|1|1|100元|1.50%|0.15%|0.15%|1\",\"240011|华宝大盘精选混合||2021-02-10|4.8011|3.61|8.80|10.25|37.15|56.87|122.42|252.03|198.45|19.91|477.08|3|1|1|0.15|0||211|1|1|100元|1.50%|0.15%|0.15%|1\",\"008314|上投摩根慧选成长A||2021-02-10|2.2320|3.02|6.31|15.34|33.48|48.32|122.24|||20.84|123.20|3|1|1|0.15|0|||1|1|100元|1.50%|0.15%|0.15%|1\",\"002083|新华鑫动力灵活配置混合A||2021-02-10|2.5192|1.43|-0.05|-2.31|35.81|65.09|121.96|246.52|170.88|8.35|151.92|3|1|1|0.10|0||215|1|1|100元|1.00%|0.10%|0.10%|1\",\"160127|南方新兴消费增长股票(LOF)A||2021-02-10|1.2651|3.16|7.94|14.24|27.81|40.78|121.85|218.45|149.41|20.27|498.20|3|1|1|0.15|0||020|1|1|100元|1.50%|0.15%|0.15%|1\",\"501065|汇添富经典成长定开混合||2021-02-10|2.2733|2.55|5.47|18.95|24.09|35.43|121.69|205.36||22.94|216.66|0|1|1|0.15|0||070,071,211|0|6|100元|1.50%|0.15%|0.15%|1\",\"004813|中欧先进制造股票C||2021-02-10|2.9625|1.73|4.45|2.86|32.65|62.79|121.61|246.45|222.33|10.04|196.25|3|0|1|0.00|0||080,701|1|1|100元||0.00%||\",\"002084|新华鑫动力灵活配置混合C||2021-02-10|2.5059|1.42|-0.05|-2.33|35.75|64.97|121.56|245.64|170.03|8.34|150.59|3|0|1|0.00|0||215|1|1|100元|0.00%|0.00%|0.00%|0\"],allRecords:5860,pageIndex:1,pageNum:100,allPages:59}";
        JSONObject obj = JSONObject.fromObject(data);
        JSONArray list = JSONArray.fromObject(obj.get("datas"));
        System.out.println(list.get(0));
        String item = String.valueOf(list.get(3));
        String[] strs = item.split("\\|");
        System.out.println(strs.length);
        FundDto dto = FundDto.builder()
                .code(strs[0])
                .name(strs[1])
                .curDate(strs[3])
                .val1(strs[4])
                .val2(strs[4])
                .day(strs[5])
                .week(strs[6])
                .month(strs[7])
                .threeMonth(strs[8])
                .sixMonth(strs[9])
                .year(strs[10])
                .twoYear(strs[11])
                .threeYear(strs[12])
                .thisYear(strs[13])
                .base(strs[14])
                .build();
        System.out.println(dto);

    }

    @Test
    public void t5() throws Exception {
        //https://danjuanapp.com/djapi/fund/detail/399011  获取持仓占比
        //https://danjuanapp.com/djapi/fund/derived/399011 获取本基金的所有数据
        //https://danjuanapp.com/djapi/fund/nav/history/161725?size=365&page=1 查看历史净值
        String url = "https://danjuanapp.com/djapi/fund/nav/history/161725?size=365&page=1";
        String res = HttpClientUtils.doGet(url, "", "utf-8");
        //计算1.26-1.29的涨幅
        Date startDate = DateUtil.date("2021-01-25");
        Date endDate = DateUtil.date("2021-01-30");
        JSONArray array = JSONArray.fromObject(JSONObject.fromObject(res).optJSONObject("data").optJSONArray("items"));
        System.out.println(array);
        float sum = 0;
        for (Object obj : array) {
            JSONObject item = JSONObject.fromObject(obj);
            Date date = DateUtil.date(item.optString("date"));
            if (date.before(endDate) && date.after(startDate)) {
                sum += Float.parseFloat(item.getString("percentage"));
                System.out.println(item.optString("date") + "," + item.getString("percentage"));
            }
        }
        System.out.println(sum);
    }

    @Test
    public void t6() throws Exception {
        //查看股票历史业绩
        String url = "https://danjuanapp.com/djapi/fund/derived/399011";
        String res = HttpClientUtils.doGet(url, "", "utf-8");
        JSONObject array = JSONObject.fromObject(res);
        System.out.println(array);
        //nav_grtd 今日  nav_grl1w 1周  nav_grl1m 1月 nav_grl3m 3月 nav_grl6m 6月
        System.out.println(array.optJSONObject("data").optJSONArray("yield_history"));
        System.out.println(array.optJSONObject("data").optString("nav_grl1m"));
    }

    @Test
    public void t7() throws Exception {
        //https://danjuanapp.com/djapi/fund/detail/399011  获取股票持仓占比
        String url = "https://danjuanapp.com/djapi/fund/detail/399011";
        String res = HttpClientUtils.doGet(url, "", "utf-8");
        System.out.println(JSONObject.fromObject(res).optJSONObject("data").optJSONObject("fund_position").optJSONArray("stock_list"));
    }

    /**
     * 获取指定基金的数据
     *
     * @throws Exception
     */
    @Test
    public void t8() throws Exception {
        //爬取网络数据，获取基金排行excel
        //要设置refer http://fund.eastmoney.com/data/fundranking.html
        List<FundDto> list = getFundDtos();
        System.out.println(list.size());
        Map<String, FundModel> fundModels = getFocusFunds();
        List<String> codeList = new ArrayList<>();
        codeList.addAll(fundModels.keySet());

        List<FundDto> fundDtos = list.stream()
                .filter(s -> codeList.contains(s.getCode()))
                .collect(Collectors.toList());
        createFundDatas(fundDtos);
    }

    /**
     * 筛选年收益，月收益
     *
     * @throws Exception
     */
    @Test
    public void t9() throws Exception {
        List<FundDto> list = getFundDtos();
        List<Float> maxDatas = getMaxDatas(list);
        System.out.println(maxDatas);
        List<FundDto> res = new ArrayList<>();
        //医药近6月收益32.3
        for (FundDto fundDto : list) {
//            String week = fundDto.getWeek();

            String month = fundDto.getMonth();
            String threeMonth = fundDto.getThreeMonth();
            String sixMonth = fundDto.getSixMonth();
            String year = fundDto.getYear();
            String twoYear = fundDto.getTwoYear();
            if (StringUtils.isBlank(month) || StringUtils.isBlank(threeMonth) || StringUtils.isBlank(sixMonth) || StringUtils.isBlank(year) || StringUtils.isBlank(twoYear)) {
//                System.out.println(fundDto);
                continue;
            }
            if (Float.parseFloat(month) < maxDatas.get(2) / 2) {
                continue;
            }
            if (Float.parseFloat(threeMonth) < maxDatas.get(3) / 2) {
                continue;
            }
            if (Float.parseFloat(sixMonth) < maxDatas.get(4) / 2) {
                continue;
            }
            if (Float.parseFloat(year) < maxDatas.get(5) / 2) {
                continue;
            }
            if (Float.parseFloat(twoYear) < maxDatas.get(6) / 2) {
                continue;
            }
            res.add(fundDto);
        }
        System.out.println(res.size());
        System.out.println(res);
        createFundDatas(res);
    }

    @Test
    public void t10() throws Exception {

    }

    /**
     * 计算所有基金的最大值，最小值
     *
     * @throws Exception
     */
    @Test
    public void t11() throws Exception {
        List<FundDto> list = getFundDtos();
        List<Float> maxDatas = getMaxDatas(list);
        System.out.println(maxDatas);
    }

    /**
     * 获取我的基金数据，计算累计收益，计算当天收益,计算持有金额
     *
     * @throws Exception
     */
    @Test
    public void t12() throws Exception {
        List<FundDto> list = getFundDtos();
        System.out.println(list.size());
        Map<String, FundModel> fundModels = getMyFunds();
        List<String> codeList = new ArrayList<>();
        codeList.addAll(fundModels.keySet());

        List<FundDto> fundDtos = list.stream()
                .filter(s -> codeList.contains(s.getCode()))
                .collect(Collectors.toList());
        //计算累计收益，计算当天收益,计算持有金额
        float dt = 0;
        float bt = 0;
        float mt = 0;
        for (FundDto fundDto : fundDtos) {
            FundModel fundModel = fundModels.get(fundDto.getCode());
            Float count = Float.parseFloat(fundModel.getCount());
            Float win = Float.parseFloat(fundDto.getVal1()) - Float.parseFloat(fundModel.getVal());
            Float lastDay = Float.parseFloat(fundDto.getVal1()) / (1 + Float.parseFloat(fundDto.getDay()) / 100);
            Float dayWin = Float.parseFloat(fundDto.getVal1()) - lastDay;
            fundModel.setDp(fundDto.getDay());
            fundModel.setBase(String.valueOf(win * count));
            fundModel.setDay(String.valueOf(dayWin * count));
            fundModel.setMoney(String.valueOf(Float.parseFloat(fundDto.getVal1()) * count));
            dt += Float.parseFloat(fundModel.getDay());
            bt += Float.parseFloat(fundModel.getBase());
            mt += Float.parseFloat(fundModel.getMoney());
        }
        FundModel total = FundModel.builder().code("xxxxxx").desc("总和").day(dt + "").base(bt + "").money(mt + "").build();
        fundModels.put("xxxxxx", total);
        System.out.println(fundModels);
        createFundprofits(fundModels);
    }


    /**
     * 基金排行
     *
     * @return
     * @throws Exception
     */
    private List<FundDto> getFundDtos() throws Exception {
        String pageSize = "1500";
        String res = "";
        File file = new File(System.getProperty("user.dir") + String.format("/src/main/resources/%s.txt", DateUtil.formtDate(new Date())));
        if (!file.exists()) {
            file.createNewFile();
            String url = String.format("http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=6yzf&st=desc&sd=2020-02-11&ed=2021-02-11&qdii=&tabSubtype=,,,,,&pi=1&pn=%s&dx=1&v=0.3096005927410441", pageSize);
            res = HttpClientUtils.doGet(url, "", "utf-8");
            FileUtils.writeStringToFile(file, res);
        } else {
            res = FileUtils.readFileToString(file);
        }

        res = res.substring(15, res.length() - 1);
        System.out.println(res);
        JSONArray jsonArray = JSONObject.fromObject(res).optJSONArray("datas");
        List<FundDto> list = new ArrayList<>();
        for (Object item : jsonArray) {
            String[] str = (item + "").split(",");
            FundDto fundDto = FundDto.builder()
                    .code(str[0])
                    .name(str[1])
                    .curDate(str[3])
                    .val1(str[4])
                    .val2(str[5])
                    .day(str[6])
                    .week(str[7])
                    .month(str[8])
                    .threeMonth(str[9])
                    .sixMonth(str[10])
                    .year(str[11])
                    .twoYear(str[12])
                    .threeYear(str[13])
                    .thisYear(str[14])
                    .base(str[15])
                    .build();
            list.add(fundDto);
        }
        return list;
    }

    private File createFundprofits(Map<String, FundModel> fundModels) {
        if (fundModels == null) {
            fundModels = new HashMap<>();
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row0 = sheet.createRow(0);
        int columnIndex = 0;
        int columnSize = 7;
        //总共16列
        //基金
        //代码	基金简称	日期	单位净值	累计净值	日增长率	近1周	近1月	近3月	近6月	近1年	近2年	近3年	今年来	成立来
        row0.createCell(columnIndex).setCellValue("序号");
        row0.createCell(++columnIndex).setCellValue("代码");
        row0.createCell(++columnIndex).setCellValue("基金简称");
        row0.createCell(++columnIndex).setCellValue("持有金额");
        row0.createCell(++columnIndex).setCellValue("日增长率");
        row0.createCell(++columnIndex).setCellValue("当天收益");
        row0.createCell(++columnIndex).setCellValue("总收益");
        int i = 0;
        for (String key : fundModels.keySet()) {
            FundModel dto = fundModels.get(key);
            Row row = sheet.createRow(++i);
            for (int j = 0; j < columnSize; j++) {
                row.createCell(j);
            }
            columnIndex = 0;
            row.getCell(columnIndex).setCellValue(i + 1);
            row.getCell(++columnIndex).setCellValue(dto.getCode());
            row.getCell(++columnIndex).setCellValue(dto.getDesc());
            row.getCell(++columnIndex).setCellValue(dto.getMoney());
            row.getCell(++columnIndex).setCellValue(dto.getDp());
            row.getCell(++columnIndex).setCellValue(dto.getDay());
            row.getCell(++columnIndex).setCellValue(dto.getBase());
        }
        return PoiUtils.createExcelFile(workbook, String.format("D://benefit_%s_%s.xlsx", DateUtil.getDate(), UUID.randomUUID()));
    }

    private Map<String, FundModel> getMyFunds() throws IOException {
        String path = System.getProperty("user.dir") + "/src/main/resources/fund.txt";
        System.out.println(path);
        File file = new File(path);
        List<String> list = FileUtils.readLines(file, "utf-8");
        Map<String, FundModel> funds = new TreeMap<>();
        for (String str : list) {
            String[] items = str.split(",");
            FundModel fundModel = FundModel.builder().code(items[0]).val(items[1]).count(items[2]).desc(items[3]).build();
            funds.put(items[0], fundModel);
        }
        return funds;
    }

    private Map<String, FundModel> getFocusFunds() throws IOException {
        String path = System.getProperty("user.dir") + "/src/main/resources/focus.txt";
        System.out.println(path);
        File file = new File(path);
        List<String> list = FileUtils.readLines(file, "utf-8");
        Map<String, FundModel> funds = new TreeMap<>();
        for (String str : list) {
            String[] items = str.split(",");
            funds.put(items[0], FundModel.builder().build());
        }
        return funds;
    }

    private List<Float> getMaxDatas(List<FundDto> list) {
        List<Float> data = new ArrayList<>();
        float max1d = 0;
        float max1w = 0;
        float max1m = 0;
        float max3m = 0;
        float max6m = 0;
        float max1y = 0;
        float max2y = 0;
        for (FundDto fundDto : list) {
            if (StringUtils.isNotBlank(fundDto.getDay())) {
                max1d = max1d > Float.parseFloat(fundDto.getDay()) ? max1d : Float.parseFloat(fundDto.getDay());
            }
            if (StringUtils.isNotBlank(fundDto.getWeek())) {
                max1w = max1w > Float.parseFloat(fundDto.getWeek()) ? max1w : Float.parseFloat(fundDto.getWeek());
            }
            if (StringUtils.isNotBlank(fundDto.getMonth())) {
                max1m = max1m > Float.parseFloat(fundDto.getMonth()) ? max1m : Float.parseFloat(fundDto.getMonth());
            }
            if (StringUtils.isNotBlank(fundDto.getThreeMonth())) {
                max3m = max3m > Float.parseFloat(fundDto.getThreeMonth()) ? max3m : Float.parseFloat(fundDto.getThreeMonth());
            }
            if (StringUtils.isNotBlank(fundDto.getSixMonth())) {
                max6m = max6m > Float.parseFloat(fundDto.getSixMonth()) ? max6m : Float.parseFloat(fundDto.getSixMonth());
            }
            if (StringUtils.isNotBlank(fundDto.getYear())) {
                max1y = max1y > Float.parseFloat(fundDto.getYear()) ? max1y : Float.parseFloat(fundDto.getYear());
            }
            if (StringUtils.isNotBlank(fundDto.getTwoYear())) {
                max2y = max2y > Float.parseFloat(fundDto.getTwoYear()) ? max2y : Float.parseFloat(fundDto.getTwoYear());
            }
        }
        data.add(max1d);
        data.add(max1w);
        data.add(max1m);
        data.add(max3m);
        data.add(max6m);
        data.add(max1y);
        data.add(max2y);
        return data;
    }

    public File createFundDatas(List<FundDto> records) {
        if (records == null) {
            records = new ArrayList<>();
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row0 = sheet.createRow(0);
        int columnIndex = 0;
        int columnSize = 16;
        //总共16列
        //基金
        //代码	基金简称	日期	单位净值	累计净值	日增长率	近1周	近1月	近3月	近6月	近1年	近2年	近3年	今年来	成立来
        row0.createCell(columnIndex).setCellValue("序号");
        row0.createCell(++columnIndex).setCellValue("代码");
        row0.createCell(++columnIndex).setCellValue("基金简称");
        row0.createCell(++columnIndex).setCellValue("日期");
        row0.createCell(++columnIndex).setCellValue("单位净值");

        row0.createCell(++columnIndex).setCellValue("累计净值");
        row0.createCell(++columnIndex).setCellValue("日增长率");
        row0.createCell(++columnIndex).setCellValue("近1周");
        row0.createCell(++columnIndex).setCellValue("近1月");
        row0.createCell(++columnIndex).setCellValue("近3月");

        row0.createCell(++columnIndex).setCellValue("近6月");
        row0.createCell(++columnIndex).setCellValue("近1年");
        row0.createCell(++columnIndex).setCellValue("近2年");
        row0.createCell(++columnIndex).setCellValue("近3年");
        row0.createCell(++columnIndex).setCellValue("今年来");

        row0.createCell(++columnIndex).setCellValue("成立来");
        for (int i = 0; i < records.size(); i++) {
            FundDto dto = records.get(i);
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < columnSize; j++) {
                row.createCell(j);
            }
            columnIndex = 0;
            row.getCell(columnIndex).setCellValue(i + 1);
            row.getCell(++columnIndex).setCellValue(dto.getCode());
            row.getCell(++columnIndex).setCellValue(dto.getName());
            row.getCell(++columnIndex).setCellValue(dto.getCurDate());
            row.getCell(++columnIndex).setCellValue(dto.getVal1());
            row.getCell(++columnIndex).setCellValue(dto.getVal2());
            row.getCell(++columnIndex).setCellValue(dto.getDay());
            row.getCell(++columnIndex).setCellValue(dto.getWeek());
            row.getCell(++columnIndex).setCellValue(dto.getMonth());
            row.getCell(++columnIndex).setCellValue(dto.getThreeMonth());
            row.getCell(++columnIndex).setCellValue(dto.getSixMonth());
            row.getCell(++columnIndex).setCellValue(dto.getYear());
            row.getCell(++columnIndex).setCellValue(dto.getTwoYear());
            row.getCell(++columnIndex).setCellValue(dto.getThreeYear());
            row.getCell(++columnIndex).setCellValue(dto.getThisYear());
            row.getCell(++columnIndex).setCellValue(dto.getBase());
        }
        return PoiUtils.createExcelFile(workbook, String.format("D://fund_%s_%s.xlsx", DateUtil.getDate(), UUID.randomUUID()));
    }

}
