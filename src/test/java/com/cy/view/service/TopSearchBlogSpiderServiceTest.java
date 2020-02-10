package com.cy.view.service;

import com.cy.view.service.weibo.TopSearchBlogSpiderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopSearchBlogSpiderServiceTest {

    @Autowired
    private TopSearchBlogSpiderService topSearchBlogSpiderService;

    @Test
    public void testCollectTopSearchInfo(){
        String keyword = "可直接与武汉医院对接捐赠";
        String cookie = "SINAGLOBAL=3525601727940.988.1581302234908; wvr=6; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhsxebFvSKMYXlP6uzl.mm15JpX5KMhUgL.Foef1Kq4SKn7eo22dJLoIEqLxK-LBo2LBo2LxK.LBKeL12-LxKBLBo.L12zLxK-LBozL1-8owBtt; UOR=,,login.sina.com.cn; webim_unReadCount=%7B%22time%22%3A1581307751918%2C%22dm_pub_total%22%3A0%2C%22chat_group_client%22%3A0%2C%22allcountNum%22%3A0%2C%22msgbox%22%3A0%7D; ALF=1612846826; SSOLoginState=1581310828; SCF=Ao9rVGiEihHQrRMGvH21bdfUjbL4X90pDvPN8bS4uRtN7we5TvjtEYdrRuteY8plUioGtRodJYb6X5yaO9kK5ww.; SUB=_2A25zRJM8DeRhGeVL4lQY9SbMyT2IHXVQM4P0rDV8PUNbmtANLWP8kW9NTDJ5VziYCIZ6R-DnQTdZtNcF1x8IPAO8; SUHB=0laV2Qw4IF0jTu; _s_tentry=login.sina.com.cn; Apache=5115489028811.262.1581310831995; ULV=1581310832183:4:4:4:5115489028811.262.1581310831995:1581306707687";
        String template = "https://s.weibo.com/realtime?q=%23{}%23&rd=realtime&tw=realtime&Refer=weibo_realtime&page={}";

        topSearchBlogSpiderService.collectTopSearchBlogInfo(keyword,cookie,template);
        /*TopSearchBlogSpiderService topSearchBlogSpiderService = new TopSearchBlogSpiderService();
        topSearchBlogSpiderService.collectTopSearchBlogInfo(keyword,cookie,template);*/
        /*String time = "02月07日 20:29";
        Date date = topSearchBlogSpiderService.transferWeiboTimeString(time);
        System.out.println(date);*/
    }



}
