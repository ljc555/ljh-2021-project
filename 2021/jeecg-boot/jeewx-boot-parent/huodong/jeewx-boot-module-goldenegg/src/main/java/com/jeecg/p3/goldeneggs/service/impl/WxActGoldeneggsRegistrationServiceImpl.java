package com.jeecg.p3.goldeneggs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jeecg.p3.baseApi.util.EmojiFilter;
import com.jeecg.p3.baseApi.util.WeixinUserUtil;
import com.jeecg.p3.goldeneggs.dao.WxActGoldeneggsRecordDao;
import com.jeecg.p3.goldeneggs.dao.WxActGoldeneggsRegistrationDao;
import com.jeecg.p3.goldeneggs.dao.WxActGoldeneggsShareRecordDao;
import com.jeecg.p3.goldeneggs.entity.*;
import com.jeecg.p3.goldeneggs.exception.GoldeneggsException;
import com.jeecg.p3.goldeneggs.exception.GoldeneggsExceptionEnum;
import com.jeecg.p3.goldeneggs.service.*;
import com.jeecg.p3.goldeneggs.util.LotteryUtil;
import org.jeecgframework.p3.base.vo.WeixinDto;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.common.utils.StringUtil;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.jeecgframework.p3.core.util.oConvertUtils;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import org.jeecgframework.p3.core.utils.persistence.OptimisticLockingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("wxActGoldeneggsRegistrationService")
public class WxActGoldeneggsRegistrationServiceImpl implements
		WxActGoldeneggsRegistrationService {
	@Resource
	private WxActGoldeneggsRecordDao wxActGoldeneggsRecordDao;
	@Resource
	private WxActGoldeneggsShareRecordDao wxActGoldeneggsShareRecordDao;

	@Resource
	private WxActGoldeneggsRegistrationDao wxActGoldeneggsRegistrationDao;
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

	@Override
	public void doAdd(WxActGoldeneggsRegistration wxActGoldeneggsRegistration) {
		wxActGoldeneggsRegistrationDao.insert(wxActGoldeneggsRegistration);
	}

	@Override
	public void doEdit(WxActGoldeneggsRegistration wxActGoldeneggsRegistration) {
		wxActGoldeneggsRegistrationDao.update(wxActGoldeneggsRegistration);
	}

	@Override
	public void doDelete(String id) {
		wxActGoldeneggsRegistrationDao.delete(id);
	}

	@Override
	public WxActGoldeneggsRegistration queryById(String id) {
		WxActGoldeneggsRegistration wxActGoldeneggsRegistration = wxActGoldeneggsRegistrationDao
				.get(id);
		return wxActGoldeneggsRegistration;
	}

	@Override
	public PageList<WxActGoldeneggsRegistration> queryPageList(
			PageQuery<WxActGoldeneggsRegistration> pageQuery) {
		PageList<WxActGoldeneggsRegistration> result = new PageList<WxActGoldeneggsRegistration>();
		Integer itemCount = wxActGoldeneggsRegistrationDao.count(pageQuery);
		PageQueryWrapper<WxActGoldeneggsRegistration> wrapper = new PageQueryWrapper<WxActGoldeneggsRegistration>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActGoldeneggsRegistration> list = wxActGoldeneggsRegistrationDao
				.queryPageList(wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(),
				itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public WxActGoldeneggsRegistration getOpenid(String openid, String actId) {

		return wxActGoldeneggsRegistrationDao.getOpenid(openid, actId);
	}

	// ????????????
	public WxActGoldeneggsRelation calcOtherPrizePercentage(
			List<WxActGoldeneggsRelation> otherPrizeList) {// ????????????id???????????????????????????????????????
		if (otherPrizeList == null || otherPrizeList.size() == 0) {
			return null;
		}
		// ??????????????????????????????
		List<Double> orignalRates = new ArrayList<Double>(otherPrizeList.size());
		for (int i = 0; i < otherPrizeList.size(); i++) {
			Integer remainNum = otherPrizeList.get(i).getRemainNum();
			if(remainNum==null){
				remainNum=0;
			}
			double probability = otherPrizeList.get(i).getProbability().doubleValue();
			if (remainNum <= 0) {// ?????????????????????????????????????????????
				probability = 0;
			}
			orignalRates.add(probability);
		}
		int index = LotteryUtil.lottery(orignalRates);
		WxActGoldeneggsRelation wxActGoldeneggsRelation = null;
		if (index >= 0) {
			wxActGoldeneggsRelation = otherPrizeList.get(index);
		}
		if (index < 0) {
			wxActGoldeneggsRelation = null;
		}
		return wxActGoldeneggsRelation;
	}

	@Transactional(rollbackFor = Exception.class)
	public AjaxJson prizeRecord(WeixinDto weixinDto, AjaxJson j) {
		String actId = weixinDto.getActId();
		String jwid = weixinDto.getJwid();
		String openid = weixinDto.getOpenid();
		String nickname = "";
		WxActGoldeneggs wxActGoldeneggs = wxActGoldeneggsService.queryById(actId);
		
		List<WxActGoldeneggsRelation> otherPrizeList = wxActGoldeneggsRelationService
				.queryByActId(actId);
		List<Double> list=new ArrayList<Double>();
		List<WxActGoldeneggsRelation> newPrizeList=new ArrayList<WxActGoldeneggsRelation>();
		for(WxActGoldeneggsRelation wxActGoldeneggsRelation:otherPrizeList){
			if(wxActGoldeneggsRelation.getRemainNum()!=null&&wxActGoldeneggsRelation.getRemainNum()>0){
				if(wxActGoldeneggsRelation.getProbability()!=null){
					list.add(wxActGoldeneggsRelation.getProbability().doubleValue());
					newPrizeList.add(wxActGoldeneggsRelation);
				}
			}
		}
		WxActGoldeneggsRelation relation=null;
		int lottery = LotteryUtil.lottery(list);
		if(lottery>=0){
			relation=newPrizeList.get(lottery);
		}
		/*int index=-1;
		for(WxActGoldeneggsRelation wxActGoldeneggsRelation:otherPrizeList){
			if(wxActGoldeneggsRelation.getRemainNum()!=null&&wxActGoldeneggsRelation.getRemainNum()>0){
				if(wxActGoldeneggsRelation.getProbability()!=null){
					index++;
					if(index==lottery&&wxActGoldeneggsRelation.getProbability().compareTo(BigDecimal.valueOf(list.get(lottery)))==0){
						relation=wxActGoldeneggsRelation;
						break;
					}
				}
			}
		}*/
		String name = null;// ???????????????
		String awardsName = null;// ???????????????
		Map<String, Object> mm = new HashMap<String, Object>();
		String prizeId = null;
		String awardId = null;
		String code = null;
		if (relation == null || "".equals(relation)) {
			name = null;// ???????????????
			awardsName = null;// ???????????????
			j.setObj("noPrizes");
		} else {
			prizeId = relation.getPrizeId();
			awardId = relation.getAwardId();
			WxActGoldeneggsPrizes prizes = wxActGoldeneggsPrizesService
					.queryById(prizeId);
			WxActGoldeneggsAwards awards = wxActGoldeneggsAwardsService
					.queryById(awardId);
			if (prizeId != null) {
				name = prizes.getName();// ???????????????
			}
			if (awards != null) {
				awardsName = awards.getAwardsName();// ???????????????
			}
			Integer rnamer = relation.getRemainNum();// ????????????????????????
			if (rnamer >= 1) {// ????????????
				rnamer = rnamer - 1;
				relation.setRemainNum(rnamer);
				wxActGoldeneggsRelationService.doEdit(relation);// ?????????????????????
			} else {
				j.setObj("noPrizes");
			}
			code = getCoupon();
			mm.put("awardsName", awardsName);
			mm.put("code", code);
			mm.put("name", name);
			j.setAttributes(mm);
			j.setObj("isPrizes");
		}
		// ??????????????????????????????
		WxActGoldeneggsRecord wxActGoldeneggsRecord = new WxActGoldeneggsRecord();
		JSONObject userobj = WeiXinHttpUtil.getGzUserInfo(openid, jwid);
		if (userobj != null && userobj.containsKey("nickname")) {
			nickname = userobj.getString("nickname");
		}
		if (StringUtil.isEmpty(nickname)) {
			nickname = "??????";
		}
		wxActGoldeneggsRecord.setActId(actId);
		wxActGoldeneggsRecord.setOpenid(openid);
		wxActGoldeneggsRecord.setNickname(EmojiFilter.filterEmoji(nickname));
		wxActGoldeneggsRecord.setCreateTime(new Date());
		wxActGoldeneggsRecord.setAwardsName(awardsName);
		if (name == null && awardsName == null) {
			wxActGoldeneggsRecord.setPrizesState("0");
		} else {
			wxActGoldeneggsRecord.setPrizesState("1");
			wxActGoldeneggsRecord.setCode(code);
			// ??????????????????
			Integer maxAwardsSeq = wxActGoldeneggsRecordDao
					.getMaxAwardsSeq(wxActGoldeneggsRecord.getActId());
			Integer nextAwardsSeq = oConvertUtils.getInt(maxAwardsSeq)  + 1;
			wxActGoldeneggsRecord.setSeq(nextAwardsSeq);
		}
		wxActGoldeneggsRecord.setPrizesName(name);
		wxActGoldeneggsRecord.setJwid(jwid);
		wxActGoldeneggsRecord.setNickname(nickname);
		wxActGoldeneggsRecord.setAwardsId(awardId);
		wxActGoldeneggsRecordService.doAdd(wxActGoldeneggsRecord);// ??????????????????

		// ?????????openid???????????????????????????????????????
		WxActGoldeneggsRegistration wxActGoldeneggsRegistration = wxActGoldeneggsRegistrationDao
				.getOpenid(openid, actId);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyyMMdd");
		String update = sb.format(date);
		if (wxActGoldeneggsRegistration == null) {
			wxActGoldeneggsRegistration = new WxActGoldeneggsRegistration();
			wxActGoldeneggsRegistration.setActId(actId);
			wxActGoldeneggsRegistration.setOpenid(openid);
			wxActGoldeneggsRegistration.setAwardsNum(1);
			wxActGoldeneggsRegistration.setCreateTime(date);
			wxActGoldeneggsRegistration.setAwardsStatus("1");
			wxActGoldeneggsRegistration.setJwid(jwid);
			wxActGoldeneggsRegistration.setUpdateTime(update);
			wxActGoldeneggsRegistration.setRemainNumDay(1);
			wxActGoldeneggsRegistration.setNickname(EmojiFilter
					.filterEmoji(nickname));
			wxActGoldeneggsRegistrationDao.insert(wxActGoldeneggsRegistration);// ???????????????????????????
			// ????????????????????????????????? ??????
		} else {// ????????????openid????????????
			String updatetime = wxActGoldeneggsRegistration.getUpdateTime();
			String dd = sb.format(date);
			Integer awardsNum = null;// ??????????????????
			if (dd.equals(updatetime)) {// ?????????????????????
				Integer remainnumdays = wxActGoldeneggsRegistration
						.getRemainNumDay();// ?????????????????????
				Integer awardsnums = wxActGoldeneggsRegistration.getAwardsNum();// ??????????????????
				if (remainnumdays >= wxActGoldeneggs.getNumPerDay()) {
					throw new GoldeneggsException(
							GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
							"???????????????????????????!");
				}
				if (awardsnums >= wxActGoldeneggs.getCount()) {
					throw new GoldeneggsException(
							GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
							"???????????????????????????");
				}
				int row = wxActGoldeneggsRegistrationDao.updateNumSameDay(wxActGoldeneggs.getCount(), wxActGoldeneggs.getNumPerDay(),dd, wxActGoldeneggsRegistration.getId());
				if (row == 0) {
					throw new OptimisticLockingException("???????????????");
				}
			} else {// ?????????????????????????????????
				//update---begin------fanpengcheng---------20160928----------for:?????????????????????????????????????????????????????????
				awardsNum = wxActGoldeneggsRegistration.getAwardsNum();// ??????????????????
				if (awardsNum >= wxActGoldeneggs.getCount()) {
					throw new GoldeneggsException(
							GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
							"???????????????????????????");
				} 
				int row = wxActGoldeneggsRegistrationDao.updateNumDistinctDay(wxActGoldeneggs.getCount(), wxActGoldeneggs.getNumPerDay(), dd, wxActGoldeneggsRegistration.getId());
				if (row == 0) {
					throw new OptimisticLockingException("???????????????");
				}
			}
		}
		return j;
	}
	/**
	 * ???????????????
	 * @throws Exception 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson prizeRecordNew(WeixinDto weixinDto, AjaxJson j) throws Exception {
		String actId = weixinDto.getActId();
		String jwid = weixinDto.getJwid();
		String openid = weixinDto.getOpenid();
		String nickname = "";
		String headimgurl = "";
		WxActGoldeneggs wxActGoldeneggs = wxActGoldeneggsService.queryById(actId);
		//?????????????????????
		List<WxActGoldeneggsRelation> otherPrizeList = wxActGoldeneggsRelationService
		.queryByActId(actId);
		List<Double> list=new ArrayList<Double>();
		List<WxActGoldeneggsRelation> newPrizeList=new ArrayList<WxActGoldeneggsRelation>();
		//?????????????????????
		for(WxActGoldeneggsRelation wxActGoldeneggsRelation:otherPrizeList){
			if(wxActGoldeneggsRelation.getRemainNum()!=null&&wxActGoldeneggsRelation.getRemainNum()>0){
				if(wxActGoldeneggsRelation.getProbability()!=null){
					list.add(wxActGoldeneggsRelation.getProbability().doubleValue());
					newPrizeList.add(wxActGoldeneggsRelation);
				}
			}
		}
		//??????
		int lottery = LotteryUtil.lottery(list);
		WxActGoldeneggsRelation relation=null;
		if(lottery>=0){
			relation=newPrizeList.get(lottery);
		}
		/*int index1=-1;
		for(WxActGoldeneggsRelation wxActGoldeneggsRelation:otherPrizeList){
			if(wxActGoldeneggsRelation.getRemainNum()!=null&&wxActGoldeneggsRelation.getRemainNum()>0){
				if(wxActGoldeneggsRelation.getProbability()!=null){
					index++;
					if(index==lottery&&wxActGoldeneggsRelation.getProbability().compareTo(BigDecimal.valueOf(list.get(lottery)))==0){
						relation=wxActGoldeneggsRelation;
						break;
					}
				}
			}
		}*/
		String name = null;// ???????????????
		String awardsName = null;// ???????????????
		Map<String, Object> mm = new HashMap<String, Object>();
		String relationId = null;
		String prizeId = null;
		String awardId = null;
		String code = null;
		if (relation == null || "".equals(relation)) {
			name = null;// ???????????????
			awardsName = null;// ???????????????
			j.setObj("2");
			mm.put("title","???????????????????????????????????????!");
			j.setAttributes(mm);
		} else {
			//????????????id
			relationId=relation.getId();
			//??????id
			prizeId = relation.getPrizeId();
			//??????id
			awardId = relation.getAwardId();
			WxActGoldeneggsPrizes prizes = wxActGoldeneggsPrizesService
			.queryById(prizeId);
			WxActGoldeneggsAwards awards = wxActGoldeneggsAwardsService
			.queryById(awardId);
			if (prizeId != null) {
				name = prizes.getName();// ???????????????
			}
			if (awards != null) {
				awardsName = awards.getAwardsName();// ???????????????
			}
			Integer rnamer = relation.getRemainNum();// ???????????????????????????
			if (rnamer >= 1) {// ????????????
				rnamer = rnamer - 1;
				relation.setRemainNum(rnamer);
				wxActGoldeneggsRelationService.doEdit(relation);// ?????????????????????
			} else {
				//??????????????????
				j.setObj("2");
				mm.put("title","????????????");
				j.setAttributes(mm);
			}
			//?????????
			code = getCoupon();
			mm.put("c_type", awardsName);
			mm.put("code", code);
			mm.put("name", name);
			mm.put("c_name", name);
			//??????
			mm.put("img", prizes.getImg());
			mm.put("c_img",prizes.getImg() );
			mm.put("title","?????????????????????????????????????????????");
			j.setAttributes(mm);
			j.setObj("1");
		}
		
		// ??????????????????????????????
		WxActGoldeneggsRecord wxActGoldeneggsRecord = new WxActGoldeneggsRecord();
		JSONObject userobj = WeiXinHttpUtil.getGzUserInfo(openid, jwid);
		if (userobj != null && userobj.containsKey("nickname")) {
			nickname = userobj.getString("nickname");
		}
		if (StringUtil.isEmpty(nickname)) {
			nickname = "??????";
		}
		if (userobj != null && userobj.containsKey("headimgurl")) {
			headimgurl = userobj.getString("headimgurl");
		}
		if (StringUtil.isEmpty(headimgurl)) {
			headimgurl = "http://static.h5huodong.com/upload/common/defaultHeadImg.png";
		}
		wxActGoldeneggsRecord.setActId(actId);
		wxActGoldeneggsRecord.setOpenid(openid);
		wxActGoldeneggsRecord.setNickname(EmojiFilter.filterEmoji(nickname));
		wxActGoldeneggsRecord.setHeadimgurl(headimgurl);
		wxActGoldeneggsRecord.setCreateTime(new Date());
		wxActGoldeneggsRecord.setAwardsName(awardsName);
		if (name == null && awardsName == null) {
			wxActGoldeneggsRecord.setPrizesState("0");
		} else {
			wxActGoldeneggsRecord.setPrizesState("1");
			wxActGoldeneggsRecord.setCode(code);
			// ??????????????????
			Integer maxAwardsSeq = wxActGoldeneggsRecordDao
			.getMaxAwardsSeq(wxActGoldeneggsRecord.getActId());
			Integer nextAwardsSeq = oConvertUtils.getInt(maxAwardsSeq) + 1;
			wxActGoldeneggsRecord.setSeq(nextAwardsSeq);
		}
		wxActGoldeneggsRecord.setPrizesName(name);
		wxActGoldeneggsRecord.setRelationId(relationId);
		wxActGoldeneggsRecord.setJwid(jwid);
		wxActGoldeneggsRecord.setNickname(nickname);
		wxActGoldeneggsRecord.setAwardsId(awardId);
		wxActGoldeneggsRecordService.doAdd(wxActGoldeneggsRecord);// ??????????????????
		
		// ?????????openid???????????????????????????????????????
		WxActGoldeneggsRegistration wxActGoldeneggsRegistration = wxActGoldeneggsRegistrationDao
		.getOpenid(openid, actId);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyyMMdd");
		String update = sb.format(date);
		if (wxActGoldeneggsRegistration == null) {
			wxActGoldeneggsRegistration = new WxActGoldeneggsRegistration();
			wxActGoldeneggsRegistration.setActId(actId);
			wxActGoldeneggsRegistration.setOpenid(openid);
			wxActGoldeneggsRegistration.setAwardsNum(1);
			wxActGoldeneggsRegistration.setCreateTime(date);
			wxActGoldeneggsRegistration.setAwardsStatus("1");
			wxActGoldeneggsRegistration.setJwid(jwid);
			wxActGoldeneggsRegistration.setUpdateTime(update);
			wxActGoldeneggsRegistration.setRemainNumDay(1);
			wxActGoldeneggsRegistration.setNickname(EmojiFilter
					.filterEmoji(nickname));
			wxActGoldeneggsRegistrationDao.insert(wxActGoldeneggsRegistration);// ???????????????????????????
			// ????????????????????????????????? ??????
		} else {
			// ????????????openid???????????? ?????????????????????????????????
			int extraCount=0;
			if("0".equals(wxActGoldeneggs.getExtraLuckyDraw())){
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				List<WxActGoldeneggsShareRecord> wxActGoldeneggsShareRecord=wxActGoldeneggsShareRecordDao.queryShareRecordByDate(actId, openid, sd.format(new Date()), "");
				if(wxActGoldeneggsShareRecord.size()>0){
					extraCount=1;
				}
			}
			if(wxActGoldeneggs.getCount() !=0){
				String updatetime = wxActGoldeneggsRegistration.getUpdateTime();
				String dd = sb.format(date);
				Integer awardsNum = null;// ??????????????????
				if (dd.equals(updatetime)) {// ?????????????????????
					Integer remainnumdays = wxActGoldeneggsRegistration
					.getRemainNumDay();// ?????????????????????
					Integer awardsnums = wxActGoldeneggsRegistration.getAwardsNum();// ??????????????????
					if (remainnumdays >= wxActGoldeneggs.getNumPerDay()+extraCount) {
						throw new GoldeneggsException(
								GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"???????????????????????????!");
					}
					if (awardsnums >= wxActGoldeneggs.getCount()) {
						throw new GoldeneggsException(
								GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"???????????????????????????");
					}
					int row = wxActGoldeneggsRegistrationDao.updateNumSameDay(wxActGoldeneggs.getCount(), wxActGoldeneggs.getNumPerDay()+extraCount,dd, wxActGoldeneggsRegistration.getId());
					if (row == 0) {
						throw new OptimisticLockingException("???????????????");
					}
				} else {// ?????????????????????????????????
					//update---begin------fanpengcheng---------20160928----------for:?????????????????????????????????????????????????????????
					awardsNum = wxActGoldeneggsRegistration.getAwardsNum();// ??????????????????
					if (awardsNum >= wxActGoldeneggs.getCount()) {
						throw new GoldeneggsException(
								GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"???????????????????????????");
					} 
					int row = wxActGoldeneggsRegistrationDao.updateNumDistinctDay(wxActGoldeneggs.getCount(), wxActGoldeneggs.getNumPerDay()+extraCount, dd, wxActGoldeneggsRegistration.getId());
					if (row == 0) {
						throw new OptimisticLockingException("???????????????");
					}
				}
			}else{
				String updatetime = wxActGoldeneggsRegistration.getUpdateTime();
				String dd = sb.format(date);
				Integer awardsNum = null;// ??????????????????
				if (dd.equals(updatetime)) {// ?????????????????????
					Integer remainnumdays = wxActGoldeneggsRegistration
					.getRemainNumDay();// ?????????????????????
					Integer awardsnums = wxActGoldeneggsRegistration.getAwardsNum();// ??????????????????
					if (remainnumdays >= wxActGoldeneggs.getNumPerDay()+extraCount) {
						throw new GoldeneggsException(
								GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"???????????????????????????!");
					}
					int row = wxActGoldeneggsRegistrationDao.updateZeroCountNumSameDay( wxActGoldeneggs.getNumPerDay()+extraCount,dd, wxActGoldeneggsRegistration.getId());
					if (row == 0) {
						throw new OptimisticLockingException("???????????????");
					}
				} else {
					int row = wxActGoldeneggsRegistrationDao.updateZeroCountNumDistinctDay(wxActGoldeneggs.getNumPerDay()+extraCount, dd, wxActGoldeneggsRegistration.getId());
					if (row == 0) {
						throw new OptimisticLockingException("???????????????");
					}
				}
				
			}
		}
		return j;
	}
	/**
	 * ????????????????????????
	 */
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson noPrizeRecordNew(WeixinDto weixinDto, AjaxJson j) {
		String actId = weixinDto.getActId();
		String jwid = weixinDto.getJwid();
		String openid = weixinDto.getOpenid();
		//String nickname = "";
		WxActGoldeneggs wxActGoldeneggs = wxActGoldeneggsService.queryById(actId);
		String name = null;// ???????????????
		String awardsName = null;// ???????????????
		Map<String, Object> mm = new HashMap<String, Object>();
		String prizeId = null;
		String awardId = null;
		j.setObj("2");
		mm.put("title","???????????????????????????????????????!");
		j.setAttributes(mm);
		// ??????????????????????????????
		WxActGoldeneggsRecord wxActGoldeneggsRecord = new WxActGoldeneggsRecord();
		WeixinUserUtil.setWeixinDto(weixinDto,null);
		/*JSONObject userobj = WeiXinHttpUtil.getGzUserInfo(openid, jwid);
		if (userobj != null && userobj.containsKey("nickname")) {
			nickname = userobj.getString("nickname");
		}
		if (StringUtil.isEmpty(nickname)) {
			nickname = "??????";
		}*/
		wxActGoldeneggsRecord.setActId(actId);
		wxActGoldeneggsRecord.setOpenid(openid);
		wxActGoldeneggsRecord.setNickname(weixinDto.getNickname());
		wxActGoldeneggsRecord.setCreateTime(new Date());
		wxActGoldeneggsRecord.setAwardsName(awardsName);
		wxActGoldeneggsRecord.setPrizesState("0");
		wxActGoldeneggsRecord.setPrizesName(name);
		wxActGoldeneggsRecord.setJwid(jwid);
		wxActGoldeneggsRecord.setHeadimgurl(weixinDto.getHeadimgurl());
		wxActGoldeneggsRecord.setAwardsId(awardId);
		wxActGoldeneggsRecordService.doAdd(wxActGoldeneggsRecord);// ??????????????????
		// ?????????openid???????????????????????????????????????
		WxActGoldeneggsRegistration wxActGoldeneggsRegistration = wxActGoldeneggsRegistrationDao
		.getOpenid(openid, actId);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyyMMdd");
		String update = sb.format(date);
		//??????????????????
		if (wxActGoldeneggsRegistration == null) {
			wxActGoldeneggsRegistration = new WxActGoldeneggsRegistration();
			wxActGoldeneggsRegistration.setActId(actId);
			wxActGoldeneggsRegistration.setOpenid(openid);
			wxActGoldeneggsRegistration.setAwardsNum(1);
			wxActGoldeneggsRegistration.setCreateTime(date);
			wxActGoldeneggsRegistration.setAwardsStatus("1");
			wxActGoldeneggsRegistration.setJwid(jwid);
			wxActGoldeneggsRegistration.setUpdateTime(update);
			wxActGoldeneggsRegistration.setRemainNumDay(1);
			wxActGoldeneggsRegistration.setNickname(weixinDto.getNickname());
			wxActGoldeneggsRegistrationDao.insert(wxActGoldeneggsRegistration);// ???????????????????????????
		}else{
			//??????openid?????????????????????????????????????????????0
			//?????????????????????????????????
			int extraCount=0;
			if("0".equals(wxActGoldeneggs.getExtraLuckyDraw())){
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				List<WxActGoldeneggsShareRecord> wxActGoldeneggsShareRecord=wxActGoldeneggsShareRecordDao.queryShareRecordByDate(actId, openid, sd.format(new Date()), "");
				if(wxActGoldeneggsShareRecord.size()>0){
					extraCount=1;
				}
			}
			if(wxActGoldeneggs.getCount() !=0){
				String updatetime = wxActGoldeneggsRegistration.getUpdateTime();
				String dd = sb.format(date);
				Integer awardsNum = null;// ??????????????????
				if (dd.equals(updatetime)) {// ?????????????????????
					Integer remainnumdays = wxActGoldeneggsRegistration
					.getRemainNumDay();// ?????????????????????
					Integer awardsnums = wxActGoldeneggsRegistration.getAwardsNum();// ??????????????????
					if (remainnumdays >= wxActGoldeneggs.getNumPerDay()+extraCount) {
						throw new GoldeneggsException(
								GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"???????????????????????????!");
					}
					if (awardsnums >= wxActGoldeneggs.getCount()) {
						throw new GoldeneggsException(
								GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"???????????????????????????");
					}
					int row = wxActGoldeneggsRegistrationDao.updateNumSameDay(wxActGoldeneggs.getCount(), wxActGoldeneggs.getNumPerDay()+extraCount,dd, wxActGoldeneggsRegistration.getId());
					if (row == 0) {
						throw new OptimisticLockingException("???????????????");
					}
				} else {// ?????????????????????????????????
					//update---begin------fanpengcheng---------20160928----------for:?????????????????????????????????????????????????????????
					awardsNum = wxActGoldeneggsRegistration.getAwardsNum();// ??????????????????
					if (awardsNum >= wxActGoldeneggs.getCount()) {
						throw new GoldeneggsException(
								GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"???????????????????????????");
					} 
					int row = wxActGoldeneggsRegistrationDao.updateNumDistinctDay(wxActGoldeneggs.getCount(), wxActGoldeneggs.getNumPerDay(), dd, wxActGoldeneggsRegistration.getId());
					if (row == 0) {
						throw new OptimisticLockingException("???????????????");
					}
				}
			}else{
				String updatetime = wxActGoldeneggsRegistration.getUpdateTime();
				String dd = sb.format(date);
				Integer awardsNum = null;// ??????????????????
				if (dd.equals(updatetime)) {// ?????????????????????
					Integer remainnumdays = wxActGoldeneggsRegistration
					.getRemainNumDay();// ?????????????????????
					Integer awardsnums = wxActGoldeneggsRegistration.getAwardsNum();// ??????????????????
					if (remainnumdays >= wxActGoldeneggs.getNumPerDay()+extraCount) {
						throw new GoldeneggsException(
								GoldeneggsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"???????????????????????????!");
					}
					int row = wxActGoldeneggsRegistrationDao.updateZeroCountNumSameDay(wxActGoldeneggs.getNumPerDay()+extraCount,dd, wxActGoldeneggsRegistration.getId());
					if (row == 0) {
						throw new OptimisticLockingException("???????????????");
					}
				} else {
					int row = wxActGoldeneggsRegistrationDao.updateZeroCountNumDistinctDay(wxActGoldeneggs.getNumPerDay()+extraCount, dd, wxActGoldeneggsRegistration.getId());
					if (row == 0) {
						throw new OptimisticLockingException("???????????????");
					}
				}
				
			}
		}
		return j;
	}
	/**
	 * ??????12?????????,????????????,????????????????????????????????????100???999??????????????????20???
	 * @return ??????????????????
	 */
	private synchronized static String getCoupon(){
		char ch[]=new char[]{'0','1','2','3','4','5','6','7','8','9'
				,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
				,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
				};
		Random rand = new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<12;i++){
			sb.append(ch[rand.nextInt(62)]);
		}
		return sb.toString();
	}

	@Override
	public Integer queryCountByActId(String id) {
		return wxActGoldeneggsRegistrationDao.queryCountByActId(id);
	}
}
