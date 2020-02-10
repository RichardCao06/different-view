package com.cy.view.domain.weibo;

import lombok.Data;

import java.util.List;

/**
 * @decription: 博主信息实体
 * @author CaoYong
 * @date: 2020/2/10
 * @version 1.0
 */
@Data
public class Blogger {

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
     * 所在地
     */
    private String place;

    /**
     * 毕业学校
     */
    private String school;

    /**
     * 公司
     */
    private String company;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 是否加V认证
     */
    private Boolean isAuthenticationV;

    /**
     * 所发博客
     */
    private List<Blog> blogList;

    /**
     * 关注列表
     */
    private List<Blogger> attentionList;

    /**
     * 粉丝列表
     */
    private List<Blogger> fansList;

    /**
     * 关注数
     */
    private Integer attentionNum;

    /**
     * 粉丝数
     */
    private Integer fansNum;

    /**
     * 博客数
     */
    private Integer blogNum;


}
