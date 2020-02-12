package com.cy.view.service.weibo.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import org.seimicrawler.xpath.JXDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: seimiCrawler爬虫框架demo
 * @author CaoYong
 * @date: 2020/2/11
 */
@Crawler(useCookie = true)
public class CrawlerDemo extends BaseSeimiCrawler {

    @Override
    public String[] startUrls() {
        return new String[]{"https://m.weibo.cn/api/statuses/repostTimeline?id=4470812956348768&page=3"};
    }

    @Override
    public void start(Response response) {

        //System.out.println(response.toString());

        List<String> cookies = new ArrayList<>();
        Map<String,String> headerMap = new HashMap();
        headerMap.put("cookie", "_T_WM=26231747755; WEIBOCN_FROM=1110006030; ALF=1583999287; SCF=Ao9rVGiEihHQrRMGvH21bdfUjbL4X90pDvPN8bS4uRtNNwnBVX2L6FfSx81RYlSodCCG_C1YVwKjzqD8L08Obuo.; SUB=_2A25zRiwgDeRhGeVH6FsY-CrOyD6IHXVQyLRorDV6PUJbktANLXbakW1NT0MNBVoQZq0kgEz0P61Ms7gBsLvc1jua; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WF2PLZ_h6UcscUlfVWlAnLC5JpX5KzhUgL.Foe4e0.41hBEe0z2dJLoIEjLxK-LBo2LBo2LxK.LBKeL12-LxKBLBo.L12zLxK-LBozL1-8oqgv4dc4r; SUHB=0LcSnQqOvcb5V2; SSOLoginState=1581407344; MLOGIN=1; XSRF-TOKEN=142335; M_WEIBOCN_PARAMS=oid%3D4469438344421081%26luicode%3D20000061%26lfid%3D4469438344421081%26uicode%3D20000061%26fid%3D4469438344421081");
        cookies.add("_T_WM=26231747755; WEIBOCN_FROM=1110006030; ALF=1583999287; SCF=Ao9rVGiEihHQrRMGvH21bdfUjbL4X90pDvPN8bS4uRtNNwnBVX2L6FfSx81RYlSodCCG_C1YVwKjzqD8L08Obuo.; SUB=_2A25zRiwgDeRhGeVH6FsY-CrOyD6IHXVQyLRorDV6PUJbktANLXbakW1NT0MNBVoQZq0kgEz0P61Ms7gBsLvc1jua; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WF2PLZ_h6UcscUlfVWlAnLC5JpX5KzhUgL.Foe4e0.41hBEe0z2dJLoIEjLxK-LBo2LBo2LxK.LBKeL12-LxKBLBo.L12zLxK-LBozL1-8oqgv4dc4r; SUHB=0LcSnQqOvcb5V2; SSOLoginState=1581407344; MLOGIN=1; XSRF-TOKEN=142335; M_WEIBOCN_PARAMS=oid%3D4469438344421081%26luicode%3D20000061%26lfid%3D4469438344421081%26uicode%3D20000061%26fid%3D4469438344421081");
        String result = response.toString();
        System.out.println(result);
        Request request = Request.build("https://m.weibo.cn/api/statuses/repostTimeline?id=4470812956348768&page=3", CrawlerDemo::getTotalTransactions)
                .useSeimiAgent()
                .setSeimiAgentUseCookie(true)
                //.setSeimiCookies(cookies)
                .setHeader(headerMap)
                .setSeimiAgentRenderTime(5000);
        push(request);

    }

    public void getTotalTransactions(Response response){
        String result = response.toString();
        System.out.println(result);
    }
}
