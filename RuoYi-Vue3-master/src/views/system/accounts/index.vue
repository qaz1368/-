<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-form
            :model="queryParams"
            ref="queryFormRef"
            :inline="true"
            v-show="showSearch"
        >
          <el-form-item label="邮箱地址" prop="email">
            <el-input
                v-model="queryParams.email"
                placeholder="请输入邮箱地址"
                clearable

                @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="用户名" prop="userName">
            <el-input
                v-model="queryParams.userName"
                placeholder="请输入用户名"
                clearable
                @keyup.enter="handleQuery"
            />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="Search" size="default" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" size="default" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>


    <el-row :gutter="10" class="mb8">

      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:accounts:edit']"
        >修改</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:accounts:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="accountsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="邮箱地址" align="center" prop="email" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="SMTP服务器域名" align="center" prop="smtpServerDomain" />
      <el-table-column label="SMTP服务器端口" align="center" prop="smtpServerPort" />
      <el-table-column label="是否启用SSL" align="center" prop="sslEnabled">
        <template #default="scope">
          {{ scope.row.sslEnabled === 1 ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column label="是否启用STARTTLS" align="center" prop="starttlsEnabled">
        <template #default="scope">
          {{ scope.row.starttlsEnabled === 1 ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-tooltip content="修改" placement="top">
            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:industry:edit']"></el-button>
          </el-tooltip>

        </template>
      </el-table-column>
    </el-table>


    <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="邮箱地址" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱地址" />
        </el-form-item>
        <el-form-item label="SMTP服务器域名" prop="smtpServerDomain">
          <el-input v-model="form.smtpServerDomain" placeholder="请输入SMTP服务器域名" />
        </el-form-item>
        <el-form-item label="SMTP服务器端口" prop="smtpServerPort">
          <el-input v-model="form.smtpServerPort" placeholder="请输入SMTP服务器端口" />
        </el-form-item>
        <el-form-item label="是否启用SSL" prop="sslEnabled">
          <el-radio-group v-model="form.sslEnabled">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否启用STARTTLS" prop="starttlsEnabled">
          <el-radio-group v-model="form.starttlsEnabled">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="创建时间" prop="createdAt">
          <el-date-picker
              clearable
              v-model="form.createdAt"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup name="Accounts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { parseTime } from '@/utils/ruoyi'
import { getToken } from "@/utils/auth"
import { useRouter } from 'vue-router'
import { listAccounts, getAccounts, delAccounts, addAccounts, updateAccounts } from "@/api/system/accounts"

const router = useRouter()

// 表单引用
const queryFormRef = ref(null)
const formRef = ref(null)

// 遮罩层
const loading = ref(true)
// 选中数组
const ids = ref([])
// 非单个禁用
const single = ref(true)
// 非多个禁用
const multiple = ref(true)
// 显示搜索条件
const showSearch = ref(true)
// 总条数
const total = ref(0)
// 【请填写功能名称】表格数据
const accountsList = ref([])
// 弹出层标题
const title = ref("")
// 是否显示弹出层
const open = ref(false)
// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  email: null,
  username: null,
  smtpServerDomain: null,
  smtpServerPort: null,
  sslEnabled: null,
  starttlsEnabled: null,
  createdAt: null
})
// 表单参数
const form = reactive({})
// 表单校验
const rules = reactive({
  email: [
    { required: true, message: "邮箱地址不能为空", trigger: "blur" }
  ],
  username: [
    { required: true, message: "用户名不能为空", trigger: "blur" }
  ],
  smtpServerDomain: [
    { required: true, message: "SMTP服务器域名不能为空", trigger: "blur" }
  ],
  smtpServerPort: [
    { required: true, message: "SMTP服务器端口不能为空", trigger: "blur" }
  ],
  sslEnabled: [
    { required: true, message: "是否启用SSL不能为空", trigger: "blur" }
  ],
  starttlsEnabled: [
    { required: true, message: "是否启用STARTTLS不能为空", trigger: "blur" }
  ],
  createdAt: [
    { required: true, message: "创建时间不能为空", trigger: "blur" }
  ]
})

/** 查询【请填写功能名称】列表 */
const getList = () => {
  loading.value = true
  listAccounts(queryParams).then(response => {
    accountsList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
const cancel = () => {
  open.value = false
  reset()
}

// 表单重置
const reset = () => {
  form.value = {
    id: null,
    email: null,
    username: null,
    smtpServerDomain: null,
    smtpServerPort: null,
    sslEnabled: null,
    starttlsEnabled: null,
    createdAt: null
  }
  formRef.value?.resetFields()
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNum = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields()
  handleQuery()
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.id)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}


/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset()
  const id = row.id || ids.value
  getAccounts(id).then(response => {
    Object.assign(form, response.data)
    open.value = true
    title.value = "修改邮箱信息"
  })
}

/** 提交按钮 */
const submitForm = () => {
  formRef.value?.validate((valid) => {
    if (valid) {
      if (form.id != null) {
        updateAccounts(form).then(response => {
          ElMessage.success("修改成功")
          open.value = false
          getList()
        })
      } else {
        addAccounts(form).then(response => {
          ElMessage.success("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}


/** 导出按钮操作 */
const handleExport = () => {
  router.push('/download/system/accounts/export?' + new URLSearchParams(queryParams).toString())
}

onMounted(() => {
  getList()
})
</script>