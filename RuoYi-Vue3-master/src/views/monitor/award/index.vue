<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="年份" prop="year">
            <el-input
                v-model="queryParams.year"
                placeholder="请输入年份"
                clearable
                style="width: 240px"
                @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="比赛类型" prop="type">
            <el-select
                v-model="queryParams.type"
                placeholder="请输入比赛类型"
                clearable
                style="width: 240px"
            >
            <el-option
                v-for="option in form.competitionTypeOptions"
                :key="option"
                :label="option"
                :value="option"
            />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
                type="primary"
                plain
                icon="Plus"
                @click="handleAdd"
                v-hasPermi="['system:user:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
                type="danger"
                plain
                icon="Delete"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['system:user:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
                type="info"
                plain
                icon="Upload"
                @click="handleImport"
                v-hasPermi="['system:user:import']"
            >导入</el-button>
          </el-col>
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

        <el-table v-loading="loading" :data="awardList" @selection-change="handleSelectionChange" class="full-width-table">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" v-if="columns[0].visible" >
            <template #default="scope">
              {{scope.$index+1}}
            </template>
          </el-table-column>
          <el-table-column label="年份" align="center" key="year" prop="year" v-if="columns[1].visible" />
          <el-table-column label="比赛类型" align="center" key="type" prop="type" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="获奖企业" align="center" key="enterprise" prop="enterprise" v-if="columns[2].visible" />
          <el-table-column label="比赛" align="center" key="competition" prop="competition" v-if="columns[2].visible" />
          <el-table-column label="比赛等级" align="center" key="level" prop="level" v-if="columns[2].visible" />
          <el-table-column label="补助金额(万)" align="center" key="subsidyAmount" prop="subsidyAmount" v-if="columns[3].visible" />
          <el-table-column label="补助或奖项的描述" align="center" key="description" prop="description" v-if="columns[4].visible" :show-overflow-tooltip="true" />
          <el-table-column label="记录创建时间" align="center" key="createdAt" prop="createdAt" v-if="columns[2].visible" />
          <el-table-column label="记录更新时间" align="center" key="updatedAt" prop="updatedAt" v-if="columns[2].visible" />

          <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
            <template #default="scope">
              <el-tooltip content="修改" placement="top">
                <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:user:edit']"></el-button>
              </el-tooltip>
              <el-tooltip content="删除" placement="top" >
                <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:user:remove']"></el-button>
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
      </el-col>
    </el-row>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form :model="form" :rules="rules" ref="userRef" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="年份" prop="year">
              <el-input v-model="form.year" placeholder="请输入年份" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="比赛" prop="competition">
              <el-select v-model="form.competition" placeholder="请选择比赛名称">
                <el-option
                    v-for="option in form.competitionOptions"
                    :key="option"
                    :label="option"
                    :value="option"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="获奖等级" prop="level">
              <el-input v-model="form.level" placeholder="请输入获奖等级" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="补助金额(万)" prop="subsidyAmount">
              <el-input v-model="form.subsidyAmount" placeholder="请输入补助金额" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="获奖企业" prop="enterprise">
              <el-select v-model="form.enterprise" placeholder="请选择比赛类型">
                <el-option
                    v-for="option in form.enterpriseOptions"
                    :key="option"
                    :label="option"
                    :value="option"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="比赛类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择比赛类型">
                <el-option
                    v-for="option in form.competitionTypeOptions"
                    :key="option"
                    :label="option"
                    :value="option"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="补助或奖项的描述" prop="description">
              <el-input v-model="form.description" placeholder="请输入补助或奖项的描述" type="textarea" />
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
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
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
  </div>
</template>

<script setup name="User">
import { ref, reactive, toRefs, getCurrentInstance, nextTick, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getToken } from "@/utils/auth"
import { changeUserStatus, listUser, resetUserPwd, delUser, getUser, updateUser, addUser, deptTreeSelect } from "@/api/system/user"
import {getEnterpriseOptions} from "../../../api/system/enterprise";
import {addAward, delAward, getAwardByAwardId, listAward, updateAward} from "../../../api/monitor/award";
import {listCompetitionNameAll} from "../../../api/monitor/competitionName";
import {getCompetitionType} from "../../../api/monitor/competitionType";

const router = useRouter()
const { proxy } = getCurrentInstance()
const { sys_normal_disable, sys_user_sex } = proxy.useDict("sys_normal_disable", "sys_user_sex")

const userList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const dateRange = ref([])
const deptName = ref("")
const deptOptions = ref(undefined)
const initPassword = ref(undefined)
const postOptions = ref([])
const roleOptions = ref([])
const awardList = ref([])
// 用户导入参数
const upload = reactive({
  open: false,
  title: "",
  isUploading: false,
  updateSupport: 0,
  headers: { Authorization: "Bearer " + getToken() },
  url: import.meta.env.VITE_APP_BASE_API + "/system/user/importData"
})

// 列显隐信息
const columns = ref([
  { key: 0, label: `用户编号`, visible: true },
  { key: 1, label: `用户名称`, visible: true },
  { key: 2, label: `用户昵称`, visible: true },
  { key: 3, label: `部门`, visible: true },
  { key: 4, label: `手机号码`, visible: true },
  { key: 5, label: `状态`, visible: true },
  { key: 6, label: `创建时间`, visible: true }
])

const data = reactive({
  form: {
    awardId: '',
    year: '',
    level: '',
    type: '',
    competition: '',
    enterprise: '',
    description: '',
    subsidyAmount: '',
    competitionOptions: [],
    competitionTypeOptions: [],
    enterpriseOptions: [],
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userName: undefined,
    year: undefined,
    type: undefined,
    deptId: undefined
  },
  rules: {
    year: [
      { required: true, message: '请选择年份', trigger: 'change' }
    ],
    level: [
      { required: true, message: '请输入比赛等级', trigger: 'blur' }
    ],
    type: [
      { required: true, message: '请输入比赛类型', trigger: 'blur' }
    ],
    subsidyAmount: [
      { required: true, message: '请输入补助金额', trigger: 'blur' }
    ],
    description: [
      { required: true, message: '请输入补助或奖项的描述', trigger: 'blur' }
    ],
    enterprise: [
      { required: true, message: '请输入获奖企业', trigger: 'blur' }
    ],
    competition: [
      { required: true, message: '请输入比赛', trigger: 'blur' }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

// 通过条件过滤节点
const filterNode = (value, data) => {
  if (!value) return true
  return data.label.indexOf(value) !== -1
}

// 根据名称筛选部门树
watch(deptName, val => {
  proxy.$refs["deptTreeRef"].filter(val)
})

// 查询部门下拉树结构
function getDeptTree() {
  deptTreeSelect().then(response => {
    deptOptions.value = response.data
  })
}

// 查询用户列表
const getList = () => {
  loading.value = true
  listAward(queryParams).then(res => {
    awardList.value = res.records.map(item => ({
      ...item,
      createdAt: formatDate(item.createdAt),
      updatedAt: formatDate(item.updatedAt)
    }))
    total.value = res.total
    loading.value = false
  }).catch(error => {
    console.error('获取奖项列表失败:', error)
    loading.value = false
  })
}


// 日期格式化函数
function formatDate(isoString) {
  if (!isoString) return ''
  const date = new Date(isoString)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  const seconds = String(date.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

// 节点单击事件
function handleNodeClick(data) {
  queryParams.value.deptId = data.id
  handleQuery()
}

// 搜索按钮操作
function handleQuery() {
  queryParams.value.pageNum = 1
  queryParams.value.pageSize=10
  getList()
}

// 重置按钮操作
function resetQuery() {
  dateRange.value = []
  proxy.resetForm("queryRef")
  queryParams.value.deptId = undefined
  proxy.$refs.tree.setCurrentKey(null)
  handleQuery()
}

// 删除按钮操作
function handleDelete(row) {
  proxy.$modal.confirm('是否确认删除奖项id为"' + row.awardId + '"的数据项？').then(function () {
    return delAward(row.awardId)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

// 导出按钮操作
function handleExport() {
  proxy.download("/award-details/export", {
    ...queryParams.value,
  }, `user_${new Date().getTime()}.xlsx`)
}

// 用户状态修改
function handleStatusChange(row) {
  let text = row.status === "0" ? "启用" : "停用"
  proxy.$modal.confirm('确认要"' + text + '""' + row.userName + '"用户吗?').then(function () {
    return changeUserStatus(row.userId, row.status)
  }).then(() => {
    proxy.$modal.msgSuccess(text + "成功")
  }).catch(function () {
    row.status = row.status === "0" ? "1" : "0"
  })
}

// 更多操作
function handleCommand(command, row) {
  switch (command) {
    case "handleResetPwd":
      handleResetPwd(row)
      break
    case "handleAuthRole":
      handleAuthRole(row)
      break
    default:
      break
  }
}

// 重置密码按钮操作
function handleResetPwd(row) {
  proxy.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    closeOnClickModal: false,
    inputPattern: /^.{5,20}$/,
    inputErrorMessage: "用户密码长度必须介于 5 和 20 之间",
  }).then(({ value }) => {
    resetUserPwd(row.userId, value).then(response => {
      proxy.$modal.msgSuccess("修改成功，新密码是：" + value)
    })
  }).catch(() => {})
}

// 选择条数
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.userId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

// 导入按钮操作
function handleImport() {
  upload.title = "用户导入"
  upload.open = true
}

// 下载模板操作
function importTemplate() {
  proxy.download("system/user/importTemplate", {}, `user_template_${new Date().getTime()}.xlsx`)
}

// 文件上传中处理
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true
}

// 文件上传成功处理
const handleFileSuccess = (response, file, fileList) => {
  upload.open = false
  upload.isUploading = false
  proxy.$refs["uploadRef"].handleRemove(file)
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true })
  getList()
}

// 提交上传文件
function submitFileForm() {
  proxy.$refs["uploadRef"].submit()
}

// 重置操作表单
function reset() {
  form.value = {
    awardId: null,
    year: null,
    level: null,
    type: null,
    competition: null,
    enterprise: null,
    description: null,
    subsidyAmount: null,
    competitionOptions: form.value.competitionOptions,
    competitionTypeOptions: form.value.competitionTypeOptions,
    enterpriseOptions: form.value.enterpriseOptions,
  }
  // 使用 nextTick 确保 DOM 更新后再重置表单
  nextTick(() => {
    if (proxy.$refs["userRef"]) {
      proxy.$refs["userRef"].resetFields()
    }
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 新增按钮操作
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加奖项"
  form.value.password = initPassword.value
}

// 修改按钮操作
function handleUpdate(row) {
  reset()

  if (row && row.awardId) {
    getAwardByAwardId(row.awardId).then(response => {
      form.value = { ...response,   competitionOptions: form.value.competitionOptions,
        competitionTypeOptions: form.value.competitionTypeOptions,
        enterpriseOptions: form.value.enterpriseOptions, }
      console.log("form.value", form.value)
      open.value = true
      title.value = "修改奖项"
    }).catch(error => {
      console.error("更新奖项时出错：", error)
      proxy.$modal.msgError("更新奖项失败，请重试")
    })
  }
}

// 提交按钮
function submitForm() {
  proxy.$refs["userRef"].validate(valid => {
    if (valid) {
      if (form.value.awardId != undefined) {
        updateAward(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addAward(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

// 查询全部比赛名称
function GetCompetitionNameAll() {
  listCompetitionNameAll().then(res => {
    const competitionNames = res.map(item => item.competitionName)
    form.value.competitionOptions = competitionNames
    console.log("form.value.competitionOptions", form.value.competitionOptions)
  }).catch(error => {
    console.error(error)
  })
}

//查询全部比赛类型
function GetCompetitionTypeAll() {
  getCompetitionType().then(res => {
    const competitionTypes = res.map(item => item.level)
    form.value.competitionTypeOptions = competitionTypes
    console.log("form.value.competitionTypeOptions", form.value.competitionTypeOptions)
  }).catch(error => {
    console.error(error)
  })
}
function getEnterpriseOption() {
  getEnterpriseOptions().then(res => {
    const companyNames = res.map(item => item.companyName)
    form.value.enterpriseOptions = companyNames
    console.log("form.value.enterpriseOptions", form.value.enterpriseOptions)
  }).catch(error => {
    console.error(error)
  })
}

onMounted(() => {
  getDeptTree()
  getList()
  getEnterpriseOption()
  GetCompetitionNameAll()
  GetCompetitionTypeAll()
})
</script>

<style scoped>
.app-container {
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

.full-width-table {
  width: 100%;
}

.el-table {
  margin: 0;
  padding: 0;
}
</style>

