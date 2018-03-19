package com.teach.mjs.spider.controller;

import com.teach.mjs.spider.model.Goods;
import com.teach.mjs.spider.repository.GoodsRepository;
import com.teach.mjs.spider.spiders.AutoGoodsCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 商品基本信息Rest接口
 *
 * @author luodong
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    AutoGoodsCrawler goodsCrawler;

    /**
     * 初始化该控制器之前的操作
     */
    @PostConstruct()
    public void init() {
        try {
            /**
             * 启动爬虫， 自动向数据库中存入爬到的数据
             */
            goodsCrawler.start(1);
            goodsCrawler.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/list")
    public List<Goods> listGoods() {
        return goodsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Goods getOne(@PathVariable("id") String id) {
        return goodsRepository.findOne(id);
    }
}
