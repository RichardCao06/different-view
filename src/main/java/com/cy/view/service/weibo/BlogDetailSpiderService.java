package com.cy.view.service.weibo;

import cn.hutool.core.util.StrUtil;
import cn.wanghaomiao.seimi.annotation.Crawler;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cy.view.domain.weibo.api.BlogUserApiData;
import com.cy.view.domain.weibo.api.RepostBlogApiData;
import com.cy.view.domain.weibo.json.RepostJson;
import com.cy.view.repository.weibo.BlogUserApiDataRepository;
import com.cy.view.repository.weibo.RepostBlogApiDataRepository;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description: 抓取微博详情信息
 * @author CaoYong
 * @date: 2020/2/11
 * @version 1.0
 */

@Service
public class BlogDetailSpiderService {

    @Autowired
    RepostBlogApiDataRepository repostBlogApiDataRepository;

    @Autowired
    BlogUserApiDataRepository blogUserApiDataRepository;


    private static final String COOKIE = "_T_WM=26231747755; WEIBOCN_FROM=1110006030; ALF=1583999287; SCF=Ao9rVGiEihHQrRMGvH21bdfUjbL4X90pDvPN8bS4uRtNNwnBVX2L6FfSx81RYlSodCCG_C1YVwKjzqD8L08Obuo.; SUB=_2A25zRiwgDeRhGeVH6FsY-CrOyD6IHXVQyLRorDV6PUJbktANLXbakW1NT0MNBVoQZq0kgEz0P61Ms7gBsLvc1jua; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WF2PLZ_h6UcscUlfVWlAnLC5JpX5KzhUgL.Foe4e0.41hBEe0z2dJLoIEjLxK-LBo2LBo2LxK.LBKeL12-LxKBLBo.L12zLxK-LBozL1-8oqgv4dc4r; SUHB=0LcSnQqOvcb5V2; SSOLoginState=1581407344; MLOGIN=1; XSRF-TOKEN=142335; M_WEIBOCN_PARAMS=oid%3D4469438344421081%26luicode%3D20000061%26lfid%3D4469438344421081%26uicode%3D20000061%26fid%3D4469438344421081";

    private static final String COOKIE1 = "_T_WM=26231747755; XSRF-TOKEN=a9ca6a; WEIBOCN_FROM=1110006030; SCF=Ao9rVGiEihHQrRMGvH21bdfUjbL4X90pDvPN8bS4uRtNqAXG5t-xIhmOKI_0nPoU_r4xRm9Ug2kaH8nL0pTkMCU.; SUB=_2A25zR9pCDeRhGeFN41UV8C_JzjyIHXVQy-YKrDV6PUJbktANLUHBkW1NQ9ceiH4rlL8Eb9K1ZOb_5pi142sAEDWG; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhOpU9C4fu6MTSHkd_3pTN.5JpX5KzhUgL.FoM01hMXeh2fSK52dJLoIEXLxK.L1-zLBKnLxK.L1-eL1KeLxKnLBKeLBoeLxK.L1-BL1KqLxK.L1-zL1h2t; SUHB=0C1C5RhX95S0Gw; SSOLoginState=1581492754; MLOGIN=1; M_WEIBOCN_PARAMS=luicode%3D10000011%26lfid%3D102803%26uicode%3D20000174";

    private static final String COOKIE2 = "_T_WM=26231747755; XSRF-TOKEN=4b41ca; WEIBOCN_FROM=1110006030; SCF=Ao9rVGiEihHQrRMGvH21bdfUjbL4X90pDvPN8bS4uRtNVFe-AZFp4EFQwLAmbI_DYaByRNE1x7RZyLhm_AWk164.; SUB=_2A25zR9xNDeRhGeBK4lcZ8SvIyjiIHXVQy-QFrDV6PUJbktANLU7jkW1NR3qMwy1ejtqjvAF3Um-XrB2YQqIIkzPc; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFE8823VBN1nzromVGvy_6r5JpX5KzhUgL.FoqX1K-ReK-XeKB2dJLoIpSDdJSE9gxDqJ7cSh.f1h2fSh2X; SUHB=0idrgqdTNz_QGu; SSOLoginState=1581493277; MLOGIN=1; M_WEIBOCN_PARAMS=luicode%3D10000011%26lfid%3D102803%26uicode%3D20000174";
    /**
     * 抓取微博转发详情信息（
     * @param blogCode
     */
    public void collectBlogRepostDetail(String blogCode, String Cookie){

        //用返回结果的长度判断是否存在数据
        int length = 101;
        int i = 1;
        while (length > 100){

            CloseableHttpClient client = HttpClients.createDefault();
            String searchUtlTemplate = "https://m.weibo.cn/api/statuses/repostTimeline?id={}&page={}";
            String searchUrl = StrUtil.format(searchUtlTemplate,blogCode, i);
            HttpGet httpGet = new HttpGet(searchUrl);
            httpGet.setHeader("cookie",COOKIE1);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
            String result = null;

            try {
                CloseableHttpResponse response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("连接异常：" + e);
            }

            //储存数据
            if (result != null && result.length() > 100){
                List<RepostBlogApiData> repostBlogApiDataList = getRepostBlogDataFromResponse(result);
                for (RepostBlogApiData repostBlogApiData : repostBlogApiDataList) {
                    if (repostBlogApiData != null && repostBlogApiDataRepository.findByBlogId(repostBlogApiData.getBlogId()) == null){
                        repostBlogApiDataRepository.save(repostBlogApiData);
                    }
                }
                List<BlogUserApiData> blogUserDataList = getBlogUserDataFromResponse(result);
                for (BlogUserApiData blogUserApiData : blogUserDataList) {
                    if (blogUserApiData != null && blogUserApiDataRepository.findByUserId(blogUserApiData.getUserId()) == null){
                        blogUserApiDataRepository.save(blogUserApiData);
                    }
                }
                repostBlogApiDataRepository.saveAll(repostBlogApiDataList);
                blogUserApiDataRepository.saveAll(blogUserDataList);
                System.out.println(result);
            }else if(result.length() < 100){
                System.out.println(result);
                collectBlogRepostDetail(blogCode,COOKIE2);
                System.out.println("切换cookie：" + COOKIE2);
            }
            System.out.println("第" + i + "页");
            System.out.println("httpGet.getURI" + httpGet.getURI());
            System.out.println("httpGet.getAllHeaders:" + httpGet.getAllHeaders());
            length = result.length();
            i++;

            //休眠策略
            try {
                Thread.sleep(3000 * (long)Math.random());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        /*CloseableHttpClient client = HttpClients.createDefault();
        String searchUtlTemplate = "https://m.weibo.cn/api/statuses/repostTimeline?id={}&page={}";
        String searchUrl = StrUtil.format(searchUtlTemplate,blogCode, 1);
        HttpGet httpGet = new HttpGet(searchUrl);
        httpGet.setHeader("cookie",COOKIE);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        String result = null;

        try {
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("连接异常：" + e);
        }

        //储存数据
        if (result != null){
            List<RepostBlogApiData> repostBlogApiDataList = getRepostBlogDataFromResponse(result);
            List<BlogUserApiData> blogUserDataList = getBlogUserDataFromResponse(result);
            repostBlogApiDataRepository.saveAll(repostBlogApiDataList);
            blogUserApiDataRepository.saveAll(blogUserDataList);
            System.out.println(result);

        }*/




    }

    /**
     * 从爬取的页面中提取转发信息
     * @param result
     * @return
     */
    private List<RepostBlogApiData> getRepostBlogDataFromResponse(String result) {
        //将抓取的json字符串转换为Json对象
        RepostJson repostJson = JSONObject.parseObject(result, new TypeReference<RepostJson>(){});
        RepostJson.DataBeanX data = repostJson.getData();
        List<RepostBlogApiData> repostBlogApiDataList = new ArrayList<>();
        //获取数据集合
        List<RepostJson.DataBeanX.DataBean> dataBeans = data.getData();
        for (RepostJson.DataBeanX.DataBean bean : dataBeans) {
            RepostBlogApiData repostBean = transformJsonBeanToRepostDataBean(bean);
            repostBlogApiDataList.add(repostBean);
        }
        return repostBlogApiDataList;

    }

    /**
     * 从抓取的页面获取微博用户信息
     * @param result
     * @return
     */
    private List<BlogUserApiData> getBlogUserDataFromResponse(String result){
        RepostJson repostJson = JSONObject.parseObject(result, new TypeReference<RepostJson>(){});
        RepostJson.DataBeanX data = repostJson.getData();
        List<BlogUserApiData> blogUserApiDataList = new ArrayList<>();
        //获取数据集合
        List<RepostJson.DataBeanX.DataBean> dataBeans = data.getData();
        for (RepostJson.DataBeanX.DataBean bean : dataBeans) {
            BlogUserApiData blogUserApiData = transformUserJsonBeanToUserDataBean(bean.getUser());
            blogUserApiDataList.add(blogUserApiData);
        }
        return blogUserApiDataList;

    }

    /**
     * 将返回的jsonbean转换为需要的repostDatabean
     * @param bean
     * @return
     */
    private RepostBlogApiData transformJsonBeanToRepostDataBean(RepostJson.DataBeanX.DataBean bean) {
        RepostBlogApiData repostBlogApiData = new RepostBlogApiData();
        repostBlogApiData.setBlogId(bean.getId());
        repostBlogApiData.setCreatedAt(transferWeiboTimeString(bean.getCreated_at()));
        repostBlogApiData.setOriginId(bean.getRetweeted_status().getId());
        repostBlogApiData.setMid(bean.getMid());
        repostBlogApiData.setText(bean.getText());
        repostBlogApiData.setSource(bean.getSource());
        repostBlogApiData.setPicTypes(bean.getPic_types());
        //repostBlogApiData.setPaid(bean.isIs_paid());
        repostBlogApiData.setIsPaid(bean.isIs_paid());
        repostBlogApiData.setFavorited(bean.isFavorited());
        repostBlogApiData.setMblog_vip_type(bean.getMblog_vip_type());
        //repostBlogApiData.setUserData(transformUserJsonBeanToUserDataBean(bean.getUser()));
        repostBlogApiData.setUserId(bean.getUser().getId());
        repostBlogApiData.setReposts_count(bean.getReposts_count());
        repostBlogApiData.setComments_count(bean.getComments_count());
        repostBlogApiData.setPending_approval_count(bean.getPending_approval_count());
        repostBlogApiData.setAttitudes_count(bean.getAttitudes_count());
        //repostBlogApiData.setLongText(bean.isIsLongText());
        repostBlogApiData.setIsLongText(bean.isIsLongText());
        return repostBlogApiData;
    }

    /**
     * 将返回的jsonDataBean转换为需要的repostDataBean
     * @param user
     * @return
     */
    private BlogUserApiData transformUserJsonBeanToUserDataBean(RepostJson.DataBeanX.DataBean.UserBean user) {
        BlogUserApiData blogUserApiData = new BlogUserApiData();
        blogUserApiData.setUserId(user.getId());
        blogUserApiData.setScreen_name(user.getScreen_name());
        blogUserApiData.setProfile_image_url(user.getProfile_image_url());
        blogUserApiData.setProfile_url(user.getProfile_url());
        blogUserApiData.setStatuses_count(user.getStatuses_count());
        blogUserApiData.setVerified(user.isVerified());
        blogUserApiData.setVerified_type(user.getVerified_type());
        blogUserApiData.setClose_blue_v(user.isClose_blue_v());
        blogUserApiData.setDescription(user.getDescription());
        blogUserApiData.setGender(user.getGender());
        blogUserApiData.setMbtype(user.getMbtype());
        blogUserApiData.setUrank(user.getUrank());
        blogUserApiData.setMbrank(user.getMbrank());
        blogUserApiData.setFollow_me(user.isFollow_me());
        blogUserApiData.setFollowing(user.isFollowing());
        blogUserApiData.setLiked(user.isLike());
        blogUserApiData.setLike_me(user.isLike_me());
        blogUserApiData.setCover_image_phone(user.getCover_image_phone());
        blogUserApiData.setAvatar_hd(user.getAvatar_hd());
        blogUserApiData.setFollow_count(user.getFollow_count());
        blogUserApiData.setFollowers_count(user.getFollowers_count());

        return blogUserApiData;

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
        }else if(StrUtil.contains(timeResult,"刚刚")){
            time = Calendar.getInstance().getTime();
        }
        else if(StrUtil.contains(timeResult, "小时")){
            Integer num = Integer.valueOf(StrUtil.subBefore(timeResult,"小时",true));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, -num);
            time = calendar.getTime();
        }
        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            try {
                time = simpleDateFormat.parse(timeResult);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("时间转换异常：" + e);
            }
        }
        return time;
    }
}
