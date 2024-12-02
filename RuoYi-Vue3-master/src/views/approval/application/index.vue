<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--用户数据-->
      <el-col :span="24">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="申请人" prop="applicantName">
            <el-input
                v-model="queryParams.applicantName"
                placeholder="请输入申请人姓名"
                clearable
                style="width: 240px"
                @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="applicantPhone">
            <el-input
                v-model="queryParams.applicantPhone"
                placeholder="请输入手机号码"
                clearable
                style="width: 240px"
                @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
         <el-row>
          <el-col :span="1.5">
            <el-button
                type="warning"
                plain
                icon="Download"
                @click="handleExport"
                v-hasPermi="['system:user:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="ApplicationList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center"  v-if="columns[0].visible" >
            <template #default="scope">
              {{scope.$index+1}}
            </template>
          </el-table-column>
          <el-table-column label="申请人姓名" align="center" key="applicantName" prop="applicantName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="申请人手机号" align="center" key="applicantPhone" prop="applicantPhone" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="申请人邮箱" align="center" key="applicantEmail" prop="applicantEmail" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="申请日期" align="center" key="applicationDate" prop="applicationDate" v-if="columns[4].visible" width="180">
            <template #default="scope">
              <span>{{ parseTime(scope.row.applicationDate) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="申请状态" align="center" key="status" prop="status" v-if="columns[5].visible" />
          <el-table-column label="创建时间" align="center" key="createdAt" prop="createdAt" v-if="columns[6].visible" width="180">
            <template #default="scope">
              <span>{{ parseTime(scope.row.createdAt) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
            <template #default="scope">
              <el-button  v-if="!isAdmin" link type="primary" icon="View" @click="openViewDialog(scope.row)"   v-hasPermi="['approval:application:list']">查看</el-button>
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
      </el-col>
    </el-row>
    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form :model="form" :rules="rules" ref="userRef" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="申请人姓名" prop="applicantName">
              <el-input v-model="form.applicantName" placeholder="请输入申请人姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="applicantPhone">
              <el-input v-model="form.applicantPhone" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="applicantEmail">
              <el-input v-model="form.applicantEmail" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="行业类型" prop="industryType">
              <el-select v-model="form.industryName" placeholder="请选择行业类型">
                <el-option
                    v-for="item in state.industryOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row> <el-col :span="12">
          <el-form-item label="申请类型" prop="applicationType">
            <el-select v-model="form.applicationType" placeholder="请选择申请类型">
              <el-option
                  v-for="item in stateType.typeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="企业描述">
              <el-input v-model="form.enterpriseDescription" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
          ref="uploadRef"
          :limit="1"
          accept=".xlsx, .xls"
          :headers="upload.headers"
          :action="upload.url + '?updateSupport=' + upload.updateSupport"
          :disabled="upload.isUploading"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess"
          :auto-upload="false"
          drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          将文件拖到此处，或<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <div class="el-upload__tip">
              <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 查看申请对话框 -->
    <el-dialog title="查看申请" v-model="viewDialogVisible" width="30%">
      <el-form :model="viewForm" label-width="100px">
        <el-form-item label="申请状态">
          <el-radio-group v-model="viewForm.status">
            <el-radio label="通过">通过</el-radio>
            <el-radio label="拒绝">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="企业描述">
          <el-input v-model="viewForm.enterpriseDescription" type="textarea" :rows="3" readonly></el-input>
        </el-form-item>
        <el-form-item label="审核意见" v-if="viewForm.status">
          <el-input v-model="viewForm.reason" type="textarea" :rows="4" :placeholder="viewForm.status === '通过' ? '请输入审核意见' : '请输入审核意见'"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="viewDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateApplicationStatus" :loading="updateLoading">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, getCurrentInstance } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { parseTime, addDateRange } from '@/utils/ruoyi'
import { listApplication, addApplication, passApplication, rejectApplication } from '@/api/application/application'
import {getIndustryOptions} from "@/api/system/industry";
import {getApplicationTypeOptions} from "@/api/approval/applyProcess";
import {getUserMessage} from "@/api/system/user";

// 获取应用实例
const { proxy } = getCurrentInstance()

const ApplicationList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const open = ref(false)
const viewDialogVisible = ref(false)
const updateLoading = ref(false)
const dateRange = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    applicant_name: undefined,
    applicant_phone: undefined,
    status: undefined
  },
  rules: {
    applicantName: [
      { required: true, message: "申请人姓名不能为空", trigger: "blur" }
    ],
    applicantPhone: [
      { required: true, message: "手机号码不能为空", trigger: "blur" },
      { pattern: /^1[3-9]\d{9}$/, message: "请输入正确的手机号码", trigger: "blur" }
    ],
    applicantEmail: [
      { required: true, message: "邮箱地址不能为空", trigger: "blur" },
      { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] }
    ],
    status: [
      { required: true, message: "状态不能为空", trigger: "change" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

// 直接在组件中定义字典数据
const sys_normal_disable = ref([
  { value: "0", label: "正常" },
  { value: "1", label: "停用" }
])

const columns = ref([
  { key: 0, label: `用户编号`, visible: true },
  { key: 1, label: `申请人姓名`, visible: true },
  { key: 2, label: `申请人手机号`, visible: true },
  { key: 3, label: `申请人邮箱`, visible: true },
  { key: 4, label: `申请日期`, visible: true },
  { key: 5, label: `申请状态`, visible: true },
  { key: 6, label: `创建时间`, visible: true }
])

const viewForm = reactive({
  applicationId: null,
  status: '',
  enterpriseDescription: '',
  reason: ''
})

const upload = reactive({
  open: false,
  title: "",
  isUploading: false,
  updateSupport: 0,
  headers: { Authorization: "Bearer " + getToken() },
  url:  "http://localhost:8091/api/application/importData"
})

// 模拟 getToken 函数
function getToken() {
  // 这里应该是从你的认证系统获取token的逻辑
  // 为了示例，我们返回一个假的token
  return "fake-token-12345"
}

// 定义状态映射表
const statusMap = {
  pending: '待审批',
  approved: '已批准',
  rejected: '已拒绝',
};

// 查询应用列表
function getList() {
  loading.value = true;
  listApplication(addDateRange(queryParams.value, dateRange.value)).then(response => {
    // 处理数据，将状态值转换为中文
    const processedRecords = response.records.map(record => ({
      ...record,
      status: statusMap[record.status] || record.status, // 如果没有匹配的中文值，保留原值
    }));
    ApplicationList.value = processedRecords;
    total.value = response.total;
    loading.value = false;
  });
}

const role = reactive({
  userMsg: '' // 定义一个响应式对象来存储用户消息
});

//查询当前登录用户
function getUser() {
  getUserMessage().then(response => {
    role.userMsg = response.msg;
  });
}
// 计算属性判断是否为管理员
const isAdmin = computed(() => role.userMsg === 'admin');

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    applicationId: undefined,
    applicantName: undefined,
    applicantPhone: undefined,
    applicantEmail: undefined,
    status: "0",
    enterpriseDescription: undefined
  }
  proxy.resetForm("userRef")
}

// 搜索按钮操作
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

// 重置按钮操作
function resetQuery() {
  dateRange.value = []
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.applicationId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

// 新增按钮操作
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加申请"
}

// 修改按钮操作
function handleUpdate(row) {
  reset()
  const applicationId = row.applicationId || ids.value[0]
  getApplication(applicationId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改申请"
  })
}

// 提交按钮
function submitForm() {
  proxy.$refs["userRef"].validate(valid => {
    if (valid) {
      if (form.value.applicationId != undefined) {
        updateApplication(form.value).then(response => {
          ElMessage.success("修改成功")
          open.value = false
          getList()
        })
      } else {
        addApplication(form.value).then(response => {
          ElMessage.success("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

// 删除按钮操作
function handleDelete(row) {
  const applicationIds = row.applicationId || ids.value
  ElMessageBox.confirm('是否确认删除申请编号为"' + applicationIds + '"的数据项？').then(function() {
    return delApplication(applicationIds)
  }).then(() => {
    getList()
    ElMessage.success("删除成功")
  }).catch(() => {})
}

// 导出按钮操作
function handleExport() {
  proxy.download('api/application/export', {
    ...queryParams.value
  }, `application_${new Date().getTime()}.xlsx`)
}

function handleImport() {
  upload.title = "申请数据导入"
  upload.open = true
}

function importTemplate() {
  proxy.download("api/application/importTemplate", {}, `application_template_${new Date().getTime()}.xlsx`)
}

function handleFileUploadProgress(event, file, fileList) {
  upload.isUploading = true
}

function handleFileSuccess(response, file, fileList) {
  upload.open = false
  upload.isUploading = false
  proxy.$refs["uploadRef"].handleRemove(file)
  ElMessage.success("导入成功")
  getList()
}

function submitFileForm() {
  proxy.$refs["uploadRef"].submit()
}

function openViewDialog(row) {
  viewForm.applicationId = row.applicationId
  viewForm.status = row.status
  viewForm.enterpriseDescription = row.enterpriseDescription
  viewForm.reason = ''
  viewDialogVisible.value = true
}

function updateApplicationStatus() {
  if (!viewForm.status) {
    ElMessage.error("请选择申请状态")
    return
  }
  if (!viewForm.reason.trim()) {
    ElMessage.error(`请输入${viewForm.status}原因`)
    return
  }

  updateLoading.value = true
  const action = viewForm.status === '通过' ? passApplication : rejectApplication
  const actionName = viewForm.status === '通过' ? '通过' : '拒绝'

  ElMessageBox.confirm(`确认${actionName}该申请吗?`).then(function() {
    return action(viewForm)
  }).then(() => {
    ElMessage.success(`${actionName}成功`)
    viewDialogVisible.value = false
    getList()
  }).catch((error) => {
    console.error(`${actionName}申请失败:`, error)
    ElMessage.error(`${actionName}申请失败，请重试!`)
  }).finally(() => {
    updateLoading.value = false
  })
}

// 创建响应式对象
const state = reactive({
  industryOptions: [],
  selectedIndustry: ''
});

// 获取行业选项
function GetIndustryOptions() {
  getIndustryOptions().then(response => {
    // 假设 response 是一个包含多个对象的数组，每个对象都有一个 value 和 label 属性
    state.industryOptions = response.map(item => ({
      value: item.industryName,
      label: item.industryName
    }));
  });
}



// 创建响应式对象
const stateType = reactive({
  typeOptions: [],
  selectedType: ''
});

// 获取申请类型选项
function GetApplicationTypeOptions() {
  getApplicationTypeOptions().then(response => {
    // 假设 response 是一个包含多个对象的数组，每个对象都有一个 value 和 label 属性
    stateType.typeOptions = response.map(item => ({
      value: item.applicationName,
      label: item.applicationName
    }));
  });
}

onMounted(() => {
  getList()
  //查询行业类型
  GetIndustryOptions()
  //查询申请类型
  GetApplicationTypeOptions()
  getUser()
})
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>

