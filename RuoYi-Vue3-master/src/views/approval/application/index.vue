<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--用户数据-->
      <el-col :span="24">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="申请人" prop="applicant_name">
            <el-input
                v-model="queryParams.applicant_name"
                placeholder="请输入申请人姓名"
                clearable
                style="width: 240px"
                @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="applicant_phone">
            <el-input
                v-model="queryParams.applicant_phone"
                placeholder="请输入手机号码"
                clearable
                style="width: 240px"
                @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
                v-model="queryParams.status"
                placeholder="用户状态"
                clearable
                style="width: 240px"
            >
              <el-option
                  v-for="dict in sys_normal_disable"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间" style="width: 308px;">
            <el-date-picker
                v-model="dateRange"
                value-format="YYYY-MM-DD"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
            ></el-date-picker>
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
                type="success"
                plain
                icon="Edit"
                :disabled="single"
                @click="handleUpdate"
                v-hasPermi="['system:user:edit']"
            >修改</el-button>
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

        <el-table v-loading="loading" :data="ApplicationList" @selection-change="handleSelectionChange" class="full-width-table">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="用户编号" align="center" key="applicationId" prop="applicationId" v-if="columns[0].visible" />
          <el-table-column label="申请人姓名" align="center" key="applicantName" prop="applicantName" v-if="columns[1].visible" :show-overflow-tooltip="true"  width="160" />
          <el-table-column label="申请人手机号" align="center" key="applicantPhone" prop="applicantPhone" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="申请人邮箱" align="center" key="applicantEmail" prop="applicantEmail" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="申请日期" align="center" key="applicationDate" prop="applicationDate" v-if="columns[4].visible" width="160" />
          <el-table-column label="申请状态" align="center" key="status" prop="status" v-if="columns[5].visible" width="160" />
          <el-table-column label="创建时间" align="center" key="createdAt" prop="createdAt" v-if="columns[6].visible" width="160">
          </el-table-column>
          <el-table-column label="企业描述" align="center" prop="enterpriseDescription" v-if="columns[7].visible" width="160"></el-table-column>
          <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
            <template #default="scope">
              <el-tooltip content="通过" placement="top" v-if="scope.row.userId !== 1">
                <el-button link type="primary" icon="CircleCheck" @click="openPassDialog(scope.row)" v-hasPermi="['system:user:edit']"></el-button>
              </el-tooltip>
              <el-tooltip content="拒绝" placement="top" v-if="scope.row.userId !== 1">
                <el-button link type="primary" icon="Key" @click="rejectApplicationOperate(scope.row)" v-hasPermi="['system:user:resetPwd']"></el-button>
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
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form :model="form" :rules="rules" ref="userRef" label-width="100px" style="padding: 0 20px;">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="applicantName">
              <el-input v-model="form.applicantName" placeholder="请输入申请人姓名" style="width: 100%;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="applicantPhone">
              <el-input v-model="form.applicantPhone" placeholder="请输入申请人手机号" style="width: 100%;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="邮箱" prop="applicantEmail">
              <el-input v-model="form.applicantEmail" placeholder="请输入申请人邮箱" style="width: 100%;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建时间" prop="createdAt">
              <el-date-picker v-model="form.createdAt" type="datetime" placeholder="选择创建时间" style="width: 100%;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="企业描述" prop="enterpriseDescription">
              <el-input v-model="form.enterpriseDescription" type="textarea" placeholder="请输入企业描述" style="width: 100%;" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer" style="text-align: right; padding-right: 20px;">
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

    <!-- 通过申请对话框 -->
    <el-dialog title="通过申请" v-model="passDialogVisible" width="30%">
      <el-form :model="passForm" label-width="80px">
        <el-form-item label="通过原因">
          <el-input v-model="passForm.reason" type="textarea" :rows="4" placeholder="请输入通过原因"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="passDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmPass">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="User">
import { ref, reactive, toRefs, getCurrentInstance, watch } from 'vue';
import { useRouter } from 'vue-router';
import { getToken } from "@/utils/auth";
import { changeUserStatus, resetUserPwd, delUser, getUser, updateUser, addUser, deptTreeSelect } from "@/api/system/user";
import { listApplication, rejectApplication, passApplication, addApplication } from "@/api/application/application";

const router = useRouter();
const { proxy } = getCurrentInstance();
const { sys_normal_disable, sys_user_sex } = proxy.useDict("sys_normal_disable", "sys_user_sex");

const userList = ref([]);
const ApplicationList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);
const deptName = ref("");
const deptOptions = ref(undefined);
const initPassword = ref(undefined);
const postOptions = ref([]);
const roleOptions = ref([]);

// 新增：通过申请对话框相关数据
const passDialogVisible = ref(false);
const passForm = reactive({
  applicationId: null,
  reason: ''
});

/*** 用户导入参数 */
const upload = reactive({
  // 是否显示弹出层（用户导入）
  open: false,
  // 弹出层标题（用户导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的用户数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/system/user/importData"
});

// 列显隐信息
const columns = ref([
  { key: 0, label: `用户编号`, visible: true },
  { key: 1, label: `申请人姓名`, visible: true },
  { key: 2, label: `申请人手机号`, visible: true },
  { key: 3, label: `申请人邮箱`, visible: true },
  { key: 4, label: `申请日期`, visible: true },
  { key: 5, label: `申请状态`, visible: true },
  { key: 6, label: `申请日期`, visible: true },
  { key: 7, label: `企业描述`, visible: true }
]);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userName: undefined,
    phonenumber: undefined,
    status: undefined,
    deptId: undefined
  },
  rules: {
    applicantName: [
      { required: true, message: "申请人姓名不能为空", trigger: "blur" },
      { min: 2, max: 20, message: "申请人姓名长度必须介于 2 和 20 之间", trigger: "blur" }
    ],
    applicantPhone: [
      { required: true, message: "申请人手机号不能为空", trigger: "blur" },
      { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }
    ],
    applicantEmail: [
      { required: true, message: "申请人邮箱不能为空", trigger: "blur" },
      { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] }
    ],
    createdAt: [
      { required: true, message: "创建时间不能为空", trigger: "change" }
    ],
    enterpriseDescription: [
      { required: true, message: "企业描述不能为空", trigger: "blur" },
      { min: 10, max: 500, message: "企业描述长度必须介于 10 和 500 之间", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询申请列表 */
function getList() {
  loading.value = true;
  listApplication(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
    loading.value = false;
    console.log("res", res);

    // 定义一个函数来转换日期时间格式
    function formatDateTime(dateTimeString) {
      if (!dateTimeString) return '';
      const dateTime = new Date(dateTimeString);
      const year = dateTime.getFullYear();
      const month = String(dateTime.getMonth() + 1).padStart(2, '0');
      const day = String(dateTime.getDate()).padStart(2, '0');
      const hours = String(dateTime.getHours()).padStart(2, '0');
      const minutes = String(dateTime.getMinutes()).padStart(2, '0');
      const seconds = String(dateTime.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }

    // 遍历 res.records 并转换 applicationDate 和 createdAt
    const updatedRecords = res.records.map(item => {
      if (item.applicationDate) {
        item.applicationDate = formatDateTime(item.applicationDate);
      }
      if (item.createdAt) {
        item.createdAt = formatDateTime(item.createdAt);
      }
      // 修改 status 为 "pending" 的项
      if (item.status === 'pending') {
        item.status = '待定';
      }
      if (item.status === 'approved') {
        item.status = '通过';
      }
      if (item.status === 'rejected') {
        item.status = '拒绝';
      }
      return item;
    });

    ApplicationList.value = updatedRecords;
    total.value = res.total;
  });
}

/**
 * 打开通过申请对话框
 */
function openPassDialog(row) {
  passForm.applicationId = parseInt(row.applicationId, 10);
  passDialogVisible.value = true;
}

/**
 * 确认通过申请
 */
function confirmPass() {
  if (!passForm.reason.trim()) {
    proxy.$modal.msgError("请输入通过原因");
    return;
  }

  proxy.$modal.confirm('确认通过该申请吗?').then(function () {
    // 调用通过申请的 API
    return passApplication(passForm);
  }).then(() => {
    proxy.$modal.msgSuccess("通过成功");
    passDialogVisible.value = false;
    passForm.reason=""
    getList(); // 刷新列表
  }).catch((error) => {
    console.error("通过申请失败:", error);
    proxy.$modal.msgError("通过申请失败，请重试!");
  });
}

/**
 * 拒绝申请操作
 */
function rejectApplicationOperate(row) {
  const applicationId = parseInt(row.applicationId, 10);
  console.log("row.applicationId", row.applicationId);
  proxy.$modal.confirm('确认拒绝该申请吗?').then(function () {
    return rejectApplication(applicationId);
  }).then(() => {
    proxy.$modal.msgSuccess("拒绝成功");
    getList();
  }).catch((error) => {
    console.error("拒绝申请失败:", error);
    proxy.$modal.msgError("拒绝申请失败，请重试!");
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  queryParams.value.deptId = undefined;
  handleQuery();
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  getUser().then(response => {
    postOptions.value = response.posts;
    roleOptions.value = response.roles;
    open.value = true;
    title.value = "添加申请";
    form.value.password = initPassword.value;
  });
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const userId = row.userId || ids.value[0];
  getUser(userId).then(response => {
    form.value = response.data;
    postOptions.value = response.posts;
    roleOptions.value = response.roles;
    open.value = true;
    title.value = "修改用户";
    form.value.password = "";
  });
}

/** 重置密码按钮操作 */
function handleResetPwd(row) {
  proxy.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    closeOnClickModal: false,
    inputPattern: /^.{5,20}$/,
    inputErrorMessage: "用户密码长度必须介于 5 和 20 之间",
  }).then(({ value }) => {
    resetUserPwd(row.userId, value).then(response => {
      proxy.$modal.msgSuccess("修改成功，新密码是：" + value);
    });
  }).catch(() => {});
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["userRef"].validate(valid => {
    if (valid) {
      if (form.value.userId != undefined) {
        updateUser(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addApplication(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const userIds = row.userId || ids.value;
  proxy.$modal.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(function() {
    return delUser(userIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download("system/user/export", {
    ...queryParams.value
  }, `user_${new Date().getTime()}.xlsx`);
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "用户导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download("system/user/importTemplate", {}, `user_template_${new Date().getTime()}.xlsx`);
}

/**文件上传中处理 */
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true;
};

/** 文件上传成功处理 */
const handleFileSuccess = (response, file, fileList) => {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].handleRemove(file);
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
};

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
}

/** 重置操作表单 */
function reset() {
  form.value = {
    userId: undefined,
    deptId: undefined,
    userName: undefined,
    nickName: undefined,
    password: undefined,
    phonenumber: undefined,
    email: undefined,
    sex: undefined,
    status: "0",
    remark: undefined,
    postIds: [],
    roleIds: []
  };
  proxy.resetForm("userRef");
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}

// 初始化
getList();
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

/* If you want to remove any default padding/margin */
.el-table {
  margin: 0;
  padding: 0;
}
</style>

