package com.teach.mjs.spider.repository;

import com.teach.mjs.spider.model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 操作商品表的接口
 *
 * 自带findAll() save(T)等接口
 * @author luodong
 */
public interface GoodsRepository extends JpaRepository<Goods, String> {
}
