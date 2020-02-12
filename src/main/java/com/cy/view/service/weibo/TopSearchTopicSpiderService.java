package com.cy.view.service.weibo;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.cy.view.domain.weibo.TopSearchBlog;
import com.cy.view.domain.weibo.TopSearchTopic;
import com.cy.view.domain.weibo.Topic;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @description: 抓取热搜话题
 * @author CaoYong
 * @date: 2020/2/11
 * @version 1.0
 */
@Service
public class TopSearchTopicSpiderService {

    private static final String UrlTemplate = "https://d.weibo.com/231650?cfs=920&Pl_Discover_Pt6Rank__4_filter=&Pl_Discover_Pt6Rank__4_page={}";
    private static  final String COOKIE = "SINAGLOBAL=3525601727940.988.1581302234908; wvr=6; UOR=,,www.baidu.com; ALF=1612939796; SSOLoginState=1581403798; SCF=Ao9rVGiEihHQrRMGvH21bdfUjbL4X90pDvPN8bS4uRtNnop0NdBg7-nFtRw-EOe0EUEM_qcanBElpNV2YLS9d0A.; SUB=_2A25zRj7HDeRhGeVL4lQY9SbMyT2IHXVQMhcPrDV8PUNbmtANLXWgkW9NTDJ5V3FRKzTCkuxfL2MBbKDCAI-FC_3D; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhsxebFvSKMYXlP6uzl.mm15JpX5KzhUgL.Foef1Kq4SKn7eo22dJLoIEqLxK-LBo2LBo2LxK.LBKeL12-LxKBLBo.L12zLxK-LBozL1-8owBtt; SUHB=0eb9Z1O_ZNmiNn; _s_tentry=weibo.com; Apache=6491399642059.283.1581403812550; ULV=1581403812624:9:9:9:6491399642059.283.1581403812550:1581393168987; webim_unReadCount=%7B%22time%22%3A1581403823286%2C%22dm_pub_total%22%3A0%2C%22chat_group_client%22%3A0%2C%22allcountNum%22%3A7%2C%22msgbox%22%3A0%7D";

    public void collectTopSearchTopic(String cookie, String urlTemplate){
        CloseableHttpClient client = HttpClients.createDefault();

        for (int i = 1; i <= 7; i++) {
            System.out.println(StrUtil.format("第{}页",i));

            //拼接查询url
            String searchUrl = StrUtil.format(UrlTemplate, i);
            HttpGet httpGet = new HttpGet(searchUrl);
            httpGet.setHeader("cookie",COOKIE);
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
            String result = null;
            //爬取信息
            try {
                CloseableHttpResponse response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("连接异常：" + e);
            }

            //数据存储
            if (result != null){
                System.out.println(result);
                //List<TopSearchTopic> topicList = getPageInfo(result);
                List<String> topicTitleList = getTopicTitle(result);
            }

            //休眠策略
            try {
                Thread.sleep(5000 * (long)Math.random());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }

    /**
     * 正则提取热搜话题
     * @param result
     * @return
     */
    private List<String> getTopicTitle(String result) {

        List<String> topicList = new ArrayList<>();
        List<String> reResult = ReUtil.findAllGroup0("alt=\\\\\"#.*?#", result);
        for (String s : reResult) {
            String topic = StrUtil.sub(s, 7, -1);
            topicList.add(topic);

        }
        System.out.println(topicList);
        return topicList;
    }

    /**
     * 解析页面信息
     * @param result
     * @return
     */
    /*private List<TopSearchTopic> getPageInfo(String result) {
        Document document = Jsoup.parse(result);
        Elements elements = document.select("#Pl_Discover_Pt6Rank__4 > div > div > div.WB_innerwrap > div > ul > *");
        int length = elements.toArray().length;
        List<TopSearchTopic> topicList = new ArrayList<>();
        for (int i = 1; i <= length ; i++) {

            *//**
             * 获取热点排名
             *//*
            String rankCssPath = StrUtil.format("li:nth-child({}) > div > div.info_box > div.text_box > div.title.W_autocut > span",i);
            String rank = elements.select(rankCssPath).text();

            *//**
             * 获取热点标题
             *//*
            String titleCssPath = StrUtil.format("li:nth-child({}) > div > div.info_box > div.text_box > div.title.W_autocut > a.S_txt1", i);
            String title = elements.select(titleCssPath).text();

            *//**
             * 获取热点链接
             *//*
            String topicUrl = elements.select(titleCssPath).attr("href");

            *//**
             * 获取热点标签
             *//*
            String tagCssPath = StrUtil.format("li:nth-child({}) > div > div.info_box > div.text_box > div.title.W_autocut > a.W_btn_b.W_btn_tag",i);
            String tag = elements.select(tagCssPath).text();

            *//**
             * 获取热点副标题
             *//*
            String subtitleCssPath = StrUtil.format("li:nth-child({}) > div > div.info_box > div.text_box > div.subtitle",i);
            String subtitle = elements.select(subtitleCssPath).text();

            *//**
             * 获取热点阅读数
             *//*
            String readNumCssPath = StrUtil.format("li:nth-child({}) > div > div.info_box > div.subinfo.clearfix > div:nth-child(1) > span > span > span", i);
            String readNum = elements.select(readNumCssPath).text();

            *//**
             * 获取热点主持人
             *//*
            String questionMasterNameCssPath = StrUtil.format("li:nth-child({}) > div > div.info_box > div.subinfo.clearfix > div:nth-child(2) > span > span > a", i);
            String questionMasterName = elements.select(questionMasterNameCssPath).text();
            *//**
             * 获取主持人url
             *//*
            String questionMasterUrl = elements.select(questionMasterNameCssPath).attr("href");

            TopSearchTopic topSearchTopic = new TopSearchTopic();

            topSearchTopic.setTitle(title);
            topSearchTopic.setTopicUrl(topicUrl);
            topSearchTopic.setRank(rank);
            topSearchTopic.setTag(tag);
            topSearchTopic.setSubtitle(subtitle);
            topSearchTopic.setReadNum(readNum);
            topSearchTopic.setQuestionMasterName(questionMasterName);
            topSearchTopic.setQuestionMasterName(questionMasterUrl);

            topicList.add(topSearchTopic);
            System.out.println(topSearchTopic.toString());

        }
        return topicList;
    }*/
}
