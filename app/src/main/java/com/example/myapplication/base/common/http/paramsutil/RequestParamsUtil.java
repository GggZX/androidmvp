package com.example.myapplication.base.common.http.paramsutil;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.base.BaseEnity;
import com.example.myapplication.utils.CommonUtil;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RequestParamsUtil {

    /**
     * Body类型的入参，辅助工具
     *
     * @param json
     * @return
     */

    //1.json-->RequestBody
    public static RequestBody jsonToReqBody(String json) {
        RequestBody body = null;
        try {
            if (CommonUtil.isEmpty(json)) {
                return null;
            }
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        } catch (Exception e) {
            return null;
        }

        return body;
    }

    // 2.map集合--》json类型的requestBody
    public static RequestBody MapToReqBody(Map<String, Object> map) {
        RequestBody body = null;
        try {
            if (map == null || map.size() == 0) {
                return null;
            }
            Gson gson=new Gson();
            String requestData = new Gson().toJson(map);
            body= RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestData);
        } catch (Exception e) {

        }

        return body;
    }

    //3.实体类===》json类RequestBody
    public static RequestBody BeanToReqBody(BaseEnity baseEnity) {
        String jsonString = null;
        try {
            if (baseEnity == null) {
                return null;
            }
            jsonString = JSON.toJSONString(baseEnity);
        } catch (Exception e) {
            return null;
        }

        return jsonToReqBody(jsonString);
    }


    /**
     * 文件上传所需的RequestBody转化
     */
    //str==>文件@Part:RequestBody
    public static RequestBody FileStrToPartBody(String req) {

        RequestBody fileBody = null;
        try {
            if (req == null) {
                fileBody = null;
            }
            fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), req);

        } catch (Exception e) {
            return null;
        }

        return fileBody;
    }


    /**
     * File===>MultipartBody.Part:part
     *
     * @param name
     * @param file
     * @return
     */
    //单个
    public static MultipartBody.Part FileToMultiPart(String name, File file) {
        MultipartBody.Part part = null;
        try {
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            part = MultipartBody.Part
                    .createFormData(CommonUtil.getString(name), file.getName(), fileBody);
        } catch (Exception e) {

        }
        if (file == null) {
            return null;
        }

        return part;

    }

    //多个filePart
    public static List<MultipartBody.Part> FileToMultiPartList(String name, List<File> fileList) {

        try {
            List<MultipartBody.Part> partList = new ArrayList<>();
            MultipartBody.Part imagePart;
            if (fileList != null && fileList.size() > 0) {
                for (int i = 0; i < fileList.size(); i++) {
                    imagePart = FileToMultiPart(name, fileList.get(i));
                    partList.add(imagePart);
                }
                return partList;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }


}
