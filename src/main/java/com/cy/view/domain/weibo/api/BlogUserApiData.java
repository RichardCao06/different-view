package com.cy.view.domain.weibo.api;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 微博api返回的微博用户信息
 * @author CaoYong
 * @date: 2020/02/12
 * @version 1.0
 */
@Data
@Entity
@Table(name = "blog_user_api_data_tb")
public class BlogUserApiData {

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 微博用户id
     */
    private Long userId;

    /**
     * 微博昵称
     */
    private String screen_name;

    /**
     * 微博头像链接
     */
    private String profile_image_url;

    /**
     * 微博用户空间地址
     */
    private String profile_url;

    /**
     * 身份分数
     */
    private Integer statuses_count;

    /**
     * 是否认证
     */
    private Boolean verified;

    /**
     * 认证类型
     */
    private Integer verified_type;

    private Boolean close_blue_v;

    /**
     * 个人简介
     */
    private String description;

    /**
     * 性别
     */
    private String gender;

    /**
     * 其余api返回信息
     */
    private Integer mbtype;
    private Integer urank;
    private Integer mbrank;
    private Boolean follow_me;
    private Boolean following;
    private Integer followers_count;
    private Integer follow_count;
    private String cover_image_phone;
    private String avatar_hd;
    private Boolean liked;
    private Boolean like_me;

}
