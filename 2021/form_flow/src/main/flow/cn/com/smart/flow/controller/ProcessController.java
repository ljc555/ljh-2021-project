package cn.com.smart.flow.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Task;
import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.JsonHelper;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.flow.ProcessContext;
import cn.com.smart.flow.SnakerEngineFacets;
import cn.com.smart.flow.SnakerHelper;
import cn.com.smart.flow.bean.DataClassify;
import cn.com.smart.flow.bean.OutputClassify;
import cn.com.smart.flow.bean.SubmitFormData;
import cn.com.smart.flow.bean.TaskInfo;
import cn.com.smart.flow.bean.entity.TFlowForm;
import cn.com.smart.flow.ext.ExtProcess;
import cn.com.smart.flow.ext.ExtProcessModel;
import cn.com.smart.flow.ext.ExtTaskModel;
import cn.com.smart.flow.helper.FlowFormUploadFileHelper;
import cn.com.smart.flow.helper.ProcessHelper;
import cn.com.smart.flow.service.FlowFormService;
import cn.com.smart.flow.service.ProcessFacade;
import cn.com.smart.form.bean.QueryFormData;
import cn.com.smart.form.bean.entity.TForm;
import cn.com.smart.form.service.IFormDataService;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.plugins.OrgUserZTreeData;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

/**
 * ????????????--??????
 * @author lmq
 * @version 1.0 
 * @since 
 *
 */
@Controller
@RequestMapping("/process")
public class ProcessController extends BaseFlowControler {
	
	private static final String VIEW_DIR = "flow/process";
	@Autowired
	private SnakerEngineFacets facets;
	@Autowired
	private ProcessFacade processFacade;
	@Autowired
	private FlowFormService flowFormServ;
	
	@Autowired
	private ProcessContext processContext;
	@Autowired
	private IFormDataService formDataServ;
	
	
	/**
	 * ??????????????????
	 * @param request
	 * @param modelView
	 * @param taskInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form")
	public ModelAndView form(HttpServletRequest request,ModelAndView modelView,TaskInfo taskInfo) throws Exception {
		modelView.setViewName(VIEW_DIR+"/form");
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("refreshUrl", taskInfo.getRefreshUrl());
		if(null == taskInfo || (StringUtils.isEmpty(taskInfo.getProcessId())) 
				&& StringUtils.isEmpty(taskInfo.getProcessName())) {
			return modelView;
		}
		UserInfo userInfo = getUserInfoFromSession(request);
		ProcessHelper.initProcessNameOrId(facets, taskInfo);
	    //???????????????????????????????????????HTML??????
	    SmartResponse<TForm> smartResp = processFacade.getForm(taskInfo.getProcessId());
	    modelMap.put("smartResp", smartResp);
	    ExtProcess process = facets.getProcess(taskInfo.getProcessId());
	    if(null != process) {
	    	modelMap.put("isAtt", process.getAttachment());
	    }
	    //?????????????????????,???????????????????????????????????????????????????????????????????????????????????????(???????????????)???
	    TaskModel model = facets.getTaskModel(taskInfo.getProcessId(), taskInfo.getTaskKey());
	    modelMap.put("taskModel", model);
	    OutputClassify outputClassify = processFacade.outputClassify(model.getOutputs());
	    modelMap.put("outputClassify", outputClassify);
	    if(null != outputClassify && CollectionUtils.isNotEmpty(outputClassify.getBackLines())) {
	    	if(outputClassify.getBackLines().size()==1) {
	    		modelMap.put("backName", outputClassify.getBackLines().get(0).getName());
	    	}
	    }
	    int firstNode = 0;
	    if(!OP_SUCCESS.equals(smartResp.getResult())) {
	    	return modelView;
	    }
	    SmartResponse<QueryFormData> chRes = processFacade.getFormData(smartResp.getData().getId(), taskInfo.getOrderId(), userInfo.getId());
	    smartResp = null;
	    //????????????ID(orderId)???????????????????????????????????????????????????????????????????????????
	    if(StringUtils.isEmpty(taskInfo.getOrderId())) {
	        firstNode = 1;
	    } else {
	        //???????????????(?????????)???????????????
	        String[] array = processFacade.getNameAndTitle(taskInfo.getOrderId());
	        if(null != array && array.length>1) {
	        	modelMap.put("title", array[0]);
	        	modelMap.put("creator", array[1]);
	        }
	    }
	    if(OP_SUCCESS.equals(chRes.getResult())) {
	    	//??????????????????ID
			if(null != chRes.getData())
				modelMap.put("formDataId", chRes.getData().getValue());
	    }//if
	    String output = JsonHelper.toJson(chRes);
    	output = StringUtils.repaceSpecialChar(output);
    	output = StringUtils.repaceSlash(output);
    	chRes = null;
    	modelMap.put("output", output);
    	modelMap.put("taskInfo", taskInfo);
	    modelMap.put("firstNode", firstNode);
	    chRes = null;
		return modelView;
	}
	
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param submitFormData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveForm",method=RequestMethod.POST)
	public void saveForm(HttpServletRequest request, HttpServletResponse response, SubmitFormData submitFormData) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != submitFormData && StringUtils.isNotEmpty(submitFormData.getProcessId()) && 
				StringUtils.isNotEmpty(submitFormData.getFormId())) {
			if(StringUtils.isEmpty(submitFormData.getFormDataId())) {
				submitFormData.setFormState(1);
			}
			UserInfo userInfo = getUserInfoFromSession(request);
			submitFormData.setParams(getRequestParamMap(request, false));
			//????????????
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if(multipartResolver.isMultipart(request)) {
				new FlowFormUploadFileHelper((MultipartHttpServletRequest) request, submitFormData.getParams(), submitFormData, userInfo.getId()).upload();
			}
			smartResp = formDataServ.saveOrUpdateForm(submitFormData.getParams(), 
			        submitFormData.getFormDataId(), submitFormData.getFormId(), userInfo.getId(), 
			        submitFormData.getFormState());
			//smartResp = processFacade.saveOrUpdateForm(submitFormData,userInfo.getId());
			submitFormData = null;
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		ObjectMapper objMapper = new ObjectMapper();
		response.getWriter().print(objMapper.writeValueAsString(smartResp));
	}
	
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param submitFormData
	 * @return
	 */
	@RequestMapping(value="/submitTask",method=RequestMethod.POST)
	public void submitTask(HttpServletRequest request, HttpServletResponse response, SubmitFormData submitFormData) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("??????????????????");
		UserInfo userInfo = getUserInfoFromSession(request);
		//????????????
		Map<String,Object> params = ProcessHelper.handleRequestParam(getRequestParamMap(request, false));
		LoggerUtils.debug(log, "?????????????????????"+params.size());
		LoggerUtils.debug(log, "?????????????????????"+JsonHelper.toJson(params));
		//????????????
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)) {
			new FlowFormUploadFileHelper((MultipartHttpServletRequest) request, params, submitFormData, userInfo.getId()).upload();
		}
		if(null != submitFormData && StringUtils.isNotEmpty(submitFormData.getProcessId()) && 
				StringUtils.isNotEmpty(submitFormData.getFormId())) {
			submitFormData.setFormState(0);
			submitFormData.setParams(params);
			smartResp = processContext.execute(submitFormData,userInfo.getId(),userInfo.getDepartmentId());
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		ObjectMapper objMapper = new ObjectMapper();
		response.getWriter().print(objMapper.writeValueAsString(smartResp));
	}
	
	
	/**
	 * ????????????
	 * @param request
	 * @param queryFilter
	 * @param modelView
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/todo")
	public ModelAndView todo(HttpServletRequest request,QueryFilter queryFilter,Page<WorkItem> page) {
		SmartResponse<DataClassify<WorkItem>> smartResp = new SmartResponse<DataClassify<WorkItem>>();
		UserInfo userInfo = super.getUserInfoFromSession(request);
		String userId = userInfo.getId();
		ModelAndView modelView = new ModelAndView();
		queryFilter = (null == queryFilter ? new QueryFilter():queryFilter);
		String uri = "process/todo";
		queryFilter.setParam(super.getRequestParamMap(request, false));
		page.setPageSize(getPerPageSize());
		facets.getEngine().query().getWorkItems(page, queryFilter.orderBy(" t.create_time").order("desc").setOperators(getGroups(request)));
		ModelMap modelMap = modelView.getModelMap();
		if(null != page && page.getTotalCount()>0) {
			processFacade.assocTaskModel(page.getResult(),userId);
			smartResp = processFacade.todoClassify(page.getResult());
			smartResp.setTotalNum(page.getTotalCount());
			smartResp.setTotalPage((int)page.getTotalPages());
		}
		pageParam = new PageParam(uri, null, page.getPage());
		refreshBtn = new RefreshBtn(uri, null,null);
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("refreshBtn", refreshBtn);
		modelView.setViewName(VIEW_DIR+"/todo");
		return modelView;
	}
	
	
	/**
	 * ????????????
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/indexTodo")
	@ResponseBody
	public SmartResponse<WorkItem> indexTodo(HttpServletRequest request,Page<WorkItem> page) {
		SmartResponse<WorkItem> smartResp = new SmartResponse<WorkItem>();
		UserInfo userInfo = getUserInfoFromSession(request);
		String userId = userInfo.getId();
		page.setPageSize(8);
		facets.getEngine().query().getWorkItems(page, new QueryFilter().setOperators(getGroups(request)));
		if(null != page && page.getTotalCount()>0) {
			smartResp.setResult(OP_SUCCESS);
			smartResp.setMsg(OP_SUCCESS_MSG);
			processFacade.assocTaskModel(page.getResult(),userId);
			smartResp.setDatas(page.getResult());
		}
		return smartResp;
	}
	
	
	/**
	 * ????????????
	 * @param session
	 * @param processId
	 * @param taskId
	 * @param taskKey
	 * @return
	 */
	@RequestMapping("/takeTask")
	@ResponseBody
	public SmartResponse<String> takeTask(HttpSession session,String processId,String taskId,String taskKey) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(processId) && StringUtils.isNotEmpty(taskId) 
				&& StringUtils.isNotEmpty(taskKey)) {
			smartResp = processFacade.takeTask(processId, taskId, taskKey, 
					getUserInfoFromSession(session).getId());
		}
		return smartResp;
	}
	
	
	/**
	 * ????????????
	 * @param request
	 * @param queryFilter
	 * @param page
	 * @return
	 */
	@RequestMapping("/hasTodo")
	public ModelAndView hasTodo(HttpServletRequest request,QueryFilter queryFilter, Page<WorkItem> page) {
		SmartResponse<DataClassify<WorkItem>> smartResp = new SmartResponse<DataClassify<WorkItem>>();
		UserInfo userInfo = getUserInfoFromSession(request);
		String userId = userInfo.getId();
		page.setPageSize(getPerPageSize());
		ModelAndView modelView = new ModelAndView();
		queryFilter = (null == queryFilter ? new QueryFilter():queryFilter);
		String uri = "process/hasTodo";
		/*if(StringUtils.isNotEmpty(queryFilter.getTitle())) {
			try {
				queryFilter.setTitle(URLDecoder.decode(queryFilter.getTitle(), "UTF-8"));
				uri += "?title="+queryFilter.getTitle(); 
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}*/
		facets.getEngine().query().getHistoryWorkItems(page, queryFilter.orderBy(" t.finish_time").order("desc").setOperator(userId));
		ModelMap modelMap = modelView.getModelMap();
		if(null != page && page.getTotalCount()>0) {
			smartResp = processFacade.todoClassify(page.getResult());
			smartResp.setTotalNum(page.getTotalCount());
			smartResp.setTotalPage((int)page.getTotalPages());
		}
		pageParam = new PageParam(uri, null, page.getPage());
		refreshBtn = new RefreshBtn(uri, null,null);
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("queryFilter", queryFilter);
		modelView.setViewName(VIEW_DIR+"/hasTodo");
		return modelView;
	}
	
	
	/**
	 * ???????????????(???????????????????????????????????????)
	 * @param request
	 * @param modelView
	 * @param processId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request,ModelAndView modelView,
			String processId,String orderId) throws Exception {
		if(StringUtils.isNotEmpty(processId)) {
			ExtProcess process = (ExtProcess) facets.getEngine().process().getProcessById(processId);
			AssertHelper.notNull(process);
			ExtProcessModel processModel = (ExtProcessModel)process.getModel();
			Map<String,Object> modelMap = modelView.getModelMap();
			String json = SnakerHelper.getModelJson(processModel);
			modelMap.put("process", json);
			modelMap.put("processName", processModel.getDisplayName());
			if(processModel != null && StringUtils.isNotEmpty(orderId)) {
				List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
				List<HistoryTask> historyTasks = facets.getEngine().query().getHistoryRelateTasks(new QueryFilter().setOrderId(orderId));
				modelMap.put("state", SnakerHelper.getStateJson(processModel, tasks, historyTasks));
			}
		}
		modelView.setViewName(VIEW_DIR+"/view");
		return modelView;
	}
	
	
	/**
	 * ??????????????????
	 * @param modelView
	 * @param processId
	 * @param orderId
	 * @param isAtt ???????????????
	 * @param isPrint ?????????????????????
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewForm")
	public ModelAndView viewForm(ModelAndView modelView,String processId,String orderId,String isAtt, String isPrint) throws Exception {
		SmartResponse<TForm> smartResp = new SmartResponse<TForm>();
		ModelMap modelMap = modelView.getModelMap();
		if(StringUtils.isNotEmpty(processId) && StringUtils.isNotEmpty(orderId)) {
			smartResp = processFacade.getForm(processId);
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				SmartResponse<QueryFormData> chRes = processFacade.getFormData(null,orderId,null);
	        	String output = JsonHelper.toJson(chRes);
	        	output = StringUtils.repaceSpecialChar(output);
	        	output = StringUtils.repaceSlash(output);
	        	chRes = null;
	        	modelMap.put("output", output);
			}
		}
		isAtt = (StringUtils.isEmpty(isAtt) || !StringUtils.isInteger(isAtt))?"0":isAtt;
		isPrint = (StringUtils.isEmpty(isPrint) || !StringUtils.isInteger(isPrint))?"0":isPrint;
		modelMap.put("isAtt", Integer.parseInt(isAtt)>0?"1":"0");
		modelMap.put("isPrint", Integer.parseInt(isPrint)>0?"1":"0");
		modelMap.put("smartResp", smartResp);
		modelView.setViewName(VIEW_DIR+"/viewForm");
		return modelView;
	}
	
	/**
	 * ????????????????????????
	 * @param modelView
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/processHandleInfo")
	public ModelAndView processHandleInfo(ModelAndView modelView,String orderId) throws Exception {
		SmartResponse<HistoryTask> smartResp = new SmartResponse<HistoryTask>();
		ModelMap modelMap = modelView.getModelMap();
		if(StringUtils.isNotEmpty(orderId)) {
			smartResp = processFacade.findHistTasks(orderId);
		}
		modelMap.put("smartResp", smartResp);
		modelView.setViewName(VIEW_DIR+"/processHandleInfo");
		return modelView;
	}
	
	/**
	 * ???????????????????????????
	 * @param session
	 * @param processId
	 * @param orderId
	 * @param taskKey
	 * @return
	 */
	@RequestMapping("/selectNextAssigner")
	@ResponseBody
	public SmartResponse<OrgUserZTreeData> selectNextAssigner(HttpSession session,String processId,String orderId,String taskKey) {
		SmartResponse<OrgUserZTreeData> smartResp = new SmartResponse<OrgUserZTreeData>();
		if(StringUtils.isNotEmpty(processId) && StringUtils.isNotEmpty(taskKey)) {
			smartResp = processFacade.getAssignees(processId, orderId,getUserInfoFromSession(session).getId(), taskKey);
		}
		return smartResp;
	}
	
	/**
	 * ????????????????????????????????????
	 * @param request
	 * @param submitFormData
	 * @return
	 */
	@RequestMapping("/checkInsTitle")
	@ResponseBody
	public SmartResponse<String> checkInsTitle(HttpServletRequest request,SubmitFormData submitFormData) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		//????????????
		Map<String,Object> params = ProcessHelper.handleRequestParam(getRequestParamMap(request, false));
		if(null != submitFormData && StringUtils.isNotEmpty(submitFormData.getProcessId()) && 
				StringUtils.isNotEmpty(submitFormData.getFormId())) {
			submitFormData.setParams(params);
			try {
				smartResp = flowFormServ.checkInsTitle(submitFormData);
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		return smartResp;
	}
	
	
	/**
	 * ??????????????????
	 * @param modelView
	 * @param processId
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/editForm")
	public ModelAndView editForm(ModelAndView modelView,String processId,String orderId) {
		SmartResponse<TForm> smartResp = new SmartResponse<TForm>();
		ModelMap modelMap = modelView.getModelMap();
		if(StringUtils.isNotEmpty(processId) && StringUtils.isNotEmpty(orderId)) {
			smartResp = processFacade.getForm(processId);
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				TFlowForm flowForm = flowFormServ.getFlowFormByOrderId(orderId);
				if(null != flowForm) {
					modelMap.put("formDataId", flowForm.getFormDataId());
				}
				SmartResponse<QueryFormData> chRes = processFacade.getFormData(null,orderId,null);
	        	String output = JsonHelper.toJson(chRes);
	        	//output = StringUtils.repaceSpecialChar(output);
	        	output = StringUtils.repaceSlash(output);
	        	chRes = null;
	        	modelMap.put("output", output);
	        	modelMap.put("formId", smartResp.getData().getId());
	        	modelMap.put("processId", processId);
	        	modelMap.put("orderId", orderId);
			}
		}
		modelMap.put("smartResp", smartResp);
		modelView.setViewName(VIEW_DIR+"/editForm");
		return modelView;
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param formId
	 * @param formDataId
	 * @param response
	 */
	@RequestMapping(value="/updateForm",method=RequestMethod.POST)
	public void updateForm(HttpServletRequest request, HttpServletResponse response,
			String processId, String orderId, String formId, String formDataId){
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("????????????????????????");
		if(StringUtils.isNotEmpty(formId) &&  StringUtils.isNotEmpty(formDataId)) {
			UserInfo userInfo = getUserInfoFromSession(request);
			Map<String, Object> datas = getRequestParamMap(request, false);
			//????????????
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if(multipartResolver.isMultipart(request)) {
				SubmitFormData submitFormData = new SubmitFormData();
				submitFormData.setFormDataId(formDataId);
				submitFormData.setFormId(formId);
				submitFormData.setOrderId(orderId);
				submitFormData.setProcessId(processId);
				submitFormData.setParams(datas);
				new FlowFormUploadFileHelper((MultipartHttpServletRequest) request, datas, submitFormData, userInfo.getId()).upload();
			}
			boolean is = flowFormServ.updateForm(datas, formId, formDataId, userInfo.getId(), YesNoType.NO.getIndex());
			if(is) {
				flowFormServ.updateInsTitle(datas, formDataId);
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg("????????????????????????");
			} 
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		ObjectMapper objMapper = new ObjectMapper();
		try {
			response.getWriter().print(objMapper.writeValueAsString(smartResp));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @param processId ??????ID
	 * @param nextTaskKeys
	 * @return
	 */
	@RequestMapping("/isSelectAssigner")
	@ResponseBody
	public SmartResponse<String> isSelectAssigner(String processId,String nextTaskKeys) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(processId) && StringUtils.isNotEmpty(nextTaskKeys)) {
			String[] nextTaskKeyArray = nextTaskKeys.split(MULTI_VALUE_SPLIT);
			boolean isSelect = false;
			List<ExtTaskModel> lists = facets.getTaskModels(processId, nextTaskKeyArray);
			List<String> selectAssignerStyle = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(lists)) {
				String style = null;
				for (ExtTaskModel taskModel : lists) {
					if(YesNoType.YES.getStrValue().equals(taskModel.getIsExeAssigner())) {
						isSelect = true;
					}
					style = taskModel.getName()+"_"+taskModel.getIsExeAssigner()+"_"+ StringUtils.handleNull(taskModel.getSelectAssignerStyle());
					selectAssignerStyle.add(style);
				}//for
			}//if
			smartResp.setResult(OP_SUCCESS);
			YesNoType yesNo = YesNoType.getObj(isSelect);
			smartResp.setData(yesNo.getStrValue());
			smartResp.setMsg(OP_SUCCESS_MSG);
			smartResp.setDatas(selectAssignerStyle);
			selectAssignerStyle = null;
		}
		return smartResp;
	}
}
