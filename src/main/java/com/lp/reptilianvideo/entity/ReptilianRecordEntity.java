package com.lp.reptilianvideo.entity;

import javax.persistence.*;

@Entity
@Table(name = "stg_reptilian_record")
public class ReptilianRecordEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "root_url")
    private String rootUrl;

    public ReptilianRecordEntity()
    {
        super();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getRootUrl()
    {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl)
    {
        this.rootUrl = rootUrl;
    }
}
