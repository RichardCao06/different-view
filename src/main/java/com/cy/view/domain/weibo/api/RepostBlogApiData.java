package com.cy.view.domain.weibo.api;

import com.cy.view.domain.weibo.api.BlogUserApiData;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *
 * @description: 微博api返回的转发信息
 * @author CaoYong
 * @date: 2020/2/12
 * @version 1.0
 */
@Data
@Entity
@Table(name = "blog_repost_api_data_tb")
public class RepostBlogApiData {

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 发布时间
     */
    private Date createdAt;

    /**
     * 发布源微博id
     */
    private String originId;

    /**
     * 微博id
     */
    private String blogId;

    /**
     * 微博mid
     */
    private String mid;

    /**
     * 微博内容
     */
    @Column(columnDefinition = "TEXT")
    private String text;

    /**
     * 微博发布源
     */
    private String source;

    /**
     * 是否收藏源博文
     */
    private Boolean favorited;

    /**
     * 图片类型
     */
    private String picTypes;

    /**
     * 是否付费
     */
    private Boolean isPaid;

    /**
     * vip类型
     */
    private Integer mblog_vip_type;

    /**
     * 微博用户
     */
    /*@ManyToOne
    @JoinColumn(name = "repost_user_id", referencedColumnName = "id")
    private BlogUserApiData userData;*/

    /**
     * 微博用户id
     */
    private Long userId;

   /* *//**
     * 源微博信息
     *//*
    @ManyToOne
    @JoinColumn(name = "origin_blog_id", referencedColumnName = "blog_id")
    private BlogStatus blogStatus;*/

    /**
     * 转发数
     */
    private Integer reposts_count;

    /**
     * 评论数
     */
    private Integer comments_count;

    /**
     * 关注数
     */
    private Integer attitudes_count;

    /**
     * 点赞数
     */
    private Integer pending_approval_count;

    /**
     * 是否为长文章
     */
    private Boolean isLongText;

    /**
     * 其余api接口返回的部分字段
     */
    /*private int reward_exhibition_type;
    private int hide_flag;
    private int mblogtype;
    private int more_info_type;
    private int content_auth;
    private int safe_tags;
    private int repost_type;
    private int pic_num;
    private String raw_text;
    private String bid;
    private String cardid;
    private List<String> pic_ids;
    private List<String> darwin_tags;*/



}
