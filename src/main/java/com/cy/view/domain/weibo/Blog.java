package com.cy.view.domain.weibo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description: 微博信息实体
 * @author CaoYong
 * @date: 2020/2/10
 * @version 1.0
 */
@Data
public class Blog {

    /**
     * 主键
     */
    private Long id;

    /**
     * 博主名称
     */
    private String bloggerName;

    /**
     * 博主个人空间地址
     */
    private String bloggerUrl;

    /**
     * 微博资源识别码
     */
    private String blogCode;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 话题信息
     */
    private List<Topic> topicList;

    /**
     * 发布时间
     */
    private Date postTime;

    /**
     * 发布源
     */
    private String origin;

    /**
     * 转发数
     */
    private Integer transmitNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 点赞数
     */
    private Integer likeNum;


    /**
     * 热搜关键字
     */
    private String keyword;








}
