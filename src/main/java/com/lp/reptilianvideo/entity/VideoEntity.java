package com.lp.reptilianvideo.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stg_reptilian_video")
@EntityListeners(AuditingEntityListener.class)
public class VideoEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "root_url",length=512)
    private String rootUrl;

    @Column(name = "index_url",length=512)
    private String indexUrl;

    @Column(name = "url")
    private String url;

    @Column(name = "video_index")
    private int index;

    @Column(name = "picture_url")
    private String picUrl;

    @CreatedDate
    @Column(name = "insert_time")
    private Date insertTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "state")
    private String state;

    public VideoEntity()
    {
        super();
    }

    public VideoEntity(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getRootUrl()
    {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl)
    {
        this.rootUrl = rootUrl;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getPicUrl()
    {
        return picUrl;
    }

    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Date getInsertTime()
    {
        return insertTime;
    }

    public void setInsertTime(Date insertTime)
    {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getIndexUrl()
    {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl)
    {
        this.indexUrl = indexUrl;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }
}
