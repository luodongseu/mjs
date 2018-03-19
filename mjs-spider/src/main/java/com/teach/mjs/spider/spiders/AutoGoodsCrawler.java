package com.teach.mjs.spider.spiders;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.teach.mjs.spider.model.Goods;
import com.teach.mjs.spider.repository.GoodsRepository;
import org.apache.commons.dbcp.BasicDataSource;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * 商品爬虫类，修改visit函数和构造函数，使其自动爬取多个商品信息
 *
 * @author hu
 */
@Component
public class AutoGoodsCrawler extends BreadthCrawler {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * @param1 crawlPath is the path of the directory which maintains
     * information of this crawler
     * @param2 if autoParse is true,BreadthCrawler will auto extract
     * links which match regex rules from pag
     */
    public AutoGoodsCrawler() {
        super("crawl", true);
        /*start page*/
        this.addSeed("http://item.jd.com/3262461.html", "item");

        setThreads(1);
        getConf().setTopN(1);
        setResumable(false);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.url();
        /*if page is news page*/
        if (page.matchType("item")) {
            Elements goodsInfo = page.select("div[id=detail]");
            // 图文详情
            String detail = goodsInfo.html();
            // 商品名
            String name = page.selectText("div[class='sku-name']");
//            System.out.println("Details:\n" + detail);
            System.out.println("Name:\n" + name);

            Goods goods = new Goods();
            goods.setGoodsId(UUID.randomUUID().toString());
            goods.setGoodsCode("123456");
            goods.setGoodsName(name);
            goods.setGoodsIntro(detail);

            // 存到数据库
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl(dataSourceUrl);
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(
                        "insert into t_goods(`goods_id`,`goods_name`,`goods_code`,`goods_intro`) values(?, ?, ?, ?)");
                preparedStatement.setString(1, goods.getGoodsId());
                preparedStatement.setString(2, goods.getGoodsName());
                preparedStatement.setBlob(3, new SerialBlob(goods.getGoodsCode().getBytes()));
                preparedStatement.setBlob(4, new SerialBlob(goods.getGoodsIntro().getBytes()));
                preparedStatement.execute();
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != preparedStatement) {
                        preparedStatement.close();
                    }
                    if (null != connection) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        next.clear();
        stop();
    }
}