package com.teach.mjs.spider.model;

import javax.persistence.*;

/**
 * 商品信息
 *
 * @author luodong
 */

@Entity
@Table(name = "t_goods")
public class Goods {

    /**
     * 商品ID 使用UUID.randomId().toString()生成
     */
    @Id
    private String goodsId;

    /**
     * 商品名称
     */
    @Column(nullable = false, length = 600)
    private String goodsName;

    /**
     * 商品二维码信息
     */
    @Lob
    private String goodsCode;

    /**
     * 商品介绍
     */
    @Lob
    private String goodsIntro;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
    }
}
