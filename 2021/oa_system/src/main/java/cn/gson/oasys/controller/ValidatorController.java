package cn.gson.oasys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import cn.gson.oasys.common.formValid.BindingResultVOUtil;
import cn.gson.oasys.common.formValid.MapToList;
import cn.gson.oasys.common.formValid.ResultEnum;
import cn.gson.oasys.common.formValid.ResultVO;
import cn.gson.oasys.model.dao.roledao.RoleDao;
import cn.gson.oasys.model.dao.system.StatusDao;
import cn.gson.oasys.model.dao.system.TypeDao;

import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.role.Role;
import cn.gson.oasys.model.entity.system.SystemStatusList;
import cn.gson.oasys.model.entity.system.SystemTypeList;
import cn.gson.oasys.model.entity.task.Tasklist;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.User;


@Controller
@RequestMapping("/")
public class ValidatorController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private StatusDao sdao;
	@Autowired
	private TypeDao tydao;
	@Autowired
	private UserDao udao;
	@Autowired
	private DeptDao ddao;
	@Autowired
	private RoleDao rdao;
	
	@RequestMapping("ck_addtask")
	public String addtask(HttpServletRequest request,@Valid Tasklist tlist,BindingResult br,HttpSession session,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		request.setAttribute("tasklist", tlist);
		Pageable pa=new PageRequest(page, size);
		
		// ????????????ResultVO??????????????????????????????ResultEnum.SUCCESS.getCode()???????????????200??????????????????????????????
		ResultVO res = BindingResultVOUtil.hasErrors(br);
		System.out.println("tlist:"+tlist);
		if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
			List<Object> list = new MapToList<>().mapToList(res.getData());
			request.setAttribute("errormess", list.get(0).toString());
			System.out.println("list???????????????????????????" + tlist);
			System.out.println("list????????????:" + list);
			System.out.println("list???????????????:" + list.get(0));
			System.out.println("?????????????????????????????????" + list.get(0).toString());
			// ?????????info?????????????????????????????????
			log.info("getData:{}", res.getData());
			log.info("getCode:{}", res.getCode());
			log.info("getMsg:{}", res.getMsg());
			
			
			
			String userId = ((String) session.getAttribute("userId")).trim();
			Long userid = Long.parseLong(userId);

			
			// ???????????????
			Iterable<SystemTypeList> typelist = tydao.findAll();
			// ???????????????
			Iterable<SystemStatusList> statuslist = sdao.findAll();
			// ???????????????????????????
			Page<User> pagelist = udao.findByFatherId(userid,pa);
			List<User> emplist=pagelist.getContent();
			// ???????????????
			Iterable<Dept> deptlist = ddao.findAll();
			// ????????????
			Iterable<Role> rolelist = rdao.findAll();
			request.setAttribute("typelist", typelist);
			request.setAttribute("statuslist", statuslist);
			request.setAttribute("emplist", emplist);
			request.setAttribute("page", pagelist);
			request.setAttribute("deptlist", deptlist);
			request.setAttribute("rolelist", rolelist);
			return "task/addtask";
		}
		else{
			
			return "forward:/addtasks";
		}
		
		
		
		
		
	}

}
