package com.jeecg.p3.goldeneggs.web;

import com.alibaba.fastjson.JSONObject;
import com.jeecg.p3.baseApi.service.BaseApiJwidService;
import com.jeecg.p3.baseApi.service.BaseApiSystemService;
import com.jeecg.p3.goldeneggs.def.SystemGoldProperties;
import com.jeecg.p3.goldeneggs.entity.WxActGoldeneggs;
import com.jeecg.p3.goldeneggs.entity.WxActGoldeneggsRecord;
import com.jeecg.p3.goldeneggs.entity.WxActGoldeneggsRegistration;
import com.jeecg.p3.goldeneggs.entity.WxActGoldeneggsRelation;
import com.jeecg.p3.goldeneggs.exception.GoldeneggsException;
import com.jeecg.p3.goldeneggs.exception.GoldeneggsExceptionEnum;
import com.jeecg.p3.goldeneggs.service.*;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.base.vo.WeixinDto;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/goldeneggs")
public class GoldeneggController {
	public final static Logger LOG = LoggerFactory
			.getLogger(GoldeneggController.class);
	@Autowired
	private WxActGoldeneggsRegistrationService wxActGoldeneggsRegistrationService;
	@Autowired
	private WxActGoldeneggsService wxActGoldeneggsService;
	@Autowired
	private WxActGoldeneggsRelationService wxActGoldeneggsRelationService;
	@Autowired
	private WxActGoldeneggsPrizesService wxActGoldeneggsPrizesService;
	@Autowired
	private WxActGoldeneggsAwardsService wxActGoldeneggsAwardsService;
	@Autowired
	private WxActGoldeneggsRecordService wxActGoldeneggsRecordService;
	@Autowired
	private BaseApiJwidService baseApiJwidService;
	@Autowired
	private BaseApiSystemService baseApiSystemService;
	private static String domain = SystemGoldProperties.domain;
	/**
	 * ???????????????
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toGoldenegg", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void toGoldenegg(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("toYaoqian parameter WeixinDto={}.",
				new Object[] { weixinDto });
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "goldeneggs/vm/Index.vm";
		WxActGoldeneggs queryById = null;
		try {
			// ????????????
			validateWeixinDtoParam(weixinDto);
			String jwid = weixinDto.getJwid();
			String openid = weixinDto.getOpenid();
			String actId = weixinDto.getActId();
			String appid = weixinDto.getAppid();
			//??????????????????
			queryById = wxActGoldeneggsService.queryById(actId);
			if (queryById == null) {
				throw new GoldeneggsException(GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR, "???????????????");
			}
			long date = new Date().getTime();
			if(date<queryById.getStarttime().getTime()){
				//throw new GoldeneggsException(GoldeneggsExceptionEnum.ACT_BARGAIN_NO_START, "??????????????????");
				velocityContext.put("act_Status", false);
				velocityContext.put("act_Status_Msg", "???????????????");
			}
			if(date>queryById.getEndtime().getTime()){
				//throw new GoldeneggsException(GoldeneggsExceptionEnum.ACT_BARGAIN_END, "???????????????");
				velocityContext.put("act_Status", false);
				velocityContext.put("act_Status_Msg", "???????????????");
			}
			
			//--update-begin---author:huangqingquan---date:20161125-----for:?????????????????????---------------
			if("1".equals(queryById.getFoucsUserCanJoin())){//???????????????????????????????????????????????????	
				velocityContext.put("qrcodeUrl", baseApiJwidService.getQrcodeUrl(jwid));
				JSONObject userobj = WeiXinHttpUtil.getGzUserInfo(weixinDto.getOpenid(),weixinDto.getJwid());
				if(userobj!=null&&userobj.containsKey("subscribe")){
					if(!"1".equals(userobj.getString("subscribe"))){
						velocityContext.put("subscribeFlage", "1");
					}
				}else{
					velocityContext.put("subscribeFlage", "1");
				}
			}
			//--update-end---author:huangqingquan---date:20161125-----for:?????????????????????---------------
			
			// ??????????????????????????????????????????????????????
			List<WxActGoldeneggsRelation> list = wxActGoldeneggsRelationService
					.queryPrizeAndAward(actId);
			Integer count = null;// ?????????
			Integer awardsNum = null;// ????????????
			Integer remainNumDay = null;// ??????????????????
			WxActGoldeneggsRegistration registration = wxActGoldeneggsRegistrationService
					.getOpenid(openid, actId);

			count = queryById.getCount();// ?????????
			if (registration == null) {// ????????????????????????????????????
				awardsNum = count;// ????????????????????????????????????
				remainNumDay = queryById.getNumPerDay();// ?????????????????????
			}
			if (registration != null) {
				SimpleDateFormat sb = new SimpleDateFormat("yyyyMMdd");
				String update = sb.format(new Date());
				if(update.equals(registration.getUpdateTime())){
					remainNumDay = queryById.getNumPerDay()-registration.getRemainNumDay();// ?????????????????????
				}else{				
					remainNumDay = registration.getRemainNumDay();
				}
				awardsNum = count-registration.getAwardsNum();
			}
			if(awardsNum<1){
				awardsNum=0;
			}
			if(remainNumDay<1){
				remainNumDay=0;
			}
			if(remainNumDay>awardsNum){
				remainNumDay=awardsNum;
			}
			velocityContext.put("count", count.toString());
			velocityContext.put("awardsNum", awardsNum.toString());
			velocityContext.put("remainNumDay", remainNumDay.toString());
			velocityContext.put("list", list);
			velocityContext.put("goldeneggs", queryById);
			velocityContext.put("weixinDto", weixinDto);
			String Hdurl = queryById.getHdurl().replace("${domain}",domain);
			velocityContext.put("hdUrl",Hdurl); //????????????URL
			velocityContext.put("appId", appid);// ?????????????????????????????????
			velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);// ?????????????????????????????????
			velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);// ?????????????????????????????????
			velocityContext.put("signature",WeiXinHttpUtil.getRedisSignature(request, jwid));// ???????????????????????????1
			//--update-begin-author:liwenhui date:2018-3-13 for?????????????????????
			velocityContext.put("huodong_bottom_copyright", baseApiSystemService.getHuodongLogoBottomCopyright(queryById.getCreateBy()));
			//--update-end-author:liwenhui date:2018-3-13 for?????????????????????
		}catch (GoldeneggsException e) {
			e.printStackTrace();
			LOG.error("goldeneggs toGoldenegg error:{}", e.getMessage());
			velocityContext.put("errCode", e.getDefineCode());
			velocityContext.put("errMsg", e.getMessage());
			viewName=chooseErrorPage(e.getDefineCode());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("goldeneggs toGoldenegg error:{}", e);
			velocityContext.put("errCode",GoldeneggsExceptionEnum.SYS_ERROR.getErrCode());
			velocityContext.put("errMsg",GoldeneggsExceptionEnum.SYS_ERROR.getErrChineseMsg());
			viewName= "system/vm/error.vm";
		}
		ViewVelocity.view(request, response, viewName, velocityContext);
	}
	/**
	 * ????????????
	 * @param errorCode
	 * @return
	 */
	private String chooseErrorPage(String errorCode){
		if(errorCode.equals("02007")){
			return "system/vm/before.vm";
		}else if(errorCode.equals("02008")){
			return "system/vm/over.vm";
		}else{
			return"system/vm/error.vm";
		}
	}
	/**
	 * ?????????????????????????????????
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCheck", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson toCheck(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		AjaxJson j = new AjaxJson();
		try {
			String actId = request.getParameter("actId");
			String jwid = request.getParameter("jwid");
			String openid = request.getParameter("openid");
			WxActGoldeneggs wxActGoldeneggs = wxActGoldeneggsService.queryById(actId);
			if (wxActGoldeneggs == null) {
				throw new GoldeneggsException(
						GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR, "???????????????");
			}
			if (!jwid.equals(wxActGoldeneggs.getJwid())) {
				throw new GoldeneggsException(
						GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"?????????????????????????????????");
			}
			//??????????????????
			WxActGoldeneggsRegistration wxActGoldeneggsRegistration = wxActGoldeneggsRegistrationService
					.getOpenid(openid, actId);
			if (wxActGoldeneggsRegistration != null) {
				//????????????????????????
				Integer remainNumDays = null;
				//???????????????
				Integer awardsNum = null;
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
				String date = sdf.format(new Date());
				if(wxActGoldeneggsRegistration.getUpdateTime().equals(date)){
					remainNumDays = wxActGoldeneggs.getNumPerDay()- wxActGoldeneggsRegistration
					.getRemainNumDay();// ????????????????????????
					if (remainNumDays < 1) {
						j.setObj("3");
					}
				}
				awardsNum = wxActGoldeneggsRegistration.getAwardsNum();// ?????????????????????
				if (awardsNum >= wxActGoldeneggs.getCount()) {
					j.setObj("2");
					j.setSuccess(false);
				}
			}
		} catch (GoldeneggsException e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("???????????????");
			LOG.info(e.toString());
		}
		return j;
	}

	/**
	 * ?????????????????????
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */

	@RequestMapping(value = "/toAward", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson toAward(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		AjaxJson j = new AjaxJson();
		try {
			String actId = request.getParameter("actId");
			String jwid = request.getParameter("jwid");
			String openid = request.getParameter("openid");
			WxActGoldeneggs queryById = wxActGoldeneggsService.queryById(actId);
			if (queryById == null) {
				throw new GoldeneggsException(
						GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR, "???????????????");
			}
			if (!jwid.equals(queryById.getJwid())) {
				throw new GoldeneggsException(
						GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"?????????????????????????????????");
			}
			j = wxActGoldeneggsRegistrationService.prizeRecord(weixinDto, j);// ?????????????????????????????????
			if(j.getAttributes()!=null){
				WxActGoldeneggsRecord wxActGoldeneggsRecord = wxActGoldeneggsRecordService.queryByCode(j.getAttributes().get("code").toString());
				j.getAttributes().put("recordId", wxActGoldeneggsRecord.getId());
			}
		} catch (GoldeneggsException e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("???????????????");
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveGoldEggPrize", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson saveGoldEggPrize(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		AjaxJson j = new AjaxJson();
		try {
			String mobile = request.getParameter("mobile");
			String username = request.getParameter("username");
			String address = request.getParameter("address");
			String recordId = request.getParameter("recordId");
			WxActGoldeneggsRecord queryByCode = wxActGoldeneggsRecordService
					.queryById(recordId);
			queryByCode.setPhone(mobile);
			queryByCode.setAddress(address);
			queryByCode.setRealname(username);
			wxActGoldeneggsRecordService.doEdit(queryByCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * ????????????????????????
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */

	@RequestMapping(value = "/toMyPrize", method = { RequestMethod.GET,RequestMethod.POST })
	public void toMyPrize(@ModelAttribute WeixinDto weixinDto,HttpServletRequest request, HttpServletResponse response)throws Exception {
		LOG.info("toYaoqian parameter WeixinDto={}.",new Object[] { weixinDto });
		String jwid = weixinDto.getJwid();
		String openid = weixinDto.getOpenid();
		String actId = weixinDto.getActId();
		String code = request.getParameter("code");
		String viewName = "goldeneggs/vm/prizename.vm";
		VelocityContext velocityContext = new VelocityContext();
		String userAddress = null;
		String userName = null;
		String userMobile = null;
		WxActGoldeneggs wxActGoldeneggs = wxActGoldeneggsService.queryById(actId);
		long date = new Date().getTime();
		if(date<wxActGoldeneggs.getStarttime().getTime()){
			velocityContext.put("act_Status", false);
			velocityContext.put("act_Status_Msg", "???????????????");
		}
		if(date>wxActGoldeneggs.getEndtime().getTime()){
			velocityContext.put("act_Status", false);
			velocityContext.put("act_Status_Msg", "???????????????");
		}
		List<WxActGoldeneggsRecord> queryLists = wxActGoldeneggsRecordService.queryMyList(openid,actId);
		List<WxActGoldeneggsRecord> queryByCodes = new ArrayList<WxActGoldeneggsRecord>();
		for (WxActGoldeneggsRecord list : queryLists) {
			String codes = list.getCode();
			if (codes != null) {
				WxActGoldeneggsRecord queryByCode = wxActGoldeneggsRecordService
						.queryByCode(codes);
				userAddress = queryByCode.getAddress();
				userName = queryByCode.getRealname();
				userMobile = queryByCode.getPhone();
				queryByCodes.add(queryByCode);
			}
		}
		velocityContext.put("code", code);
		velocityContext.put("queryList", queryByCodes);
		velocityContext.put("goldeneggs", wxActGoldeneggs);
		velocityContext.put("weixinDto", weixinDto);
		velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);// ?????????????????????????????????
		velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);// ?????????????????????????????????
		velocityContext.put("signature",WeiXinHttpUtil.getRedisSignature(request, jwid));// ???????????????????????????1
		//--update-begin-author:liwenhui date:2018-3-13 for?????????????????????
		velocityContext.put("huodong_bottom_copyright", baseApiSystemService.getHuodongLogoBottomCopyright(wxActGoldeneggs.getCreateBy()));
		//--update-end-author:liwenhui date:2018-3-13 for?????????????????????
		ViewVelocity.view(request, response, viewName, velocityContext);

	}

	/**
	 * ????????????????????????
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAllPrize", method = { RequestMethod.GET,RequestMethod.POST })
	public void toAllPrize(@ModelAttribute WeixinDto weixinDto,HttpServletRequest request, HttpServletResponse response)throws Exception {
		LOG.info("toYaoqian parameter WeixinDto={}.",new Object[] { weixinDto });
		String jwid = weixinDto.getJwid();
		String actId = weixinDto.getActId();
		String viewName = "goldeneggs/vm/allprizename.vm";
		VelocityContext velocityContext = new VelocityContext();
		WxActGoldeneggs wxActGoldeneggs = wxActGoldeneggsService.queryById(actId);
		long date = new Date().getTime();
		if(date<wxActGoldeneggs.getStarttime().getTime()){
			velocityContext.put("act_Status", false);
			velocityContext.put("act_Status_Msg", "???????????????");
		}
		if(date>wxActGoldeneggs.getEndtime().getTime()){
			velocityContext.put("act_Status", false);
			velocityContext.put("act_Status_Msg", "???????????????");
		}
		List<WxActGoldeneggsRecord> queryLists = wxActGoldeneggsRecordService.queryList(actId);
		List<WxActGoldeneggsRecord> queryByCodes = new ArrayList<WxActGoldeneggsRecord>();
		for (WxActGoldeneggsRecord list : queryLists) {
			String codes = list.getCode();
			if (codes != null) {
				WxActGoldeneggsRecord queryByCode = wxActGoldeneggsRecordService.queryByCode(codes);
				queryByCodes.add(queryByCode);
			}
		}
		velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);// ?????????????????????????????????
		velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);// ?????????????????????????????????
		velocityContext.put("signature",WeiXinHttpUtil.getRedisSignature(request, jwid));// ???????????????????????????1
		velocityContext.put("queryList", queryByCodes);
		velocityContext.put("goldeneggs", wxActGoldeneggs);
		velocityContext.put("weixinDto", weixinDto);
		//--update-begin-author:liwenhui date:2018-3-13 for?????????????????????
		velocityContext.put("huodong_bottom_copyright", baseApiSystemService.getHuodongLogoBottomCopyright(wxActGoldeneggs.getCreateBy()));
		//--update-end-author:liwenhui date:2018-3-13 for?????????????????????
		ViewVelocity.view(request, response, viewName, velocityContext);

	}

	/**
	 * ????????????????????????????????????
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping(value = "/toUpdateMessage", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson toUpdateMessage(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response) {
		LOG.info("toYaoqian parameter WeixinDto={}.",
				new Object[] { weixinDto });
		AjaxJson j = new AjaxJson();
		String jwid = weixinDto.getJwid();
		String openid = weixinDto.getOpenid();
		String actId = weixinDto.getActId();
		String id = request.getParameter("id");
		try {
			WxActGoldeneggsRecord queryByCode = wxActGoldeneggsRecordService
					.queryById(id);

			String userAddress = null;
			String userName = null;
			String userMobile = null;
			String awdCode=null;//???????????????
			if (queryByCode != null) {
				userAddress = queryByCode.getAddress();
				userName = queryByCode.getRealname();
				userMobile = queryByCode.getPhone();
				//update-begin--Author:liuyuchong  Date:20180503 for??????????????????
				awdCode=queryByCode.getCode();
				//update-end--Author:liuyuchong  Date:20180503 for??????????????????
			}
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("userName", userName);
			mm.put("userAddress", userAddress);
			mm.put("userMobile", userMobile);
			mm.put("recordId", id);
			//update-begin--Author:liuyuchong  Date:20180503 for???????????????????????????
			mm.put("awdCode",awdCode);
			//update-end--Author:liuyuchong  Date:20180503 for???????????????????????????
			j.setAttributes(mm);
			j.setObj("iscode");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		return j;
	}
	/**
	 * ????????????
	 * @param weixinDto
	 */
	private void validateWeixinDtoParam(WeixinDto weixinDto) {
		
		if (StringUtils.isEmpty(weixinDto.getActId())) {
			throw new GoldeneggsException(
					GoldeneggsExceptionEnum.ARGUMENT_ERROR, "??????ID????????????");
		}
		if (StringUtils.isEmpty(weixinDto.getOpenid())) {
			throw new GoldeneggsException(
					GoldeneggsExceptionEnum.ARGUMENT_ERROR, "?????????openid????????????");
		}
		if (StringUtils.isEmpty(weixinDto.getJwid())) {
			throw new GoldeneggsException(
					GoldeneggsExceptionEnum.ARGUMENT_ERROR, "??????ID????????????");
		}
	}
	
}
