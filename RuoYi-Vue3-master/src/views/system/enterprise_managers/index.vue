<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--用户数据-->
      <el-col :span="24">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="姓名" prop="name">
            <el-input
                v-model="queryParams.name"
                placeholder="请输入姓名"
                clearable
                style="width: 240px"
                @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="企业" prop="enterprise">
            <el-select
                v-model="queryParams.enterprise"
                placeholder="请输入企业"
                clearable
                style="width: 240px"
            >
            <el-option
                v-for="option in form.enterpriseOptions"
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
                type="warning"
                plain
                icon="Download"
                @click="handleExport"
                v-hasPermi="['system:user:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange" class="full-width-table">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" v-if="columns[0].visible" >
            <template #default="scope">
              {{scope.$index+1}}
            </template>
          </el-table-column>
          <el-table-column label="姓名" align="center" key="name" prop="name" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="性别" align="center" key="gender" prop="gender" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="身份证号" align="center" key="idCard" prop="idCard" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="生源地" align="center" key="birthplace" prop="birthplace" v-if="columns[4].visible" width="120" />
          <el-table-column label="政治面貌" align="center" key="politicalStatus" prop="politicalStatus" v-if="columns[4].visible" width="120" />
          <el-table-column label="毕业院校" align="center" key="almaMater" prop="almaMater" v-if="columns[4].visible" width="120" />
          <el-table-column label="专业" align="center" key="major" prop="major" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="学历" align="center" key="educationLevel" prop="educationLevel" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="职位" align="center" key="position" prop="position" v-if="columns[4].visible" width="120" />
          <el-table-column label="公司名称" align="center" key="company" prop="company" v-if="columns[4].visible" width="120" />
          <el-table-column label="联系方式" align="center" key="contactInfo" prop="contactInfo" v-if="columns[4].visible" width="120" />
          <el-table-column label="入职日期" align="center" prop="hireDate" v-if="columns[6].visible" width="160">
            <template #default="scope">
              <span>{{ parseTime(scope.row.hireDate) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
            <template #default="scope">
              <el-tooltip content="修改" placement="top" v-if="scope.row.userId !== 1">
                <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:user:edit']"></el-button>
              </el-tooltip>
              <el-tooltip content="删除" placement="top" v-if="scope.row.userId !== 1">
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
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
  <el-form :model="form" :rules="rules" ref="userRef" label-width="80px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" maxlength="30" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择">
            <el-option
                v-for="dict in sys_user_gender"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="联系方式" prop="contactInfo">
          <el-input v-model="form.contactInfo" placeholder="请输入联系方式" maxlength="30" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" maxlength="18" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="生源地" prop="birthplace">
          <el-input v-model="form.birthplace" placeholder="请输入生源地" maxlength="50" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="政治面貌" prop="politicalStatus">
          <el-select v-model="form.politicalStatus" placeholder="请选择">
            <el-option
                v-for="dict in sys_political_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="专业" prop="major">
          <el-input v-model="form.major" placeholder="请输入专业" maxlength="50" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="学历" prop="educationLevel">
          <el-select v-model="form.educationLevel" placeholder="请选择学历">
            <el-option label="本科" value="Undergraduate"></el-option>
            <el-option label="硕士" value="Master"></el-option>
            <el-option label="博士" value="Doctoral"></el-option>
            <el-option label="副教授" value="Associate"></el-option>
            <el-option label="其他" value="Other"></el-option>
          </el-select>
        </el-form-item>
      </el-col>

    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="毕业院校" prop="almaMater">
          <el-input v-model="form.almaMater" placeholder="请输入毕业院校" maxlength="50" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="毕业时间" prop="graduationDate">
          <el-date-picker v-model="form.graduationDate" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="公司名称" prop="company">
          <el-select v-model="form.company" placeholder="请选择公司名称">
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
        <el-form-item label="职位" prop="position">
          <el-input v-model="form.position" placeholder="请输入职位" maxlength="50" />
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
import { getToken } from "@/utils/auth";
import { changeUserStatus, listUser, resetUserPwd, delUser, getUser, updateUser, addUser, deptTreeSelect } from "@/api/system/user";
import {
  addEnterprise_managers,
  delEnterprise_managers, getEnterprise_managers,
  listEnterprise_managers, updateEnterprise_managers
} from "@/api/system/enterprise_managers";
import {getCurrentInstance, reactive, ref} from "vue";
import {toRefs} from "@vueuse/core";
import {getEnterpriseOptions} from "../../../api/system/enterprise";

const router = useRouter();
const { proxy } = getCurrentInstance();
const { sys_normal_disable, sys_user_sex } = proxy.useDict("sys_normal_disable", "sys_user_sex");

const userList = ref([]);
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
  { key: 1, label: `用户名称`, visible: true },
  { key: 2, label: `用户昵称`, visible: true },
  { key: 3, label: `部门`, visible: true },
  { key: 4, label: `手机号码`, visible: true },
  { key: 5, label: `状态`, visible: true },
  { key: 6, label: `创建时间`, visible: true }
]);

//性别选择
const sys_user_gender = ref([
  { value: 'Male', label: '男' },
  { value: 'Female', label: '女' }
]);

//政治面貌

const sys_political_status = ref([
  { value: '中共党员', label: '中共党员' },
  { value: '共青团员', label: '共青团员' },
  { value: '群众', label: '群众' },
  { value: '民主党派', label: '民主党派' }
]);

const data = reactive({
  form: {
    managerId: null,
    name: null,
    gender: null,
    idCard: null,
    birthplace: null,
    politicalStatus: null,
    graduationDate: null,
    almaMater: null,
    major: null,
    educationLevel: null,
    position: null,
    company: null,
    contactInfo: null,
    hireDate: null,
    createdAt: null,
    updatedAt: null,
    enterprise: '',
    enterpriseOptions: [],
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userName: undefined,
    phonenumber: undefined,
    status: undefined,
    deptId: undefined
  },
  rules: {
    managerId: [{ required: false, message: "管理者ID不可更改", trigger: "blur" }],
    name: [{ required: true, message: "姓名不能为空", trigger: "blur" }, { min: 2, max: 30, message: "姓名长度必须介于 2 和 30 之间", trigger: "blur" }],
    gender: [{ required: true, message: "性别不能为空", trigger: "change" }],
    idCard: [{ required: true, message: "身份证号不能为空", trigger: "blur" }],
    birthplace: [{ required: false, message: "生源地可以为空", trigger: "blur" }, { max: 50, message: "生源地长度不能超过 50 个字符", trigger: "blur" }],
    politicalStatus: [{ required: false, message: "政治面貌可以为空", trigger: "change" }],
    almaMater: [{ required: false, message: "毕业院校可以为空", trigger: "blur" }, { max: 50, message: "毕业院校长度不能超过 50 个字符", trigger: "blur" }],
    createTime: [{ required: false, message: "创建时间不可更改", trigger: "blur" }],
    remark: [{ required: false, message: "备注可以为空", trigger: "blur" }],
    company: [{ required: true, message: "公司名称不能为空", trigger: "change" }] // 新增的公司名称验证规则
  }

});

const { queryParams, form, rules } = toRefs(data);

/** 通过条件过滤节点  */
const filterNode = (value, data) => {
  if (!value) return true;
  return data.label.indexOf(value) !== -1;
};
/** 根据名称筛选部门树 */
watch(deptName, val => {
  proxy.$refs["deptTreeRef"].filter(val);
});
/** 查询部门下拉树结构 */
function getDeptTree() {
  deptTreeSelect().then(response => {
    deptOptions.value = response.data;
  });
};
/** 查询管理员管理列表 */
function getList() {
  loading.value = true;
  listEnterprise_managers(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
    loading.value = false;
    console.log("企业管理者", res.records);

    // 定义教育水平的映射对象
    const educationLevelMap = {
      Undergraduate: '本科',
      Master: '硕士',
      Doctoral: '博士',
      Associate: '专科',
      Other: '其他'
    };

    // 处理 gender 和 hireDate 字段，并转换 educationLevel
    const transformedRecords = res.records.map(item => ({
      ...item,
      gender: item.gender === 'Male' ? '男' : item.gender === 'Female' ? '女' : item.gender,
      hireDate: formatDate(item.hireDate),
      educationLevel: educationLevelMap[item.educationLevel] || item.educationLevel
    }));

    userList.value = transformedRecords;
    total.value = res.total;
  });
};


function formatDate(isoString) {
  const date = new Date(isoString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}
/** 节点单击事件 */
function handleNodeClick(data) {
  queryParams.value.deptId = data.id;
  handleQuery();
};
/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
};
/** 重置按钮操作 */
/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  queryParams.value = {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    enterprise: undefined
  };
  // 移除了 proxy.$refs.tree.setCurrentKey(null); 这行代码
  getList();
}
/** 删除按钮操作 */
function handleDelete(row) {

  proxy.$modal.confirm('是否确认删除管理者编号为"' + row.managerId + '"的数据项？').then(function () {
    return delEnterprise_managers(row.managerId);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
};
/** 导出按钮操作 */
function handleExport() {
  proxy.download("/enterprise-managers/export", {
    ...queryParams.value,
  },`enterprise-managers_${new Date().getTime()}.xlsx`);
};
/** 用户状态修改  */
function handleStatusChange(row) {
  let text = row.status === "0" ? "启用" : "停用";
  proxy.$modal.confirm('确认要"' + text + '""' + row.userName + '"用户吗?').then(function () {
    return changeUserStatus(row.userId, row.status);
  }).then(() => {
    proxy.$modal.msgSuccess(text + "成功");
  }).catch(function () {
    row.status = row.status === "0" ? "1" : "0";
  });
};
/** 更多操作 */
function handleCommand(command, row) {
  switch (command) {
    case "handleResetPwd":
      handleResetPwd(row);
      break;
    case "handleAuthRole":
      handleAuthRole(row);
      break;
    default:
      break;
  }
};
/** 跳转角色分配 */
function handleAuthRole(row) {
  const userId = row.userId;
  router.push("/system/user-auth/role/" + userId);
};
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
};
/** 选择条数  */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.userId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
};
/** 导入按钮操作 */
function handleImport() {
  upload.title = "用户导入";
  upload.open = true;
};
/** 下载模板操作 */
function importTemplate() {
  proxy.download("system/user/importTemplate", {
  }, `user_template_${new Date().getTime()}.xlsx`);
};
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
};
/** 重置操作表单 */
function reset() {
  form.value = {
    managerId: null,
    name: null,
    gender: null,
    idCard: null,
    birthplace: null,
    politicalStatus: null,
    graduationDate: null,
    almaMater: null,
    major: null,
    educationLevel: null,
    position: null,
    company: null,
    contactInfo: null,
    hireDate: null,
    createdAt: null,
    updatedAt: null,
    enterprise: null,
    enterpriseOptions: form.value.enterpriseOptions,
  };
  proxy.resetForm("userRef");
};
/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
};
/** 新增按钮操作 */
function handleAdd() {
  reset();
  getUser().then(response => {
    postOptions.value = response.posts;
    roleOptions.value = response.roles;
    open.value = true;
    title.value = "添加企业管理者";
    form.value.password = initPassword.value;
  });
};
/** 修改按钮操作 */
function handleUpdate(row) {
    // reset();

  getEnterprise_managers( row.managerId).then(response => {
     form.value = response;
    console.log("response",response)
    open.value = true;
    title.value = "修改企业管理者";
    form.password = "";
  });
};
/** 提交按钮 */
function submitForm() {
  proxy.$refs["userRef"].validate(valid => {
    if (valid) {
      if (form.value.managerId != undefined) {
        updateEnterprise_managers(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addEnterprise_managers(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};
function getEnterpriseOption() {
  getEnterpriseOptions().then(res => {
    const companyNames = res.map(item => item.companyName)
    form.value.enterpriseOptions = companyNames
    console.log("form.value.enterpriseOptions", form.value.enterpriseOptions)
  }).catch(error => {
    console.error(error)
  })
}
getDeptTree();
getList();
getEnterpriseOption()
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