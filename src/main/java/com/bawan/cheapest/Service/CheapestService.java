package com.bawan.cheapest.Service;

import com.bawan.cheapest.Service.External.BasicService;
import com.bawan.cheapest.Service.External.JdService;
import com.bawan.cheapest.Service.External.TMService;
import com.bawan.cheapest.Utils.FileUtils;
import com.bawan.cheapest.bean.ChepestItemInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangzhen on 2016/11/11.
 */
@Service
public class CheapestService{
    private static final Logger LOGGER = LoggerFactory.getLogger(CheapestService.class);
    public static volatile ConcurrentHashMap<String,String> webIdToUrl= new ConcurrentHashMap(1);
    public static volatile ConcurrentHashMap<String,List<String>> userSetItem = new ConcurrentHashMap<>(1);
    public static volatile ConcurrentHashMap<String,ChepestItemInfo> resultItemInfo = new ConcurrentHashMap<>(1);
    public static volatile List<String> allItemList =new ArrayList<>(1);
    public static HashMap<String,BasicService> idToServiceMap = new HashMap<>();
    public static volatile List<String> defaultItemList = new ArrayList<>();
    public static volatile boolean isRunningTask = false;
    String baseUrl = "e:/config/im-server/";
    @Autowired
    JdService jdService;
    @Autowired
    TMService tmService;

    @PostConstruct
    public void  init(){
        idToServiceMap.put("JD",jdService);
        idToServiceMap.put("TM",tmService);
        webIdToUrl.put("JD","http://search.jd.com/Search?keyword=$keyword&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&bs=1&psort=2&ev=exbrand_Apple%40&click=0");
        webIdToUrl.put("TM","https://list.tmall.com/search_product.htm?q=$keyword&sort=p");
        defaultItemList.add("iphone6s");
        allItemList.add("iphone6s");
    }


    List<String> getAllItemListByUser(String userId){
        List<String> resultList = userSetItem.get(userId);
        return resultList  != null ? resultList : defaultItemList;
    }

    List<ChepestItemInfo> getCheapestList(List<String> itemNameList){
        List<ChepestItemInfo> resultList = new ArrayList<>();
        for (String itemName : itemNameList){
            for (ConcurrentHashMap.Entry<String,String> websit : webIdToUrl.entrySet()){
                ChepestItemInfo chepestItemInfo = resultItemInfo.get(itemName + websit.getKey());
                if (chepestItemInfo !=null)
                    resultList.add(chepestItemInfo);
            }
        }
        return  resultList;
    }

    public List<ChepestItemInfo> getAllUserItemList(String userId){
        return getCheapestList(getAllItemListByUser(userId));
    }

    public synchronized void  runGetAllChepest(){
        if (isRunningTask)
            return;
        isRunningTask =true;
        for (ConcurrentHashMap.Entry<String,String> entry : webIdToUrl.entrySet()){
            for (String item : allItemList){
                BasicService basicService = idToServiceMap.get(entry.getKey());
                ChepestItemInfo chepestItemInfo = basicService.getCheapest(entry.getKey(), item, entry.getValue());
                if (chepestItemInfo == null){
                    //add 出错信息
                }
                resultItemInfo.put(item+entry.getKey(),chepestItemInfo);
            }
        }
        isRunningTask =false;
    }


    private void saveDataToFile(String filename,Object object){
        try  {
            File file = new File(filename +".tmp");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(object);
            out.close();
            FileUtils.deleteFile(filename);
            FileUtils.renameFile(filename +".tmp",filename);
        } catch (IOException e) {
            LOGGER.error("保存配置失败",e);
        }
    }

    public void saveAllDataToFile(){
        try  {
            saveDataToFile(baseUrl +"webIdToUrl",webIdToUrl);
            saveDataToFile(baseUrl +"userSetItem",userSetItem);
            saveDataToFile(baseUrl +"resultItemInfo",resultItemInfo);
            saveDataToFile(baseUrl +"allItemList",allItemList);
            saveDataToFile(baseUrl +"idToServiceMap",idToServiceMap);
            saveDataToFile(baseUrl +"defaultItemList",defaultItemList);
        } catch (Exception e) {
            LOGGER.error("保存配置失败",e);
        }
    }

    public void loadAllDataToFile(){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(baseUrl + "resultItemInfo"));
            resultItemInfo= (ConcurrentHashMap<String,ChepestItemInfo>)in.readObject();
            in.close();
         //   in = new ObjectInputStream(new FileInputStream(baseUrl + "webIdToUrl"));
         //   resultItemInfo= (ConcurrentHashMap<String,ChepestItemInfo>)in.readObject();
         //   in.close();
         //   in = new ObjectInputStream(new FileInputStream(baseUrl + "userSetItem"));
         //   resultItemInfo= (ConcurrentHashMap<String,ChepestItemInfo>)in.readObject();
         //   in.close();
         //   in = new ObjectInputStream(new FileInputStream(baseUrl + "allItemList"));
         //   resultItemInfo= (ConcurrentHashMap<String,ChepestItemInfo>)in.readObject();
         //   in.close();
         //   in = new ObjectInputStream(new FileInputStream(baseUrl + "idToServiceMap"));
         //   idToServiceMap= (ConcurrentHashMap<String,ChepestItemInfo>)in.readObject();
         //   in.close();
         //   in = new ObjectInputStream(new FileInputStream(baseUrl + "defaultItemList"));
         //   defaultItemList= (List<String>)in.readObject();
         //   in.close();

        } catch (Exception e) {
            LOGGER.error("读取配置失败",e);
        }
    }
}
