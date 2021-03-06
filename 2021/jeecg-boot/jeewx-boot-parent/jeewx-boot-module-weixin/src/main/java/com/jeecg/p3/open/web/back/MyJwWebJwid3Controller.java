package com.jeecg.p3.open.web.back;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.p3.core.web.BaseController;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.third.JwThirdAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jeecg.p3.commonweixin.def.CommonWeixinProperties;
import com.jeecg.p3.commonweixin.entity.MyJwWebJwid;
import com.jeecg.p3.open.entity.WeixinOpenAccount;
import com.jeecg.p3.commonweixin.exception.CommonweixinException;
import com.jeecg.p3.open.service.WeixinOpenAccountService;
import com.jeecg.p3.commonweixin.util.AccessTokenUtil;
import com.jeecg.p3.commonweixin.util.Constants;
import com.jeecg.p3.commonweixin.util.ContextHolderUtils;
import com.jeecg.p3.redis.JedisPoolUtil;
import com.jeecg.p3.system.service.MyJwWebJwidService;
import com.jeecg.p3.weixinInterface.entity.WeixinAccount;

import net.sf.json.JSONObject;


 /**
 * ?????????????????????????????????????????????????????????
  * ??????????????????????????????????????????????????????????????????
 * @author pituo
 * @since???2015???12???21??? 16???33???45??? ????????? 
 * @version:1.0
 */
@Controller
@RequestMapping("/commonweixin/back/myJwWebJwid3")
public class MyJwWebJwid3Controller extends BaseController{
	private static final Logger LOG = LoggerFactory.getLogger(MyJwWebJwid3Controller.class);
  @Autowired
  private MyJwWebJwidService myJwWebJwidService;
  @Autowired
  private WeixinOpenAccountService weixinOpenAccountService;
  
/**
  * ????????????
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute MyJwWebJwid query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "commonweixin/back/myJwWebJwid-list3.vm";
		try {
			String systemUserid = request.getSession().getAttribute("system_userid").toString();
			if(StringUtils.isEmpty(systemUserid)){
				throw new CommonweixinException("????????????????????????");
			}
		 	//???????????????
			String jwid =  request.getSession().getAttribute("jwid").toString();
			MyJwWebJwid jwWebJwid = myJwWebJwidService.queryByCreateBy(systemUserid,jwid);
			velocityContext.put("jwidFlag","Y");
			if(jwWebJwid==null){
				//???????????????
				jwWebJwid=myJwWebJwidService.queryByJwid(jwid);
				velocityContext.put("jwidFlag","N");
			}
			if(jwid.equals(CommonWeixinProperties.defaultJwid)){
				velocityContext.put("isDefaultJwid","1");
			}
			velocityContext.put("jwWebJwid",jwWebJwid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * ??????
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void jwWebJwidDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletRequest request,HttpServletResponse response)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "commonweixin/back/myJwWebJwid-detail.vm";
		MyJwWebJwid myJwWebJwid = myJwWebJwidService.queryById(id);
		velocityContext.put("myJwWebJwid",myJwWebJwid);
		 String jwid =  request.getSession().getAttribute("jwid").toString();
		 velocityContext.put("jwid",jwid);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * ?????????????????????
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "commonweixin/back/myJwWebJwid-add.vm";
	 String jwid = request.getSession().getAttribute("jwid").toString();
	 velocityContext.put("jwid",jwid);
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * ????????????
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute MyJwWebJwid myJwWebJwid,HttpServletRequest request){
	AjaxJson j = new AjaxJson();
	try {
		myJwWebJwid.setAuthType("1");
		Map<String, Object> map = AccessTokenUtil.getAccseeToken(myJwWebJwid.getWeixinAppId(), myJwWebJwid.getWeixinAppSecret());
		if(map.get("accessToken") != null){
			myJwWebJwid.setAccessToken(map.get("accessToken").toString());
			myJwWebJwid.setTokenGetTime((Date) map.get("accessTokenTime"));
			myJwWebJwid.setApiTicket(map.get("apiTicket").toString());
			myJwWebJwid.setApiTicketTime((Date) map.get("apiTicketTime"));
			myJwWebJwid.setJsApiTicket(map.get("jsApiTicket").toString());
			myJwWebJwid.setJsApiTicketTime((Date) map.get("jsApiTicketTime"));
			j.setMsg("?????????????????????");
		}else{
			j.setMsg("AppId??? AppSecret??????????????????????????? ");
			j.setSuccess(true);
		}
		myJwWebJwid.setCreateBy((String)request.getSession().getAttribute(Constants.SYSTEM_USERID));
		MyJwWebJwid myJwWebJwid2 = myJwWebJwidService.queryByJwid(myJwWebJwid.getJwid());
		if(myJwWebJwid2!=null){
			j.setSuccess(false);
			j.setMsg("???????????????????????????!");
			return j;
		}
		myJwWebJwidService.doAdd(myJwWebJwid);
	} catch (Exception e) {
		e.printStackTrace();
		log.info(e.getMessage());
		j.setSuccess(false);
		j.setMsg("????????????");
	}
	return j;
}

/**
 * ?????????????????????
 * @return
 */
@RequestMapping(value="toEdit",method = RequestMethod.GET)
public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		 VelocityContext velocityContext = new VelocityContext();
		 MyJwWebJwid myJwWebJwid = myJwWebJwidService.queryById(id);
		 velocityContext.put("myJwWebJwid",myJwWebJwid);
		 String viewName = "commonweixin/back/myJwWebJwid-edit.vm";
		 String jwid = request.getSession().getAttribute("jwid").toString();
		 velocityContext.put("jwid",jwid);
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * ??????
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute MyJwWebJwid myJwWebJwid){
	AjaxJson j = new AjaxJson();
	try {
		Map<String, Object> map = AccessTokenUtil.getAccseeToken(myJwWebJwid.getWeixinAppId(), myJwWebJwid.getWeixinAppSecret());
		if(map.get("accessToken") != null){
			myJwWebJwid.setAccessToken(map.get("accessToken").toString());
			myJwWebJwid.setTokenGetTime((Date) map.get("accessTokenTime"));
			myJwWebJwid.setApiTicket(map.get("apiTicket").toString());
			myJwWebJwid.setApiTicketTime((Date) map.get("apiTicketTime"));
			myJwWebJwid.setJsApiTicket(map.get("jsApiTicket").toString());
			myJwWebJwid.setJsApiTicketTime((Date) map.get("jsApiTicketTime"));
			j.setMsg("?????????????????????");
		}else{
			j.setMsg("AppId??? AppSecret??????????????????????????? ");
			j.setSuccess(false);
		}
		myJwWebJwidService.doEdit(myJwWebJwid);
	} catch (Exception e) {
		e.printStackTrace();
		log.info(e.getMessage());
		j.setSuccess(false);
		j.setMsg("????????????");
	}
	return j;
}


/**
 * ??????
 * @return
 */
@RequestMapping(value="doDelete",method = RequestMethod.GET)
@ResponseBody
public AjaxJson doDelete(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try {
			String jwid = request.getParameter("jwid");
			if(jwid.equals(CommonWeixinProperties.defaultJwid)){
				j.setMsg("???????????????????????????");
				j.setSuccess(false);
				return j;
			}
			myJwWebJwidService.doDelete(id);
			j.setMsg("????????????");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("????????????");
		}
		return j;
}

/**
 * ?????? AccessToken
 * @return
 * @throws Exception
 */
@RequestMapping(value="reset",method = {RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson resetAccessToken(@RequestParam(required = true, value = "id") String id){
	AjaxJson json = new AjaxJson();
	try{
		String resetAccessToken = myJwWebJwidService.resetAccessToken(id);
		if(StringUtils.isNotEmpty(resetAccessToken)){
			if("success".equals(resetAccessToken)){
				json.setMsg("??????token??????");
			}else{
				json.setSuccess(false);
				json.setMsg("??????token?????????"+resetAccessToken);
			}
		}else{
			json.setSuccess(false);
			json.setMsg("??????token?????????????????????");
		}
	}catch(Exception e){
		e.printStackTrace();
		log.info(e.getMessage());
		json.setSuccess(false);
		json.setMsg("??????token?????????????????????");
	}
	return json;
}
/**
 * ????????????
 * @return
 */
@RequestMapping(value = "/doUpload",method ={RequestMethod.POST})
@ResponseBody
public AjaxJson doUpload(MultipartHttpServletRequest request,HttpServletResponse response){
	AjaxJson j = new AjaxJson();
	try {
		MultipartFile uploadify = request.getFile("file");
        byte[] bytes = uploadify.getBytes();  
        String realFilename=uploadify.getOriginalFilename();
        String fileExtension = realFilename.substring(realFilename.lastIndexOf("."));
        String filename=UUID.randomUUID().toString().replace("-", "")+fileExtension;
        String uploadDir = request.getSession().getServletContext().getRealPath("upload/img/commonweixin/");   
        File dirPath = new File(uploadDir);  
        if (!dirPath.exists()) {  
            dirPath.mkdirs();  
        }  
        String sep = System.getProperty("file.separator");  
        File uploadedFile = new File(uploadDir + sep  + filename);  
        FileCopyUtils.copy(bytes, uploadedFile);  
        j.setObj(filename);
        j.setSuccess(true);
		j.setMsg("????????????");
	} catch (Exception e) {
		e.printStackTrace();
		j.setSuccess(false);
		j.setMsg("????????????");
	}
	return j;
}
/**
 * ?????????????????????
 * @param request
 * @return
 */
@RequestMapping(value = "toSweepCodeAuthorization",method = {RequestMethod.GET,RequestMethod.POST})
public void toSweepCodeAuthorization(HttpServletRequest request,HttpServletResponse response) throws Exception {
	VelocityContext velocityContext = new VelocityContext();
	String viewName = "open/back/myJwWebJwid3-sweepCodeAuthorization.vm";
	ViewVelocity.view(request,response,viewName,velocityContext);
}
@ResponseBody
@RequestMapping(value = "getAuthhorizationUrl")
public AjaxJson getAuthhorizationUrl(HttpServletRequest request) {
	AjaxJson j=new AjaxJson();
	try {
		WeixinOpenAccount weixinOpenAccount = weixinOpenAccountService.queryOneByAppid(CommonWeixinProperties.component_appid);
		if(weixinOpenAccount==null){
			throw new CommonweixinException("??????APPID??????WEIXINOPENACCOUNT??????!");
		}
		//??????ACCESSTOKEN
		if(StringUtils.isEmpty(weixinOpenAccount.getComponentAccessToken())){
			throw new CommonweixinException("??????????????????????????????ACCESSTOKEN");
		}
		//??????????????????
		String preAuthCode = JwThirdAPI.getPreAuthCode(CommonWeixinProperties.component_appid, weixinOpenAccount.getComponentAccessToken());
		String authhorizationUrl = CommonWeixinProperties.authhorizationUrl.replace("PRE_AUTH_CODE", preAuthCode);
		String redirect_uri = URLEncoder.encode(CommonWeixinProperties.authhorizationCallBackUrl+"?userId="+request.getSession().getAttribute(Constants.SYSTEM_USERID),"UTF-8");
		authhorizationUrl = authhorizationUrl.replace("REDIRECT_URI", redirect_uri).replace("COMPONENT_APPID", CommonWeixinProperties.component_appid);
		log.info("===========??????????????????????????????===?????????==="+authhorizationUrl+"============");
		j.setObj(authhorizationUrl);
	}catch (CommonweixinException e) {
		e.printStackTrace();
		j.setMsg(e.getMessage());
		j.setSuccess(false);
		log.error("getAuthhorizationUrl error={}",new Object[]{e.getMessage()});
	}catch (Exception e) {
		e.printStackTrace();
		log.error("getAuthhorizationUrl error={}",new Object[]{e});
		j.setMsg("??????????????????????????????!");
		j.setSuccess(false);
	}
	return j;
}

/**
 * ??????????????????
 * @param request
 * @return
 */
@RequestMapping(value = "callback")
public void callback(HttpServletRequest request,HttpServletResponse response) throws Exception {
	String message="???????????????";
	VelocityContext velocityContext = new VelocityContext();
	String viewName = "open/back/myJwWebJwid-callback.vm";
	velocityContext.put("message",message);
	try {
		String authCode = request.getParameter("auth_code");
		WeixinOpenAccount weixinOpenAccount = weixinOpenAccountService.queryOneByAppid(CommonWeixinProperties.component_appid);

		//????????????????????????ACCESSTOKEN
		String componentAccessToken = weixinOpenAccount.getComponentAccessToken();
		if(StringUtils.isEmpty(componentAccessToken)){
			throw new CommonweixinException("??????????????????????????????ACCESSTOKEN??????!");
		}

		//????????????
		String urlFormat = CommonWeixinProperties.getApiQueryAuth.replace("COMPONENT_ACCESS_TOKEN", componentAccessToken);
		JSONObject json = new JSONObject();
		json.put("component_appid", CommonWeixinProperties.component_appid);
		json.put("authorization_code", authCode);
		log.info("??????????????????????????????????????????????????????{}",new Object[]{json.toString()});
		JSONObject jsonObject = WxstoreUtils.httpRequest(urlFormat, "POST", json.toString());
		log.info("??????????????????????????????????????????????????????{}",new Object[]{jsonObject});
		if (jsonObject != null && !jsonObject.containsKey("errcode")) {
			MyJwWebJwid myJwWebJwid = new MyJwWebJwid();
			// ????????????????????????????????????
			myJwWebJwid.setCreateBy(request.getParameter("userId"));
			save(jsonObject, myJwWebJwid);
			// ???????????????token?????????????????????
			String getAuthorizerInfoUrl = CommonWeixinProperties.getAuthorizerInfo.replace("COMPONENT_ACCESS_TOKEN", componentAccessToken);
			JSONObject j = new JSONObject();
			// ???????????????appid
			j.put("component_appid", CommonWeixinProperties.component_appid);
			// ???????????????appid
			j.put("authorizer_appid", myJwWebJwid.getWeixinAppId());
			JSONObject jsonObj = WxstoreUtils.httpRequest(getAuthorizerInfoUrl, "POST", j.toString());
			log.info("===========??????????????????===???????????????????????????Info==="+jsonObj.toString()+"===========");
			if (jsonObj != null && !jsonObj.containsKey("errcode")) {
				update(jsonObj, myJwWebJwid);
			}
		}
	}catch (CommonweixinException e) {
		e.printStackTrace();
		message="????????????";
		log.error("?????????????????????????????????????????????????????????={}",new Object[]{e.getMessage()});

	}catch (Exception e) {
		e.printStackTrace();
		log.error("?????????????????????????????????????????????????????????={}",new Object[]{e});
		message="????????????";
	}
	/*PrintWriter pw = null;
	try {
		//response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		pw = response.getWriter();
		pw.write("<h1 style='text-align:center'>"+message+"</h1>");
		pw.flush();
	} finally{
		pw.close();
	}*/
	ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * ????????????
 * @param jsonObj
 * @param myJwWebJwid
 */
private void update(JSONObject jsonObj, MyJwWebJwid myJwWebJwid) {
	try {
		String authorizerInfoStr = jsonObj.getString("authorizer_info");
		String qrcodeUrl = null;
		JSONObject authorizerInfoJson = JSONObject.fromObject(authorizerInfoStr);
		if(authorizerInfoJson.containsKey("qrcode_url")){
			qrcodeUrl = authorizerInfoJson.getString("qrcode_url");
		}
		String nickName = authorizerInfoJson.getString("nick_name");
		String headImg = null;
		if(authorizerInfoJson.containsKey("head_img")&& StringUtils.isNotEmpty(authorizerInfoJson.getString("head_img"))){
			   headImg = authorizerInfoJson.getString("head_img");
			   myJwWebJwid.setHeadimgurl(headImg);
		}
		String serviceTypeInfo = authorizerInfoJson.getString("service_type_info");
		String verifyTypeInfo = authorizerInfoJson.getString("verify_type_info");
		String userName = authorizerInfoJson.getString("user_name");
		String businessInfo = authorizerInfoJson.getString("business_info");
		String alias="";
		if(authorizerInfoJson.containsKey("alias")){
			alias = authorizerInfoJson.getString("alias");
		}
		String authorizationInfoS = jsonObj.getString("authorization_info");
		JSONObject authorization_info_json = JSONObject.fromObject(authorizationInfoS);
		String func_info = authorization_info_json.getString("func_info");
		myJwWebJwid.setWeixinNumber(alias);
		myJwWebJwid.setBusinessInfo(businessInfo);
		myJwWebJwid.setFuncInfo(func_info);
		myJwWebJwid.setName(nickName);
		String fileName = UUID.randomUUID().toString().replace("-", "").toUpperCase()+".jpg";
		String uploadDir =ContextHolderUtils.getSession().getServletContext().getRealPath("upload/img/commonweixin/");
		download(qrcodeUrl, fileName, uploadDir);
		myJwWebJwid.setQrcodeimg(fileName);
		JSONObject json=JSONObject.fromObject(serviceTypeInfo);
		if(json!=null&&json.containsKey("id")){
			int accountType = json.getInt("id");
			if(2==accountType){
				myJwWebJwid.setAccountType("1");
			}else{
				myJwWebJwid.setAccountType("2");
			}
		}
		json=JSONObject.fromObject(verifyTypeInfo);
		if(json!=null&&json.containsKey("id")){
			int authStatus=json.getInt("id");
			if(authStatus==-1){
				myJwWebJwid.setAuthStatus("0");
			}else{
				myJwWebJwid.setAuthStatus("1");
			}
		}
		myJwWebJwid.setJwid(userName);
		//??????apiticket
		Map<String, String> apiTicket = AccessTokenUtil.getApiTicket(myJwWebJwid.getAccessToken());
		if("true".equals(apiTicket.get("status"))){
			myJwWebJwid.setApiTicket(apiTicket.get("apiTicket"));
			myJwWebJwid.setApiTicketTime(new Date());
			myJwWebJwid.setJsApiTicket(apiTicket.get("jsApiTicket"));
			myJwWebJwid.setJsApiTicketTime(new Date());
		}
		//??????????????????????????????????????????
		MyJwWebJwid webJwid = myJwWebJwidService.queryByJwid(userName);
		if(webJwid==null){
			myJwWebJwidService.doAdd(myJwWebJwid);
		}else{
			myJwWebJwid.setId(webJwid.getId());
			myJwWebJwidService.doUpdate(myJwWebJwid);
		}
		//-------H5??????????????????????????????redis??????,???token??????redis-------------------------------------------
		try {
			log.info("----------???????????????H5??????????????????????????????redis??????token??????-------------");
			WeixinAccount po = new WeixinAccount();
			po.setAccountappid(myJwWebJwid.getWeixinAppId());
			po.setAccountappsecret(myJwWebJwid.getWeixinAppSecret());
			po.setAccountaccesstoken(myJwWebJwid.getAccessToken());
			po.setAddtoekntime(myJwWebJwid.getTokenGetTime());
			po.setAccountnumber(myJwWebJwid.getWeixinNumber());
			po.setApiticket(myJwWebJwid.getApiTicket());
			po.setApiticketttime(myJwWebJwid.getApiTicketTime());
			po.setAccounttype(myJwWebJwid.getAccountType());
			po.setWeixinAccountid(myJwWebJwid.getJwid());//??????ID
			po.setJsapiticket(myJwWebJwid.getJsApiTicket());
			po.setJsapitickettime(myJwWebJwid.getJsApiTicketTime());
			JedisPoolUtil.putWxAccount(po);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("----------???????????????H5??????????????????????????????redis??????token??????-------------"+e.toString());
		}
		//--------H5??????????????????????????????redis??????---------------------------------------
	} catch (Exception e) {
		e.printStackTrace();
		throw new CommonweixinException("??????????????????==UPDATE???????????????"+e.getMessage());
	}
}


/**
 * ????????????
 * @param jsonObject
 * @param myJwWebJwid
 */
private void save(JSONObject jsonObject,MyJwWebJwid myJwWebJwid) {
	try {
		String authorizationInfoStr = jsonObject.getString("authorization_info");
		JSONObject authorizationInfoJson = JSONObject.fromObject(authorizationInfoStr);
		String authorizerAppid = null;
		if(authorizationInfoJson.containsKey("authorizer_appid")){
			authorizerAppid=authorizationInfoJson.getString("authorizer_appid");
		}else if(jsonObject.containsKey("authorizer_appid")){
			authorizerAppid = jsonObject.getString("authorizer_appid");
		}
		String authorizerAccessToken = authorizationInfoJson.getString("authorizer_access_token");
		String authorizerRefreshToken = authorizationInfoJson.getString("authorizer_refresh_token");
		String funcInfoStr ="";
		if(authorizationInfoJson.containsKey("func_info")){
			funcInfoStr= authorizationInfoJson.getString("func_info");
		}else if(jsonObject.containsKey("func_info")){
			funcInfoStr= jsonObject.getString("func_info");
		}
		myJwWebJwid.setAuthorizationInfo(authorizationInfoStr);
		myJwWebJwid.setAccessToken(authorizerAccessToken);
		myJwWebJwid.setTokenGetTime(new Date());
		myJwWebJwid.setWeixinAppId(authorizerAppid);
		myJwWebJwid.setAuthorizerRefreshToken(authorizerRefreshToken);
		myJwWebJwid.setFuncInfo(funcInfoStr);
		myJwWebJwid.setAuthType("2");
		//???????????????1?????????2????????????
		myJwWebJwid.setAuthorizationStatus("1");
	} catch (Exception e) {
		e.printStackTrace();
		throw new CommonweixinException("??????????????????==DOADD???????????????"+e.getMessage());
	}
	
}

/**
 * @param urlString
 * @param filename
 * @param savePath
 * @throws IOException
 */
private void download(String urlString, String filename, String savePath) throws IOException {
	OutputStream os=null;
	InputStream is=null;
	try {
		log.info("??????????????????????????????????????????{},?????????????????????{},???????????????{}",new Object[]{urlString,filename,savePath});
		 // ??????URL
	    URL url = new URL(urlString);
	    // ????????????
	    URLConnection con = url.openConnection();
	    // ?????????
	    is = con.getInputStream();
	    // 1K???????????????
	    byte[] bs = new byte[1024];
	    // ????????????????????????
	    int len;
	    // ??????????????????
	    String sep = System.getProperty("file.separator");  
	    os= new FileOutputStream(savePath+sep+filename);
	    // ????????????
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);
	    }
	} catch (Exception e) {
		e.printStackTrace();
		LOG.error("============???????????????????????????============,error={}",e);
	}finally{
	    if(os!=null){
	    	os.close();
	    }
	    if(is!=null){
	    	is.close();
	    }
	}
}
/**
 * @??????:?????????????????????
 * @??????:LiuShaoQian
 * @??????:20181126
 * @param request
 * @return ajson
 */
@RequestMapping(value = "/switchDefaultOfficialAcco", method = {RequestMethod.GET, RequestMethod.POST })
@ResponseBody
public AjaxJson switchDefaultOfficialAcco(HttpServletRequest request) {
	AjaxJson ajson = new AjaxJson();
	try {
		String systemUserid = request.getSession().getAttribute("system_userid").toString();
		String jwid = null;
		MyJwWebJwid jwWebJwid = myJwWebJwidService.queryByCreateBy(systemUserid,null);
		if (jwWebJwid==null) {
			jwid = CommonWeixinProperties.defaultJwid;
		}else{
			jwid=jwWebJwid.getJwid();
		}
		request.getSession().setAttribute(Constants.SYSTEM_JWID, jwid);
		MyJwWebJwid myJwWebJwid = myJwWebJwidService.queryByJwid(jwid);
		if(myJwWebJwid!=null){
			request.getSession().setAttribute(Constants.SYSTEM_JWIDNAME,myJwWebJwid.getName()); 
		}
	} catch (Exception e) {
		e.printStackTrace();
		ajson.setSuccess(false);
	}
	return ajson;
}

}
