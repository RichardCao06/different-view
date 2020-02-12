package com.cy.view.domain.weibo.api;

import com.cy.view.domain.weibo.json.RepostJson;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @description: 微博api返回的微博状态信息
 * @author CaoYong
 * @date: 2020/2/12
 * @version 1.0
 */
@Data
public class BlogStatusApiData {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private String created_at;

    /**
     * 微博id
     */
    private String blogId;
    private String mid;
    private boolean can_edit;
    private int version;
    private int show_additional_indication;
    private String text;
    private int textLength;
    private String source;
    private boolean favorited;
    private String pic_types;
    private int pic_flag;
    private String thumbnail_pic;
    private String bmiddle_pic;
    private String original_pic;
    private boolean is_paid;
    private int mblog_vip_type;
    private RepostJson.DataBeanX.DataBean.RetweetedStatusBean.UserBeanX user;
    private String picStatus;
    private int reposts_count;
    private int comments_count;
    private int attitudes_count;
    private int pending_approval_count;
    private boolean isLongText;
    private int reward_exhibition_type;
    private int hide_flag;
    private int mblogtype;
    private int more_info_type;
    private int content_auth;
    private int safe_tags;
    private int pic_num;
    private RepostJson.DataBeanX.DataBean.RetweetedStatusBean.PageInfoBean page_info;
    private String bid;
    private List<String> pic_ids;
    private List<RepostJson.DataBeanX.DataBean.RetweetedStatusBean.PicFocusPointBean> pic_focus_point;
    private List<?> pic_rectangle_object;
    private List<RepostJson.DataBeanX.DataBean.RetweetedStatusBean.DarwinTagsBean> darwin_tags;
    private List<RepostJson.DataBeanX.DataBean.RetweetedStatusBean.PicsBean> pics;
}
