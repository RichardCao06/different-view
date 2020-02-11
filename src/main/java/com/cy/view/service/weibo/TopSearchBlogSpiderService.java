package com.cy.view.service.weibo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.cy.view.domain.weibo.Blog;
import com.cy.view.domain.weibo.TopSearchBlog;
import com.cy.view.domain.weibo.Topic;
import com.cy.view.repository.weibo.TopSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description: 抓取微博热搜信息
 * @author CaoYong
 * @date: 2020/2/10
 * @version
 */
@Component
@Slf4j
@Service
public class TopSearchBlogSpiderService {

    private Logger logger = LoggerFactory.getLogger(TopSearchBlogSpiderService.class);

    @Autowired
    TopSearchRepository topSearchRepository;



    private static final String TopSearchRealtimeUrlTemplate = "https://s.weibo.com/realtime?q=%23{}%23&rd=realtime&tw=realtime&Refer=weibo_realtime&page={}";

    private static  final String COOKIE = "SINAGLOBAL=3525601727940.988.1581302234908; wvr=6; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhsxebFvSKMYXlP6uzl.mm15JpX5KMhUgL.Foef1Kq4SKn7eo22dJLoIEqLxK-LBo2LBo2LxK.LBKeL12-LxKBLBo.L12zLxK-LBozL1-8owBtt; UOR=,,login.sina.com.cn; webim_unReadCount=%7B%22time%22%3A1581307751918%2C%22dm_pub_total%22%3A0%2C%22chat_group_client%22%3A0%2C%22allcountNum%22%3A0%2C%22msgbox%22%3A0%7D; ALF=1612846826; SSOLoginState=1581310828; SCF=Ao9rVGiEihHQrRMGvH21bdfUjbL4X90pDvPN8bS4uRtN7we5TvjtEYdrRuteY8plUioGtRodJYb6X5yaO9kK5ww.; SUB=_2A25zRJM8DeRhGeVL4lQY9SbMyT2IHXVQM4P0rDV8PUNbmtANLWP8kW9NTDJ5VziYCIZ6R-DnQTdZtNcF1x8IPAO8; SUHB=0laV2Qw4IF0jTu; _s_tentry=login.sina.com.cn; Apache=5115489028811.262.1581310831995; ULV=1581310832183:4:4:4:5115489028811.262.1581310831995:1581306707687";

    /**
     * 收集微博热搜信息
     * @param keyword
     * @param cookie
     * @param urlTemplate
     * @return
     */
    public void collectTopSearchBlogInfo(String keyword, String cookie, String urlTemplate) {

        CloseableHttpClient client = HttpClients.createDefault();
        for (int i = 1; i <= 50; i++) {
            System.out.println(StrUtil.format("第{}页",i));

            //拼接查询url
            String searchUrl = StrUtil.format(urlTemplate, keyword, i);
            HttpGet httpGet = new HttpGet(searchUrl);
            httpGet.setHeader("cookie",cookie);
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
                //System.out.println(result);
                List<TopSearchBlog> topSearchBlogPageInfoList = this.getPageInfoFromResponse(result, keyword);
                for (TopSearchBlog topSearchBlog : topSearchBlogPageInfoList) {
                    if(topSearchRepository.findByBlogCode(topSearchBlog.getBlogCode()) == null){
                        topSearchRepository.save(topSearchBlog);
                    }
                }
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
     * 抓取微博热搜实时信息
     * @param keyword
     * @param cookie
     */
    public void collectRealtimeTopSearchBlog(String keyword, String cookie){

        String topSearchRealtimeUrlTemplate = "https://s.weibo.com/realtime?q=%23{}%23&rd=realtime&tw=realtime&Refer=weibo_realtime&page={}";
        this.collectTopSearchBlogInfo(keyword,cookie,topSearchRealtimeUrlTemplate);

    }

    /**
     * 解析页面提取数据
     * @param result 页面爬取结果
     * @return
     */
    private List<TopSearchBlog> getPageInfoFromResponse(String result, String keyword) {

        Document document = Jsoup.parse(result);
        Elements elements = document.select("#pl_feedlist_index > div:nth-child(1) > * ");
        int length = elements.toArray().length; //元素列表长度
        List<TopSearchBlog> pageBlogList = new ArrayList<>();


        for (int i = 1; i <= length; i++) {

            TopSearchBlog blog = new TopSearchBlog();
            //获取信息发布人微博名称
            String nickNameCssPath = StrUtil.format("div:nth-child({}) > div > div.card-feed > div.content > div.info > div:nth-child(2) > a.name", i);
            String bloggerName = elements.select(nickNameCssPath).text();

            //获取发布人空间地址
            String bloggerUrl = elements.select(nickNameCssPath).attr("href");

            //获取发布内容
            String articleCssPath = StrUtil.format("div:nth-child({}) > div > div.card-feed > div.content > p.txt",i);
            String content = elements.select(articleCssPath).text();

            //获取博客资源识别码
            String blogCodeCssPath = StrUtil.format("div:nth-child({})",i);
            String blogCode = elements.select(blogCodeCssPath).attr("mid");
            //System.out.println(blogCode);

            //获取发布内容中的话题标签
            String tagCssPath = StrUtil.format("div:nth-child({}) > div > div.card-feed > div.content > p.txt > a", i);
            Elements elementList = elements.select(tagCssPath);
            List<Topic> topics = new ArrayList<>();
            for (Element element : elementList) {
                if (element.hasAttr("href")){
                    Topic topic = new Topic();
                    String tagName = element.text();
                    String tagHref = element.attr("href");
                    topic.setTitle(tagName);
                    topic.setTopicUrl(tagHref);
                    topics.add(topic);
                }
            }

            //获取文章的转发数
            String transmitNumCssPath = StrUtil.format("div:nth-child({}) > div.card > div.card-act > ul > li:nth-child(2) > a",i);
            String transmitNumResult = elements.select(transmitNumCssPath).text();
            //System.out.println(transmitNumResult.length());
            Integer transmitNum = 0;
            if(!transmitNumResult.isEmpty() && transmitNumResult.length() > 3){
                transmitNum = Integer.valueOf(StrUtil.sub(transmitNumResult, 6, -1)); //从第五个字符开始截取数据
            }
            //System.out.println(transmitNum);

            //获取文章评论数
            String commentNumCssPath = StrUtil.format("div:nth-child({}) > div.card > div.card-act > ul > li:nth-child(3) > a", i);
            String commentNumResult = elements.select(commentNumCssPath).text();
            Integer commentNum = 0;
            if(!commentNumResult.isEmpty() && commentNumResult.length() > 3){
                commentNum = Integer.valueOf(StrUtil.sub(commentNumResult, 6, -1));
            }
            //System.out.println(commentNum);

            //获取点赞数
            String likeNumCssPath = StrUtil.format("div:nth-child({}) > div.card > div.card-act > ul > li:nth-child(4) > a",i);
            String likeNumResult = elements.select(likeNumCssPath).text();
            Integer likeNum = 0;
            if(!likeNumResult.isEmpty()){
                likeNum = Integer.valueOf(likeNumResult);
            }
            //System.out.println(likeNum);

            //获取发布时间
            String timeCssPath = StrUtil.format("div:nth-child({}) > div.card > div.card-feed > div.content > p.from > a:nth-child(1)",i);
            String timeResult = elements.select(timeCssPath).text();
            Date time = transferWeiboTimeString(timeResult);
            //System.out.println(timeResult);
            //System.out.println("time:" + time);

            //获取发布源
            String originCssPath = StrUtil.format("div:nth-child({}) > div.card > div.card-feed > div.content > p.from > a:nth-child(2)", i);
            String origin = elements.select(originCssPath).text();
            //System.out.println(origin);

            blog.setBloggerName(bloggerName);
            blog.setBloggerUrl(bloggerUrl);
            blog.setContent(content);
            //blog.setTopicList(topics);
            blog.setBlogCode(blogCode);
            blog.setTransmitNum(transmitNum);
            blog.setCommentNum(commentNum);
            blog.setLikeNum(likeNum);
            blog.setPostTime(time);
            blog.setOrigin(origin);
            blog.setKeyword(keyword);


            pageBlogList.add(blog);

        }

        return pageBlogList;

    }

    /**
     * 微博格式日期转换为标准日期
     * @param timeResult
     * @return
     */
    public Date transferWeiboTimeString(String timeResult) {
        Date time = null;
        if(StrUtil.contains(timeResult, "秒")){
            Integer num = Integer.valueOf(StrUtil.subBefore(timeResult,"秒",true));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, -num);
            time = calendar.getTime();

        }else if(StrUtil.contains(timeResult, "分")){
            Integer num = Integer.valueOf(StrUtil.subBefore(timeResult,"分",true));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, -num);
            time = calendar.getTime();
        }else if(StrUtil.contains(timeResult, "今天")){
            String hhmm = StrUtil.subAfter(timeResult,"今天",true);
            String yyMMdd = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            String dateString = yyMMdd + " " + hhmm;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            try {
                time = simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("时间转换异常：" + e);
            }
        }else if(StrUtil.contains(timeResult,"月")){
            String year = new SimpleDateFormat("yyyy年").format(Calendar.getInstance().getTime());
            String dateString = year + timeResult;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
            try {
                time = simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("时间转换异常：" + e);
            }
        }
        /*else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            try {
                time = simpleDateFormat.parse(timeResult);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("时间转换异常：" + e);
            }

        }*/
        return time;
    }

}
