<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="人员编码" prop="pernr">
        <el-input v-model="dataForm.pernr" placeholder="人员编码"></el-input>
      </el-form-item>
      <el-form-item label="人员姓名" prop="name">
        <el-input v-model="dataForm.name" placeholder="人员姓名"></el-input>
      </el-form-item>
      <el-form-item label="公司代码" prop="bukrs">
        <el-input v-model="dataForm.bukrs" placeholder="公司代码"></el-input>
      </el-form-item>
      <el-form-item label="部门编码" prop="depart">
        <el-input v-model="dataForm.depart" placeholder="部门编码"></el-input>
      </el-form-item>
      <el-form-item label="部门描述" prop="name1">
        <el-input v-model="dataForm.name1" placeholder="部门描述"></el-input>
      </el-form-item>
      <el-form-item label="银行账号" prop="bankc">
        <el-input v-model="dataForm.bankc" placeholder="银行账号"></el-input>
      </el-form-item>
      <el-form-item label="银行代码" prop="bank">
        <el-input v-model="dataForm.bank" placeholder="银行代码"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="tel">
        <el-input v-model="dataForm.tel" placeholder="电话"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          pernr: '',
          name: '',
          name1: '',
          bukrs: '',
          depart: '',
          bankc: '',
          bank: '',
          tel: '',
        },
        dataRule: {
          pernr: [
            { required: true, message: '人员编码不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '人员姓名不能为空', trigger: 'blur' }
          ],
          bukrs: [
            { required: true, message: '公司代码不能为空', trigger: 'blur' }
          ],
          depart: [
            { required: true, message: '部门编码不能为空', trigger: 'blur' }
          ],
          bankc: [
            { required: true, message: '银行账号不能为空', trigger: 'blur' }
          ],
          bank: [
            { required: true, message: '银行代码不能为空', trigger: 'blur' }
          ],
          tel: [
            { required: true, message: '电话不能为空', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/travel/user/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'pernr': this.dataForm.pernr,
                'name': this.dataForm.name,
                'name1': this.dataForm.name1,
                'bukrs': this.dataForm.bukrs,
                'depart': this.dataForm.depart,
                'bankc': this.dataForm.bankc,
                'bank': this.dataForm.bank,
                'tel': this.dataForm.tel,
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
