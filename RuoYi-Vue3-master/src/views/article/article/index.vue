<template>
  <div class="app-container">
    <div v-if="!showAddForm">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="分类" prop="category">
              <el-select
                  v-model="queryParams.category"
                  placeholder="请输入分类名称"
                  clearable
                  style="width: 240px"
              >
                <el-option
                    v-for="option in form.categoryOptions"
                    :key="option"
                    :label="option"
                    :value="option"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="标签" prop="primaryTag">
              <el-select
                  v-model="queryParams.primaryTag"
                  placeholder="请输入标签名称"
                  clearable
                  style="width: 240px"
              >
                <el-option
                    v-for="option in form.primaryTagOptions"
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
                  @click="handleAddNew"
                  v-hasPermi="['system:article:add']"
              >新增</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                  type="danger"
                  plain
                  icon="Delete"
                  :disabled="multiple"
                  @click="handleDeleteList"
                  v-hasPermi="['system:article:remove']"
              >删除</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                  type="warning"
                  plain
                  icon="Download"
                  @click="handleExport"
                  v-hasPermi="['system:article:export']"
              >导出</el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
          </el-row>

          <el-table v-loading="loading" :data="articleList" @selection-change="handleSelectionChange" class="full-width-table">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="序号" align="center" v-if="columns[0].visible">
              <template #default="scope">
                {{scope.$index+1}}
              </template>
            </el-table-column>
            <el-table-column label="分类" align="center" key="category" prop="category"  :show-overflow-tooltip="true" />
            <el-table-column label="标签" align="center" key="primaryTag" prop="primaryTag"  :show-overflow-tooltip="true" />
            <el-table-column label="文章标题" align="center" key="title" prop="title"  :show-overflow-tooltip="true" />
            <el-table-column label="文章内容" align="center" key="content" prop="content"  :show-overflow-tooltip="true" />
            <el-table-column label="发布日期" align="center" key="publishDate" prop="publishDate" width="120">
              <template #default="scope">
                <span>{{ scope.row.publishDate }}</span>
              </template>
            </el-table-column>
            <el-table-column label="截止时间" align="center" key="publishDate" prop="publishDate" width="120">
              <template #default="scope">
                <span>{{ scope.row.deadlineDate }}</span>
              </template>
            </el-table-column>
            <el-table-column label="文章状态" align="center" key="status" prop="status">
            </el-table-column>
            <el-table-column label="创建时间" align="center" prop="createdAt"  width="160">
              <template #default="scope">
                <span>{{ parseTime(scope.row.createdAt) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="更新时间" align="center" prop="updatedAt" width="160">
              <template #default="scope">
                <span>{{ parseTime(scope.row.updatedAt) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
              <template #default="scope">
                <el-tooltip content="修改" placement="top">
                  <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" ></el-button>
                </el-tooltip>
                <el-tooltip content="删除" placement="top">
                  <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"></el-button>
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
    </div>

    <div v-else>
      <h2>新增文章</h2>
      <el-form :model="articleForm" :rules="articleFormRules" ref="articleFormRef" label-width="100px">
        <el-form-item label="分类" prop="category">
          <el-select v-model="form.category" placeholder="请选择分类">
            <el-option
                v-for="option in form.categoryOptions"
                :key="option"
                :label="option"
                :value="option"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="标签" prop="primaryTag">
          <el-select v-model="form.primaryTag" placeholder="请选择标签">
            <el-option
                v-for="option in form.primaryTagOptions"
                :key="option"
                :label="option"
                :value="option"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="文章标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入文章标题" />
        </el-form-item>
        <el-form-item label="文章内容" prop="content">
          <el-input v-model="form.content" type="textarea" rows="10" placeholder="请输入文章内容" />
        </el-form-item>
        <el-form-item label="发布日期" prop="publishDate">
          <el-date-picker v-model="form.publishDate" type="date" placeholder="选择发布日期" />
        </el-form-item>
        <el-form-item label="截止时间" prop="deadlineDate">
          <el-date-picker v-model="form.deadlineDate" type="date" placeholder="选择截止时间" />
        </el-form-item>
        <el-form-item label="文章状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择文章状态">
            <el-option label="草稿" value="草稿" />
            <el-option label="已发布" value="已发布" />
            <el-option label="已归档" value="已归档" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitArticleForm">新增</el-button>
          <el-button @click="handleBackToList">返回</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form :model="form" :rules="rules" ref="userRef" label-width="80px">
        <el-row>

          <el-col :span="12">
            <el-form-item label="分类" prop="category">
              <el-select v-model="form.category" placeholder="请选择分类">
                <el-option
                    v-for="option in form.categoryOptions"
                    :key="option"
                    :label="option"
                    :value="option"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="文章状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择文章状态">
                <el-option label="草稿" value="草稿" />
                <el-option label="已发布" value="已发布" />
                <el-option label="已归档" value="已归档" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标签" prop="primaryTag">
              <el-select v-model="form.primaryTag" placeholder="请选择标签">
                <el-option
                    v-for="option in form.primaryTagOptions"
                    :key="option"
                    :label="option"
                    :value="option"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="文章标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入文章标题" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="文章内容" prop="content">
              <el-input rows="10"   v-model="form.content" type="textarea" placeholder="请输入文章内容" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="发布日期" prop="publishDate">
              <el-date-picker v-model="form.publishDate" type="date" placeholder="选择发布日期" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="截止时间" prop="publishDate">
              <el-date-picker v-model="form.deadlineDate" type="date" placeholder="选择截止时间" />
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
import {addArticle, delArticle, getArticle, listArticle, updatePolicyArticle} from "@/api/article/article";
import {onMounted, ref} from "vue";
import {getPrimaryTagOptions} from "../../../api/article/tag";
import {getCategoryOptions} from "../../../api/article/type";
import {deleteIndustryList} from "../../../api/system/industry";
import {deletePolicyArticles} from "../../../api/article/article";

const router = useRouter();
const { proxy } = getCurrentInstance();
const { sys_normal_disable, sys_user_sex } = proxy.useDict("sys_normal_disable", "sys_user_sex");
const articleList=ref([]);
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
const showAddForm = ref(false);

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

const data = reactive({
  form: {
    articleId: undefined,
    category: undefined,
    primaryTag: undefined,
    title: undefined,
    content: undefined,
    publishDate: undefined,
    deadlineDate: undefined,
    status: "草稿",
    categoryOptions: [],
    primaryTagOptions: []
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
    articleId: [{ required: false, message: "文章ID不能为空", trigger: "blur" }],
    category: [{ required: true, message: "分类不能为空", trigger: "blur" }],
    primaryTag: [{ required: true, message: "主要标签不能为空", trigger: "blur" }],
    title: [{ required: true, message: "文章标题不能为空", trigger: "blur" }],
    content: [{ required: true, message: "文章内容不能为空", trigger: "blur" }],
    publishDate: [{ required: true, message: "发布日期不能为空", trigger: "change" }],
    deadlineDate: [{ required: true, message: "截止时间不能为空", trigger: "change" }],
    status: [{ required: true, message: "文章状态不能为空", trigger: "change" }]
  },
  articleForm: {
    category: null,
    primaryTag: null,
    title: null,
    content: null,
    publishDate: null,
    deadlineDate: null,
    status: null
  },
  articleFormRules: {
    category: [{ required: true, message: "分类不能为空", trigger: "blur" }],
    primaryTag: [{ required: true, message: "主要标签不能为空", trigger: "blur" }],
    title: [{ required: true, message: "文章标题不能为空", trigger: "blur" }],
    content: [{ required: true, message: "文章内容不能为空", trigger: "blur" }],
    publishDate: [{ required: true, message: "发布日期不能为空", trigger: "change" }],
    deadlineDate: [{ required: true, message: "截止时间不能为空", trigger: "change" }],
    status: [{ required: true, message: "文章状态不能为空", trigger: "change" }]
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
/** 查询用户列表 */
function getList() {
  loading.value = true;
  listArticle(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
    loading.value = false;
    articleList.value = res.records;
    total.value = res.total;
  });
};
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
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  queryParams.value.deptId = undefined;
  handleQuery();
  getList()
};
/** 批量删除按钮操作 */
function handleDeleteList() {
  const selectedRows = getSelectedRows(); // 获取选中的行数据
  if (!selectedRows || selectedRows.length === 0) {
    proxy.$modal.msgError("请选择要删除的数据项");
    return;
  }

  const articleIds = selectedRows.map(row => row.articleId);
  proxy.$modal.confirm(`是否确认删除标签编号为"${articleIds}"的数据项？`).then(function () {
    return deletePolicyArticles(articleIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
};
/** 删除按钮操作 */
function handleDelete(row) {

  proxy.$modal.confirm('是否确认删除用户编号为"' + row.articleId + '"的数据项？').then(function () {
    return delArticle(row.articleId);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
};
/** 导出按钮操作 */
function handleExport() {
  proxy.download("/policy-articles/export", {
    ...queryParams.value,
  },`policy_article_${new Date().getTime()}.xlsx`);
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
const multipleTable = ref(null);
const multipleSelection = ref([]);
/** 选择条数  */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.userId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
  multipleSelection.value = selection; // 移除多余的点
}

// 获取选中的行数据
const getSelectedRows = () => {
  return multipleSelection.value;
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
    userId: undefined,
    deptId: undefined,
    userName: undefined,
    nickName: undefined,
    password: undefined,
    phonenumber: undefined,
    email: undefined,
    sex: undefined,
    status: "草稿",
    remark: undefined,
    postIds: [],
    roleIds: [],
    categoryOptions: form.value.categoryOptions,
    primaryTagOptions: form.value.primaryTagOptions
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
    title.value = "添加文章";
    form.value.password = initPassword.value;
  });
};

/** 新增按钮操作 */
/** 新增按钮操作 */
function handleAddNew() {
  showAddForm.value = true;
  form.category = undefined;
  form.primaryTag = undefined;
  form.title = undefined;
  form.content = undefined;
  form.publishDate = undefined;
  form.deadlineDate = undefined;
  form.status = "草稿";
  console.log("触发了吗")
  reset()
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();

  if (row && row.articleId) {
    getArticle(row.articleId).then(response => {
      console.log("response是是是", response)
      if (response) {
        form.value = {
          ...response,
          categoryOptions: form.value.categoryOptions || [],
          primaryTagOptions: form.value.primaryTagOptions || []
        }
        console.log("form.value", form.value)
        open.value = true
        title.value = "修改文章"
      } else {
        proxy.$modal.msgError("获取文章数据失败，请重试")
      }
    }).catch(error => {
      console.error("修改文章时出错：", error)
      proxy.$modal.msgError("修改文章失败，请重试")
    })
  }
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["userRef"].validate(valid => {
    if (valid) {
      if (form.value.articleId != undefined) {
        updatePolicyArticle(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addArticle(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};

// 查询全部比赛名称
function getCategoryOption() {
  getCategoryOptions().then(res => {
    const categoryNames = res.map(item => item.categoryName)
    form.value.categoryOptions = categoryNames
    console.log("form.value.categoryOptions", form.value.categoryOptions)
  }).catch(error => {
    console.error(error)
  })
}

//查询全部比赛类型
function getPrimaryTagOption() {
  getPrimaryTagOptions().then(res => {
    const primaryTags = res.map(item => item.tagName)
    form.value.primaryTagOptions = primaryTags
    console.log("form.value.primaryTagOptions", form.value.primaryTagOptions)
  }).catch(error => {
    console.error(error)
  })
}

//返回到表格
function handleBackToList(){
  showAddForm.value = false;
}
//新增文章
function submitArticleForm(){

  showAddForm.value = false;
  addArticle(form.value).then(response => {
    proxy.$modal.msgSuccess("新增成功");
    open.value = false;
    getList();
  });
  form.category = undefined;
  form.primaryTag = undefined;
  form.title = undefined;
  form.content = undefined;
  form.publishDate = undefined;
  form.deadlineDate = undefined;
  form.status = "草稿";
}


onMounted(() => {
  getDeptTree()
  getList()
  getCategoryOption()
  getPrimaryTagOption()
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