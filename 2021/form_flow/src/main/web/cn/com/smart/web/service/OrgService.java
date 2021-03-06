package cn.com.smart.web.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.bean.TreeProp;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.helper.TreeHelper;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNOrg;
import cn.com.smart.web.bean.entity.TNRole;
import cn.com.smart.web.bean.entity.TNRoleOrg;
import cn.com.smart.web.cache.impl.OrgMemoryCache;
import cn.com.smart.web.constant.enums.OrgType;
import cn.com.smart.web.dao.impl.RoleDao;
import cn.com.smart.web.dao.impl.RoleOrgDao;
import cn.com.smart.web.plugins.OrgZTreeData;
import cn.com.smart.web.plugins.ZTreeHelper;
import cn.com.smart.web.utils.TreeUtil;

import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 *
 */
@Service("orgServ")
public class OrgService extends MgrServiceImpl<TNOrg> {

	@Autowired
	private RoleOrgDao roleOrgDao;
	@Autowired
	private OrgMemoryCache orgCache;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleService roleServ;
	
	@Override
	public SmartResponse<String> save(TNOrg bean) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != bean) {
				TNOrg parentOrg = orgCache.find(bean.getParentId());
				if(null == parentOrg)
					parentOrg = super.find(bean.getParentId()).getData();
				if(null != parentOrg) {
					bean.setSeqParentIds(parentOrg.getSeqParentIds()+"."+parentOrg.getId()+".");
					if(OrgType.DEPARTMENT.getValue().equals(bean.getType())) {
						bean.setSeqNames(parentOrg.getSeqNames()+">"+bean.getName());
					} else {
						bean.setSeqNames(bean.getName());
					}
				} else {
					bean.setSeqNames(bean.getName());
				}
				parentOrg = null;
				smartResp = super.save(bean);
				if(OP_SUCCESS.equals(smartResp.getResult())) {
					TNRole role = roleDao.adminRole();
					if(null != role) {
						LoggerUtils.info(logger, "????????????????????????????????????????????????????????????????????????");
						if(OP_SUCCESS.equals(roleServ.addOrg2Role(role.getId(), new String[]{bean.getId()}))) {
							LoggerUtils.info(logger, "??????????????????????????????????????????[??????]");
						} else {
							LoggerUtils.info(logger, "??????????????????????????????????????????[??????]");
						}
					}
					orgCache.refreshCache();
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	@Override
	public SmartResponse<String> update(TNOrg bean) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != bean) {
				TNOrg parentOrg = orgCache.find(bean.getParentId());
				if(null == parentOrg)
					parentOrg = super.find(bean.getParentId()).getData();
				//??????????????????????????????
				boolean isCascadeUpdate = false; 
				if(!bean.getSeqParentIds().endsWith(bean.getParentId()+".")) {
					isCascadeUpdate = true;
				}
				bean.getParentId();
				bean.getSeqParentIds();
				if(null != parentOrg) {
					bean.setSeqParentIds(StringUtils.handleNull(parentOrg.getSeqParentIds())+parentOrg.getId()+".");
					if(OrgType.DEPARTMENT.getValue().equals(bean.getType())) {
						bean.setSeqNames(parentOrg.getSeqNames()+">"+bean.getName());
					} else {
						bean.setSeqNames(bean.getName());
					}
				} else {
					bean.setSeqNames(bean.getName());
				}
				List<TNOrg> updateOrgs = new ArrayList<TNOrg>();
				if(isCascadeUpdate) {
					List<TNOrg> orgs = orgCache.findAll();
					if(null == orgs)
						orgs = super.findAll().getDatas();
					TNOrg org = getOrg(bean, orgs);
					copyBeanPropValue(bean, org);
					updateOrgs.add(org);
					Stack<TNOrg> stacks = new Stack<TNOrg>();
					stacks.push(bean);
					while(null != stacks && !stacks.isEmpty()) {
						parentOrg = stacks.pop();
						List<TNOrg> subOrgs = getSubOrg(orgs,parentOrg);
						if(null != subOrgs && subOrgs.size()>0) {
							for (TNOrg orgTmp : subOrgs) {
								orgTmp.setSeqParentIds(StringUtils.handleNull(parentOrg.getSeqParentIds())+parentOrg.getId()+".");
								orgTmp.setSeqNames(parentOrg.getSeqNames()+">"+orgTmp.getName());
								updateOrgs.add(orgTmp);
							}
							stacks.addAll(subOrgs);
						}
					}//while
					orgs = null;
					stacks = null;
				} else {
					updateOrgs.add(bean);
				}
				parentOrg = null;
				smartResp = super.update(updateOrgs);
				updateOrgs = null;
				if(OP_SUCCESS.equals(smartResp.getResult())) {
					orgCache.refreshCache();
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	/**
	 *  ??????
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> delete(String id) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(super.getDao().delete(id)) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				orgCache.refreshCache();
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	/**
	 * ????????????
	 * @param bean
	 * @param orgs
	 * @return
	 */
	private TNOrg getOrg(TNOrg bean, List<TNOrg> orgs) {
		for (TNOrg org : orgs) {
			if(org.getId().equals(bean.getId())) {
				bean = org;
				break;
			}
		}
		return bean;
	}
	
	/**
	 * ???????????????
	 * @param source
	 * @param target
	 */
	private void copyBeanPropValue(TNOrg source,TNOrg target) {
		if(null != source && null != target) {
			target.setCode(source.getCode());
			target.setContactNumber(source.getContactNumber());
			target.setContacts(source.getContacts());
			target.setName(source.getName());
			target.setParentId(source.getParentId());
			target.setSeqNames(source.getSeqNames());
			target.setSeqParentIds(source.getSeqParentIds());
			target.setSortOrder(source.getSortOrder());
			target.setType(source.getType());
		}
	}
	
	
	/**
	 * ?????????????????????
	 * @param orgs
	 * @param org
	 * @return
	 * @throws ServiceException
	 */
	protected List<TNOrg> getSubOrg(List<TNOrg> orgs ,TNOrg org) throws ServiceException {
		List<TNOrg> subOrgs = null;
		try {
			if(null != orgs && orgs.size()>0 && null != org) {
				subOrgs = new ArrayList<TNOrg>();
				for (TNOrg orgTmp : orgs) {
					if(org.getId().equals(orgTmp.getParentId())) {
						subOrgs.add(orgTmp);
					}
				}
				subOrgs = subOrgs.size()>0?subOrgs:null;
			}
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return subOrgs;
	}
	
	
	/**
	 * ?????????????????????
	 * @param orgIds
	 * @return
	 * @throws ServiceException
	 */
	public List<TreeProp> getTree(Collection<String> orgIds) throws ServiceException {
		List<TreeProp> treeProps = null;
		try {
			List<TNOrg> orgs = null;
			if(null != orgIds && orgIds.size()>0) {
				//orgs =  orgDao.find(StringUtil.list2Array(orgIds));
				orgs =  orgCache.find(StringUtils.list2Array(orgIds));
			} else {
				//orgs = orgDao.findAll();
				orgs = orgCache.findAll();
			}
			if(null != orgs && orgs.size()>0) {
				TreeHelper<TNOrg> treeHelper = new TreeHelper<TNOrg>();
				List<TNOrg> orgTrees = treeHelper.outPutTree(orgs);
				treeProps = TreeUtil.Org2TreeProp(orgTrees);
				orgTrees = null;
				treeHelper = null;
				orgs = null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return treeProps;
	}
	
	
	/**
	 * ?????????????????????
	 * @param orgIds
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<OrgZTreeData> getZTree(List<String> orgIds,String treeType) throws ServiceException {
		SmartResponse<OrgZTreeData> smartResp = new SmartResponse<OrgZTreeData>();
		try {
			if(null != orgIds && orgIds.size()>0) {
				List<TreeProp> treeProps = getTree(orgIds);
				if(null != treeProps && treeProps.size()>0) {
					ZTreeHelper<OrgZTreeData> zTreeHelper = new ZTreeHelper<OrgZTreeData>(OrgZTreeData.class, treeProps);
					List<OrgZTreeData> ztreeDatas = zTreeHelper.convert(treeType);
					zTreeHelper = null;
					treeProps = null;
					if(null != ztreeDatas && ztreeDatas.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(ztreeDatas);
					}
					ztreeDatas = null;
				}
		  }
	  } catch (Exception e) {
		  throw new ServiceException(e.getCause());
	  }
	  return smartResp;
	}
	
	
	/**
	 * ?????????
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<OrgZTreeData> getZTree() throws ServiceException {
		SmartResponse<OrgZTreeData> smartResp = new SmartResponse<OrgZTreeData>();
		try {
			List<TreeProp> treeProps = getTree(null);
			if(null != treeProps && treeProps.size()>0) {
				ZTreeHelper<OrgZTreeData> zTreeHelper = new ZTreeHelper<OrgZTreeData>(OrgZTreeData.class, treeProps);
				List<OrgZTreeData> ztreeDatas = zTreeHelper.convert(null);
				zTreeHelper = null;
				treeProps = null;
				if(null != ztreeDatas && ztreeDatas.size()>0) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
					smartResp.setDatas(ztreeDatas);
				}
				ztreeDatas = null;
			}
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	/**
	 * ??????????????????????????????
	 * @param orgId
	 * @param id
	 * @return
	 */
	public SmartResponse<String> deleteRole(String orgId, String id) {
	    SmartResponse<String> smartResp = new SmartResponse<String>();
	    smartResp.setMsg("????????????");
	    if(StringUtils.isEmpty(orgId) || StringUtils.isEmpty(id)) {
	        return smartResp;
	    }
	    Map<String,Object> param = new HashMap<String, Object>(3);
	    param.put("orgId", orgId);
	    param.put("id", id);
	    param.put("flag", "o");
	    if(roleOrgDao.delete(param)) {
	        smartResp.setResult(OP_SUCCESS);
	        smartResp.setMsg("????????????");
	    }
	    return smartResp;
	}
	
	/**
	 * ???????????????????????????
	 * @param orgId
	 * @param roleIds
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> addRole2Org(String orgId,String[] roleIds) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(StringUtils.isNotEmpty(orgId) && null != roleIds && roleIds.length>0) {
				if(!roleOrgDao.isRoleInOrgExist(orgId, roleIds)) {
					List<TNRoleOrg> roleOrgs = new ArrayList<TNRoleOrg>();
					TNRoleOrg roleOrg = null;
					for (int i = 0; i < roleIds.length; i++) {
						roleOrg = new TNRoleOrg();
						roleOrg.setRoleId(roleIds[i]);
						roleOrg.setOrgId(orgId);
						roleOrg.setFlag(TNRoleOrg.ORG_FLAG);
						roleOrgs.add(roleOrg);
					}
					List<Serializable> ids = roleOrgDao.save(roleOrgs);
					if(null != ids && ids.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
					}
					ids = null;
					roleOrg= null;
					roleOrgs = null;
				} else {
					smartResp.setMsg("???????????????????????????????????????????????????????????????");
				}
				roleIds = null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
}
