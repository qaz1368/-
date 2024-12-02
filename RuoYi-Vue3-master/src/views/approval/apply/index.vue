<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--用户数据-->
      <el-col :span="24">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
                type="warning"
                plain
                icon="Download"
                @click="handleExport"

            >导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange" class="full-width-table">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" v-if="columns[0].visible">
            <template #default="scope">
              {{scope.$index+1}}
            </template>
          </el-table-column>
          <el-table-column label="申请类型" align="center" key="applicationType" prop="applicationType" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="申请人姓名" align="center" key="applicantName" prop="applicantName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="流程顺序" align="center" key="sequence" prop="sequence" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="审批状态" align="center" key="approvalStatus" prop="approvalStatus" v-if="columns[4].visible" width="120" />
          <el-table-column label="审批日期" align="center" key="createdAt" prop="createdAt" v-if="columns[2].visible" :show-overflow-tooltip="true" >
            <template #default="scope">
              <span>{{ parseTime(scope.row.approvalDate) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="审批备注" align="center" key="comments" prop="comments" v-if="columns[4].visible" width="120" />
          <el-table-column label="创建时间" align="center" key="createdAt" prop="createdAt" v-if="columns[2].visible" :show-overflow-tooltip="true" >
            <template #default="scope">
              <span>{{ parseTime(scope.row.createdAt) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="更新时间" align="center" key="updatedAt" prop="updatedAt" v-if="columns[3].visible" :show-overflow-tooltip="true" >
            <template #default="scope">
              <span>{{ parseTime(scope.row.updatedAt) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
            <template #default="scope">
<!--              <el-tooltip content="修改" placement="top" v-if="scope.row.userId !== 1">-->
<!--                <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:user:edit']"></el-button>-->
<!--              </el-tooltip>-->
              <el-tooltip content="删除" placement="top" v-if="scope.row.userId !== 1">
                <el-button v-if="!isAdmin" link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:industry:edit']"></el-button>
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
            <el-form-item label="申请Id" prop="applicationId">
              <el-input v-model="form.applicationId" placeholder="请输入申请Id" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请类型" prop="applicationType">
              <el-select v-model="form.applicationType" placeholder="请选择申请类型">
                <el-option
                    v-for="option in form.applicationTypeOptions"
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
            <el-form-item label="流程顺序" prop="sequence">
              <el-input v-model="form.sequence" placeholder="请输入流程顺序" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批状态" prop="approvalStatus">
              <!-- 使用 el-select 组件 -->
              <el-select v-model="form.approvalStatus" placeholder="请选择审批状态">
                <el-option
                    v-for="item in approvalStatusOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="审批备注" prop="comments">
              <el-input v-model="form.comments" placeholder="请输入审批备注" maxlength="50" />
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

  </div>
</template>

<script setup name="User">
import { getToken } from "@/utils/auth";
import {

  deptTreeSelect,
  getUserMessage
} from "@/api/system/user";
import {

  listApply,
  addApply,
  updateApply,
  delApply, getApply
} from "../../../api/approval/apply";
import {getApplicationTypeOptions, getApprovalProcessById} from "../../../api/approval/applyProcess";
import {computed, onMounted, reactive} from "vue";

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
  { key: 1, label: `申请人姓名`, visible: true },
  { key: 2, label: `申请人手机号`, visible: true },
  { key: 3, label: `申请人邮箱`, visible: true },
  { key: 4, label: `申请日期`, visible: true },
  { key: 5, label: `申请状态`, visible: true },
  { key: 6, label: `申请日期`, visible: true },
  { key: 7, label: `企业描述`, visible: true }
]);

const data = reactive({
  form: {
    approvalId:null,
    applicationId: null,
    processId: null,
    sequence: null,
    approvalStatus: null,
    departmentId: null,
    approvalDate: null,
    comments: null,
    createdAt: null,
    updatedAt: null,
    applicationType: null,
    applicantName: null,
    department: null,
    applicantNameOptions:[],
    applicationTypeOptions: [],
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
    sequence: [
      { required: true, message: '请输入流程顺序', trigger: 'blur' },
      { pattern: /^\d+$/, message: '流程顺序只能输入数字', trigger: 'blur' }
    ],
    approvalStatus: [{ required: true, message: '请选择审批状态', trigger: 'change' }],
    comments: [{ required: true, message: '请输入审批备注', trigger: 'blur' }]
  },

});
const approvalStatusOptions = [
  { value: 0, label: '拒绝' },
  { value: 1, label: '通过' },
  { value: 2, label: '待定' }
];
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
/** 查询用户列表 */
// 定义审批状态的映射对象
const approvalStatusMap = {
  0: '拒绝',
  1: '通过',
  2: '待定'
};

function getList() {
  loading.value = true;
  listApply(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
    console.log("审批记录", res.records);

    // 遍历记录，将审批状态转换为中文
    const transformedRecords = res.records.map(record => ({
      ...record,
      approvalStatus: approvalStatusMap[record.approvalStatus] || '未知状态'
    }));

    loading.value = false;
    userList.value = transformedRecords;
    total.value = res.total;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
};
/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  queryParams.value.deptId = undefined;
  proxy.$refs.tree.setCurrentKey(null);
  handleQuery();
};


/** 删除按钮操作 */
function handleDelete(row) {
  proxy.$modal.confirm('是否确认删除用户编号为"' + row.approvalId + '"的数据项？').then(function () {
    return delApply(row.approvalId);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
};
/** 导出按钮操作 */
function handleExport() {
  proxy.download("/api/approval/export", {
    ...queryParams.value,
  },`approval_${new Date().getTime()}.xlsx`);
};




/** 选择条数  */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.userId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
};


/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
};
/** 重置操作表单 */
function reset() {
  form.value = {
    approvalId:null,
    applicationId: null,
    processId: null,
    sequence: null,
    approvalStatus: null,
    departmentId: null,
    approvalDate: null,
    comments: null,
    createdAt: null,
    updatedAt: null,
    applicationType: null,
    applicantName: null,
    department: null,
    applicantNameOptions: form.value.applicantNameOptions,
    applicationTypeOptions: form.value.applicationTypeOptions,
  };
  proxy.resetForm("userRef");
};
/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
};
/** 新增按钮操作 */
/** 修改按钮操作 */


/** 提交按钮 */
function submitForm() {
  proxy.$refs["userRef"].validate(valid => {
    if (valid) {
      if (form.value.approvalId != undefined) {
        updateApply(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addApply(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};
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

function getApplicationTypeOptions1() {
  getApplicationTypeOptions().then(res => {
    const applicationNames = res.map(item => item.applicationName)
    form.value.applicationTypeOptions = applicationNames
    console.log("form.value.applicationTypeOptions", form.value.applicationTypeOptions)
  }).catch(error => {
    console.error(error)
  })
}

onMounted(() => {
  getDeptTree()
  getList()
  getApplicationTypeOptions1()
  getUser()
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

/* If you want to remove any default padding/margin */
.el-table {
  margin: 0;
  padding: 0;
}
</style>