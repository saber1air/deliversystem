package com.deliver.util;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;

/**
 * Created by pdl on 2019/1/2.
 */
public class ShearImgUtil {
    public static void main(String[] args) {
        File file = new File("D:\\imgShear\\in\\scene");
        File[] sfile = file.listFiles();
        ToInterface2 toInterface = new ToInterface2();
        String path = "D:\\imgShear\\in\\scene\\";

        for (int i = 0; i < sfile.length; i++) {
            String pathName = path + sfile[i].getName();
            try {
                List<Map<String, Object>> mapList = toInterface.pythonMap(pathName);
                if(mapList!=null && mapList.size()>0){
                    for(Map map:mapList){
                        String photoName = UUID.randomUUID().toString().replace("-", "")+".png";
                        Base64Img.base64StrToImage(map.get("crop_img")
                                .toString(), "D:\\imgShear\\out\\" + photoName);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("结束！");

    }
}
