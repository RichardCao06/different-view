package com.cy.view.job.weibo;

import cn.hutool.core.date.DateUtil;
import com.cy.view.service.weibo.TopSearchBlogSpiderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: 微博热搜定期爬虫任务
 * @author CaoYong
 * @date: 2020/2/11
 * @version 1.0
 */
@Slf4j
@Component
public class TopSearchBlogJob {

    @Autowired
    TopSearchBlogSpiderService topSearchBlogSpiderService;

    private Logger logger = LoggerFactory.getLogger(TopSearchBlogJob.class);

    //private static final String TopSearchRealtimeUrlTemplate = "https://s.weibo.com/realtime?q=%23{}%23&rd=realtime&tw=realtime&Refer=weibo_realtime&page={}";

    private static final String COOKIE = "SINAGLOBAL=3525601727940.988.1581302234908; wvr=6; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhsxebFvSKMYXlP6uzl.mm15JpX5KMhUgL.Foef1Kq4SKn7eo22dJLoIEqLxK-LBo2LBo2LxK.LBKeL12-LxKBLBo.L12zLxK-LBozL1-8owBtt; UOR=,,login.sina.com.cn; webim_unReadCount=%7B%22time%22%3A1581307751918%2C%22dm_pub_total%22%3A0%2C%22chat_group_client%22%3A0%2C%22allcountNum%22%3A0%2C%22msgbox%22%3A0%7D; ALF=1612846826; SSOLoginState=1581310828; SCF=Ao9rVGiEihHQrRMGvH21bdfUjbL4X90pDvPN8bS4uRtN7we5TvjtEYdrRuteY8plUioGtRodJYb6X5yaO9kK5ww.; SUB=_2A25zRJM8DeRhGeVL4lQY9SbMyT2IHXVQM4P0rDV8PUNbmtANLWP8kW9NTDJ5VziYCIZ6R-DnQTdZtNcF1x8IPAO8; SUHB=0laV2Qw4IF0jTu; _s_tentry=login.sina.com.cn; Apache=5115489028811.262.1581310831995; ULV=1581310832183:4:4:4:5115489028811.262.1581310831995:1581306707687";

    /**
     * 定时抓取微博热搜实时信息
     */
    //@Scheduled(fixedDelay = 5 * 1000 * 60)
    public void CollectRealtimeTopSearchBlogTask(){
        String keyword = "上海虹桥枢纽已发现体温异常101人";
        log.info("【job】开始执行：{}", DateUtil.formatDateTime(new Date()));
        topSearchBlogSpiderService.collectRealtimeTopSearchBlog(keyword,COOKIE);
        log.info("【job】执行完毕：{}", DateUtil.formatDateTime(new Date()));

    }


}
