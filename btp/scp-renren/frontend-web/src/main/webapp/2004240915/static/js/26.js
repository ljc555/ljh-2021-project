webpackJsonp([26],{vvxy:function(e,a,t){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var r={data:function(){return{visible:!1,dataForm:{id:0,pernr:"",name:"",name1:"",bukrs:"",depart:"",bankc:"",bank:"",tel:""},dataRule:{pernr:[{required:!0,message:"人员编码不能为空",trigger:"blur"}],name:[{required:!0,message:"人员姓名不能为空",trigger:"blur"}],bukrs:[{required:!0,message:"公司代码不能为空",trigger:"blur"}],depart:[{required:!0,message:"部门编码不能为空",trigger:"blur"}],bankc:[{required:!0,message:"银行账号不能为空",trigger:"blur"}],bank:[{required:!0,message:"银行代码不能为空",trigger:"blur"}],tel:[{required:!0,message:"电话不能为空",trigger:"blur"}]}}},methods:{init:function(e){var a=this;this.dataForm.id=e||0,this.visible=!0,this.$nextTick(function(){a.$refs.dataForm.resetFields()})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(a){a&&e.$http({url:e.$http.adornUrl("/travel/user/"+(e.dataForm.id?"update":"save")),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,pernr:e.dataForm.pernr,name:e.dataForm.name,name1:e.dataForm.name1,bukrs:e.dataForm.bukrs,depart:e.dataForm.depart,bankc:e.dataForm.bankc,bank:e.dataForm.bank,tel:e.dataForm.tel})}).then(function(a){var t=a.data;t&&0===t.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(t.msg)})})}}},l={render:function(){var e=this,a=e.$createElement,t=e._self._c||a;return t("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(a){e.visible=a}}},[t("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(a){if(!("button"in a)&&e._k(a.keyCode,"enter",13,a.key,"Enter"))return null;e.dataFormSubmit()}}},[t("el-form-item",{attrs:{label:"人员编码",prop:"pernr"}},[t("el-input",{attrs:{placeholder:"人员编码"},model:{value:e.dataForm.pernr,callback:function(a){e.$set(e.dataForm,"pernr",a)},expression:"dataForm.pernr"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"人员姓名",prop:"name"}},[t("el-input",{attrs:{placeholder:"人员姓名"},model:{value:e.dataForm.name,callback:function(a){e.$set(e.dataForm,"name",a)},expression:"dataForm.name"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"公司代码",prop:"bukrs"}},[t("el-input",{attrs:{placeholder:"公司代码"},model:{value:e.dataForm.bukrs,callback:function(a){e.$set(e.dataForm,"bukrs",a)},expression:"dataForm.bukrs"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"部门编码",prop:"depart"}},[t("el-input",{attrs:{placeholder:"部门编码"},model:{value:e.dataForm.depart,callback:function(a){e.$set(e.dataForm,"depart",a)},expression:"dataForm.depart"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"部门描述",prop:"name1"}},[t("el-input",{attrs:{placeholder:"部门描述"},model:{value:e.dataForm.name1,callback:function(a){e.$set(e.dataForm,"name1",a)},expression:"dataForm.name1"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"银行账号",prop:"bankc"}},[t("el-input",{attrs:{placeholder:"银行账号"},model:{value:e.dataForm.bankc,callback:function(a){e.$set(e.dataForm,"bankc",a)},expression:"dataForm.bankc"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"银行代码",prop:"bank"}},[t("el-input",{attrs:{placeholder:"银行代码"},model:{value:e.dataForm.bank,callback:function(a){e.$set(e.dataForm,"bank",a)},expression:"dataForm.bank"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"电话",prop:"tel"}},[t("el-input",{attrs:{placeholder:"电话"},model:{value:e.dataForm.tel,callback:function(a){e.$set(e.dataForm,"tel",a)},expression:"dataForm.tel"}})],1)],1),e._v(" "),t("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{on:{click:function(a){e.visible=!1}}},[e._v("取消")]),e._v(" "),t("el-button",{attrs:{type:"primary"},on:{click:function(a){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},o=t("VU/8")(r,l,!1,null,null,null);a.default=o.exports}});