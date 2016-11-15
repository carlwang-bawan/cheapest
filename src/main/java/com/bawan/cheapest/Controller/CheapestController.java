package com.bawan.cheapest.Controller;

import com.bawan.cheapest.Service.CheapestService;
import com.bawan.cheapest.bean.ChepestItemInfo;
import com.bawan.cheapest.bean.ExternalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangzhen on 2016/11/11.
 */
@Controller
@RequestMapping(value = "cheap")
public class CheapestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheapestController.class);


    @Autowired
    CheapestService cheapestService;

    @RequestMapping(value = "getUserItem")
    @ResponseBody
    public ExternalResponse getUserItem(String userId){
        ExternalResponse externalResponse = new ExternalResponse();
        List<ChepestItemInfo> resultList =  cheapestService.getAllUserItemList(userId);
        if (resultList == null){
            externalResponse.markFailed(-1,"查询失败");
        }
        externalResponse.markSuccess();
        externalResponse.getBody().put("result",resultList);
        return externalResponse;
    }

    @RequestMapping(value = "runALL")
    @ResponseBody
    public ExternalResponse runAll(){
        ExternalResponse externalResponse = new ExternalResponse();
        cheapestService.runGetAllChepest();
        externalResponse.markSuccess();
        return externalResponse;
    }

    @RequestMapping(value = "saveAllConfig")
    @ResponseBody
    public ExternalResponse saveAllConfig(){
        ExternalResponse externalResponse = new ExternalResponse();
        cheapestService.saveAllDataToFile();
        externalResponse.markSuccess();
        return externalResponse;
    }


    @RequestMapping(value = "loadAllConfig")
    @ResponseBody
    public ExternalResponse loadAllConfig(){
        ExternalResponse externalResponse = new ExternalResponse();
        cheapestService.loadAllDataToFile();
        externalResponse.markSuccess();
        return externalResponse;
    }

}
