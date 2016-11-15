package com.bawan.cheapest.Service.External;

import com.bawan.cheapest.Constants.Constants;
import com.bawan.cheapest.bean.ChepestItemInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * Created by wangzhen on 2016/11/15.
 */
@Service
public class TMService extends BasicService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TMService.class);

    @Override
    public ChepestItemInfo getCheapest(String webSit, String keyword, String itemUrl) {
        String response = Constants.NIL_RESPONSE;
        try {
            ChepestItemInfo testProductInfo = new ChepestItemInfo();
            URL url = new URL(itemUrl.replace("$keyword", keyword));
            Document doc = Jsoup.parse(url, 100000);
            Elements elements = doc.getElementsByClass("product");
            if (elements == null || elements.size() == 0) {
                LOGGER.info("获取商品信息失败");
                return null;
            }
            Element checpestElement = elements.get(0);
            if (checpestElement == null) {
                LOGGER.info("获取商品信息失败,没有子商品");
                return null;
            }
            Elements elementHref = checpestElement.getElementsByAttribute("href");
            String href = elementHref.attr("href");
            Elements elementPicURL = checpestElement.getElementsByAttribute("src");
            String picUrl = elementPicURL.attr("src");
            Elements elementPrices = checpestElement.getElementsByClass("productPrice");
            elementPrices = elementPrices.get(0).getElementsByAttribute("title");
            String data_price = elementPrices.attr("title");
            testProductInfo.setHref(href);
            testProductInfo.setData_price(data_price);
            testProductInfo.setSrc(picUrl);
            testProductInfo.setFromWebSite(webSit);
            return testProductInfo;
        } catch (Exception e) {
            LOGGER.error("获取商品信息失败", e);
        }
        return null;
    }
}
