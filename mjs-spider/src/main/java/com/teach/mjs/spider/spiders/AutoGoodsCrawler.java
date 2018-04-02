package com.teach.mjs.spider.spiders;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.github.jarlakxen.embedphantomjs.PhantomJSReference;
import com.github.jarlakxen.embedphantomjs.executor.PhantomJSConsoleExecutor;
import com.teach.mjs.spider.model.Goods;
import org.apache.commons.dbcp.BasicDataSource;
import org.jsoup.select.Elements;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

/**
 * 商品爬虫类，修改visit函数和构造函数，使其自动爬取多个商品信息
 *
 * @author luodong
 */
public class AutoGoodsCrawler extends BreadthCrawler {

    private String dataSourceUrl;
    private String driverClassName;
    private String userName;
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

        loadProperties();
    }

    /**
     * 加载配置文件
     */
    private void loadProperties() {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getResourceAsStream("/application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        dataSourceUrl = properties.getProperty("spring.datasource.url");
        driverClassName = properties.getProperty("spring.datasource.driver-class-name");
        userName = properties.getProperty("spring.datasource.username");
        password = properties.getProperty("spring.datasource.password");
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

            saveGoodsToDB(goods);
        }
    }

    /**
     * 将商品信息存到数据库
     *
     * @param goods
     */
    private void saveGoodsToDB(Goods goods) {
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


    /**
     * 启动爬虫的main函数
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        AutoGoodsCrawler crawler = new AutoGoodsCrawler();
//        crawler.start(1);

        PhantomJSConsoleExecutor ex = new PhantomJSConsoleExecutor(PhantomJSReference.create().build());
        ex.start();
        ex.execute("var page = require('webpage').create();\n" +
                "page.open('http://baidu.com', function(status) {\n" +
                "  console.log(\"Status: \" + status);\n" +
                "  if(status === \"success\") {\n" +
                "    page.render('example.png');\n" +
                "  }\n" +
                "  phantom.exit();\n" +
                "});");
        System.out.println(ex.execute("system.stdout.writeLine('TEST1');", "true")); // This prints "TEST1"
        System.out.println(ex.execute("system.stdout.writeLine('TEST2');", "true")); // This prints "TEST2"
        ex.destroy();
    }


}