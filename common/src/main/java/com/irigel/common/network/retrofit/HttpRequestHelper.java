package com.irigel.common.network.retrofit;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.irigel.common.Constants;
import com.irigel.common.network.HttpConstants;
import com.irigel.common.network.interfaces.IHttpRequstCallBack;
import com.irigel.common.network.interfaces.IOTask;
import com.irigel.common.network.retrofit.service.ModeService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.json.JSONObject;

import static com.irigel.common.network.HttpConstants.MODEL_API_VERSION;

/**
 * Created by zhangqing on 5/21/21.
 */
public class HttpRequestHelper {

    /**
     * 获取首页模板数据
     * @param callBack
     * @param appId
     * @param <T>
     * @return
     */
//    public static <T> Disposable getModeData(IHttpRequstCallBack<T> callBack,String appId){
//        Flowable<ModelBean>  flowable = HttpRequestManager.getInstance().createService(ModeService.class,HttpConstants.TAG_MODE_HOST)
//               .getData(MODEL_API_VERSION,appId);
//        if(flowable != null){
//            return HttpRequestManager.getInstance().sendRequestOnIOCallback(flowable,callBack);
//        }
//        return null;
//    }

    /**
     * 下载zip文件
     * @param url
     * @param callBack
     * @param ioTask
     * @param zipPath
     * @param zipFileName
     * @return
     * @throws Exception
     */
//    public static Disposable loadZipFile(String url, IHttpRequstCallBack<Boolean> callBack, IOTask ioTask, String zipPath, String zipFileName) throws Exception{
//        Flowable<ResponseBody>  flowable = HttpRequestManager.getInstance().createService(ModeService.class,HttpConstants.TAG_MODE_ZIP_HOST)
//                .downloadZipFile(url);
//
//        if(flowable != null){
//            return HttpRequestManager.getInstance().sendLoadFileRequestOnUiCallBack(flowable,callBack,ioTask,zipPath,zipFileName);
//        }
//        return null;
//    }

    /**
     * 上传文件,获取token_id
     * @param file 原始图片
     * @param processName 任意取值
     *  @param processType 1为全图动漫，2为头部动漫，3位老照片上色
     * @return
     */
//    public static Disposable upLoadPicFile(File file, IHttpRequstCallBack<FlashBean> callBack, String processName, String processType,int factor){
//        if(file == null || TextUtils.isEmpty(processName)|| TextUtils.isEmpty(processType)){
//            return null;
//        }
//        Map<String, RequestBody> paramMap = new HashMap<>(4);
//        paramMap.put(HttpConstants.Param.PROCESS_NAME, getRequestTextBody(processName));
//        paramMap.put(HttpConstants.Param.PROCESS_TYPE, getRequestTextBody(processType));
//        paramMap.put(HttpConstants.Param.FILE + "\";filename=\"" +
//                file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
//        if(Constants.PROCESS_TYPE_COLOR_OLD_PIC.equals(processType)){
//            paramMap.put(HttpConstants.Param.PARAMS, getRequestTextBody(getColorPicParamsJson(factor)));
//        }
//
//        Flowable<FlashBean> flowable = HttpRequestManager.getInstance().createService(ModeService.class,HttpConstants.TAG_CARTOON_HOST)
//                .uploadFile(paramMap);
//        if(flowable != null){
//            return HttpRequestManager.getInstance().sendRequestOnUICallback(flowable,callBack);
//        }
//        return null;
//    }


    /**
     * 根据tokenid_获取卡通图片
     * @param tokenId
     * @param callBack
     * @return
     * @throws Exception
     */
//    public static Disposable loadCartoonPic(Context context,String tokenId, IHttpRequstCallBack<Bitmap> callBack){
//        Flowable<ResponseBody>  flowable = HttpRequestManager.getInstance().createService(ModeService.class,HttpConstants.TAG_CARTOON_HOST)
//                .downloadCartoonPic(tokenId);
//        if(flowable != null){
//            return HttpRequestManager.getInstance().sendCartoonRequestOnUICallBack(context,flowable,callBack);
//        }
//        return null;
//    }



    /**
     * 获取contenttype为文本型的requestbody对象
     * @param value 文本内容
     * @return
     */
    public static RequestBody getRequestTextBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    /**
     *
     * @param factor
     * @return
     */
    public static String getColorPicParamsJson(int factor) {
        try {
            JSONObject rootJson = new JSONObject();
        //    rootJson.put("weight_path","https://paddlegan.bj.bcebos.com/applications/DeOldfy_stable.pdparams");
            rootJson.put("artistic",true);
            rootJson.put("render_factor",factor);
            return rootJson.toString();
        }catch (Exception e){
             e.printStackTrace();
        }
        return  "";
    }

//
//    public static MultipartBody fileToMultipartBody(File file, RequestBody requestBody) {
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("fileName", file.getName());
//        jsonObject.addProperty("fileSha", Utils.getFileSha1(file));
//        jsonObject.addProperty("appId", "test0002");
//
//        builder.addFormDataPart("file", file.getName(), requestBody);
//
//        builder.addFormDataPart("params", jsonObject.toString());
//        builder.setType(MultipartBody.FORM);
//        return builder.build();
//    }

}
