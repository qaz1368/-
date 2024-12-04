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
            <el-table-column label="培训时间" align="center" key="content" prop="time" >
              <template #default="scope">
                {{"2024/11/20 00:00"}}
              </template>
            </el-table-column>
            <el-table-column label="发布时间" align="center" key="publishDate" prop="publishDate" width="120">
              <template #default="scope">
                <span>{{ new Date(scope.row.publishDate).toLocaleString() }}</span>
              </template>
            </el-table-column>
            <el-table-column label="截止时间" align="center" key="publishDate" prop="publishDate" width="120">
              <template #default="scope">
                <span>{{ new Date(scope.row.deadlineDate ).toLocaleString() }}</span>
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
      <el-form :model="form" ref="articleFormRef" label-width="100px" :rules="rules">
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
        <!-- 新增的培训时间选择器 -->
        <el-form-item v-if="form.category === '培训信息'" label="培训时间" prop="trainingTime">
          <el-date-picker
              v-model="form.trainingTime"
              type="datetime"
              placeholder="选择培训时间"
          />
        </el-form-item>
        <el-form-item label="文章标题" prop="title" >
          <el-input v-model="form.title" style="width: 12.6vw" placeholder="请输入文章标题" />
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

        <el-form-item label="发布时间" prop="publishDate">
          <el-date-picker v-model="form.publishDate" type="datetime" placeholder="选择发布时间" />
        </el-form-item>
        <el-form-item label="截止时间" prop="deadlineDate">
          <el-date-picker v-model="form.deadlineDate" type="datetime" placeholder="选择截止时间" />
        </el-form-item>
        <el-form-item label="文章状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择文章状态">
            <el-option label="草稿" value="草稿" />
            <el-option label="已发布" value="已发布" />
            <el-option label="已归档" value="已归档" />
          </el-select>
        </el-form-item>
        <el-form-item label="文章视频" prop="videoUrl">
          <el-upload
              ref="uploadRef"
              :limit="1"
              accept=".mp4, .avi, .mov"
              :headers="upload.headers"
              :action="upload.url"
              :disabled="upload.isUploading"
              :on-progress="handleFileUploadProgress"
              :on-success="handleFileSuccess"
              :on-error="handleFileError"
              :on-change="handleFileChange"
              :auto-upload="false"
              drag
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传视频</em></div>
          </el-upload>
        </el-form-item>
        <el-form-item label="文章内容" prop="content">
          <el-input v-model="form.content" type="textarea" rows="10" placeholder="请输入文章内容" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitArticleForm">新增</el-button>
          <el-button @click="handleBackToList">返回</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
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
            <el-form-item label="发布时间" prop="publishDate">
              <el-date-picker v-model="form.publishDate" type="datetime" placeholder="选择发布时间" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="截止时间" prop="publishDate">
              <el-date-picker v-model="form.deadlineDate" type="datetime" placeholder="选择截止时间" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="视频" prop="videoUrl">
              <el-input v-model="form.videoUrl" placeholder="视频 URL" disabled />
              <video-player v-if="form.videoUrl" :src="form.videoUrl" class="mt-2" />
              <el-upload
                  class="upload-demo mt-2"
                  ref="editUploadRef"
                  :action="upload.url"
                  :headers="upload.headers"
                  :on-change="handleEditFileChange"
                  :before-upload="beforeEditFileUpload"
                  :limit="1"
                  :auto-upload="false"
              >
                <el-button size="small" type="primary">更换视频</el-button>
              </el-upload>
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
  </div>
</template>

<script setup>
import {ref, reactive, toRefs, onMounted, onUnmounted, getCurrentInstance, watch} from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { getToken } from "@/utils/auth";
import { changeUserStatus, listUser, resetUserPwd, delUser, getUser, updateUser, addUser, deptTreeSelect } from "@/api/system/user";
import {
  addArticle,
  delArticle,
  getArticle,
  listArticle,
  updateArticle,
  updatePolicyArticle
} from "@/api/article/article";
import { getPrimaryTagOptions } from "@/api/article/tag";
import { getCategoryOptions } from "@/api/article/type";
import { deletePolicyArticles } from "@/api/article/article";
import VideoPlayer from "@/components/VideoPlayer/VideoPlayer.vue";

const { proxy } = getCurrentInstance();
const router = useRouter();

const articleList = ref([]);
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
const editUploadRef = ref(null);
const selectedFile = ref(null);
const articleFormRef = ref(null);
const uploadRef = ref(null);

const upload = reactive({
  open: false,
  title: "",
  isUploading: false,
  updateSupport: 0,
  headers: { Authorization: "Bearer " + getToken() },
  url: "http://localhost:8091/policy-articles/addVideo"
});

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
    primaryTagOptions: [],
    file: null,
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
    category: [{ required: true, message: "分类不能为空", trigger: "blur" }],
    primaryTag: [{ required: true, message: "主要标签不能为空", trigger: "blur" }],
    title: [{ required: true, message: "文章标题不能为空", trigger: "blur" }],
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
    publishDate: [{ required: true, message: "发布时间不能为空", trigger: "change" }],
    deadlineDate: [{ required: true, message: "截止时间不能为空", trigger: "change" }],
    status: [{ required: true, message: "文章状态不能为空", trigger: "change" }]
  },
});

const { queryParams, form, rules, articleForm } = toRefs(data);

const filterNode = (value, data) => {
  if (!value) return true;
  return data.label.indexOf(value) !== -1;
};



// 监听分类变化


function getDeptTree() {
  deptTreeSelect().then(response => {
    deptOptions.value = response.data;
  });
}

function getList() {
  loading.value = true;
  listArticle(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {

    loading.value = false;
    articleList.value = res.records;
    console.log("articleList.value",articleList.value)
    total.value = res.total;
  });
}

function handleNodeClick(data) {
  queryParams.value.deptId = data.id;
  handleQuery();
}

function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  queryParams.value.deptId = undefined;
  handleQuery();
  getList();
}

function handleDeleteList() {
  const selectedRows = getSelectedRows();
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
}

function handleDelete(row) {
  proxy.$modal.confirm('是否确认删除用户编号为"' + row.articleId + '"的数据项？').then(function () {
    return delArticle(row.articleId);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

function handleExport() {
  proxy.download("/policy-articles/export", {
    ...queryParams.value,
  },`policy_article_${new Date().getTime()}.xlsx`);
}

const multipleTable = ref(null);
const multipleSelection = ref([]);

function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.userId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
  multipleSelection.value = selection;
}

const getSelectedRows = () => {
  return multipleSelection.value;
};

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
    primaryTag: undefined,  // 这里应该设置初始值
    primaryTagOptions: form.value.primaryTagOptions,
    videoUrl:""
  };
  proxy.resetForm("userRef");
}

function cancel() {
  open.value = false;
  reset();
}

function handleAdd() {
  reset();
  getUser().then(response => {
    postOptions.value = response.posts;
    roleOptions.value = response.roles;
    open.value = true;
    title.value = "添加文章";
    form.value.password = initPassword.value;
  });
}

function handleAddNew() {
  reset();
  showAddForm.value = true;
  Object.assign(form, {
    category: undefined,
    primaryTag: undefined,
    title: undefined,
    content: undefined,
    publishDate: undefined,
    deadlineDate: undefined,
    status: "草稿",
    file: null
  });
}

function handleUpdate(row) {
  reset();

  if (row && row.articleId) {
    getArticle(row.articleId).then(response => {
      if (response) {
        form.value = {
          ...response,
          categoryOptions: form.value.categoryOptions || [],
          primaryTagOptions: form.value.primaryTagOptions || []
        };

        if (form.value.videoUrl) {
          form.value.videoUrl = "http://118.31.112.72:8191/" + form.value.videoUrl;
        }

        console.log("form.value", form.value);

        open.value = true;
        title.value = "修改文章";
      } else {
        proxy.$modal.msgError("获取文章数据失败，请重试");
      }
    }).catch(error => {
      console.error("修改文章时出错：", error);
      proxy.$modal.msgError("修改文章失败，请重试");
    });
  }
}

function submitForm() {
  proxy.$refs["userRef"].validate(valid => {
    if (valid) {
      const formData = new FormData();

      const policyArticleDTO = {
        articleId: form.value.articleId,
        category: form.value.category,
        primaryTag: form.value.primaryTag,
        title: form.value.title,
        content: form.value.content,
        publishDate: form.value.publishDate,
        deadlineDate: form.value.deadlineDate,
        status: form.value.status
      };

      formData.append('policyArticleDTO', new Blob([JSON.stringify(policyArticleDTO)], {
        type: 'application/json'
      }));

      if (selectedFile.value) {
        formData.append('file', selectedFile.value);
        console.log('添加文件到 FormData:', selectedFile.value);
      } else if (form.value.videoUrl) {
        formData.append('videoUrl', form.value.videoUrl.replace('http://118.31.112.72:8191/', ''));
        console.log('添加文件到');
      }

      for (let [key, value] of formData.entries()) {
        console.log(key, value);
      }

      updatePolicyArticle(formData).then(response => {
        ElMessage.success("修改成功");
        open.value = false;
        getList();
        selectedFile.value = null;
      }).catch(error => {
        console.error("更新文章失败：", error);
        ElMessage.error(`更新文章失败: ${error.message || '未知错误'}`);
      });
    }
  });
}

function getCategoryOption() {
  getCategoryOptions().then(res => {
    const categoryNames = res.map(item => item.categoryName);
    form.value.categoryOptions = categoryNames;
    console.log("form.value.categoryOptions", form.value.categoryOptions);
  }).catch(error => {
    console.error(error);
  });
}

function getPrimaryTagOption() {
  getPrimaryTagOptions().then(res => {
    const primaryTags = res.map(item => item.tagName);
    form.value.primaryTagOptions = primaryTags;
    console.log("form.value.primaryTagOptions", form.value.primaryTagOptions);
  }).catch(error => {
    console.error(error);
  });
}

function handleBackToList() {
  showAddForm.value = false;
}


function submitArticleForm() {
  articleFormRef.value.validate((valid) => {
    if (valid) {
      const formData = new FormData();
      const policyArticleDTO = {
        category: form.value.category,
        trainingTime: form.value.category === '培训信息' ? form.value.trainingTime : null,
        primaryTag: form.value.primaryTag,
        title: form.value.title,
        content: form.value.content,
        publishDate: form.value.publishDate,
        deadlineDate: form.value.deadlineDate,
        status: form.value.status
      };
      console.log("policyArticleDTO", policyArticleDTO);

      formData.append('policyArticleDTO', new Blob([JSON.stringify(policyArticleDTO)], {
        type: 'application/json'
      }));

      if (form.value.file) {
        formData.append('file', form.value.file);
      }

      addArticle(formData)
          .then(() => {
            proxy.$modal.msgSuccess("文章添加成功");
            getList();
            handleBackToList();
          })
          .catch(error => {
            console.error('添加文章失败：', error);
            proxy.$modal.msgError(`添加文章失败: ${error.message || '未知错误'}`);
          });
    }
  });
}

const handleFileSuccess = (response, file, fileList) => {
  upload.isUploading = false;
  form.value.file = file.raw;
  proxy.$modal.msgSuccess("文件上传成功");
};

function beforeEditFileUpload(file) {
  const isVideo = file.type.startsWith('video/');
  const isLt50M = file.size / 1024 / 1024 < 50;
  const allowedTypes = ['video/mp4', 'video/quicktime', 'video/x-msvideo'];

  if (!isVideo) {
    ElMessage.error('只能上传视频文件!');
    return false;
  }
  if (!isLt50M) {
    ElMessage.error('视频大小不能超过 50MB!');
    return false;
  }
  if (!allowedTypes.includes(file.type)) {
    ElMessage.error('只支持 MP4、MOV 和 AVI 格式的视频!');
    return false;
  }

  return true;
}

function handleEditFileChange(file) {
  console.log('File changed:', file);
  selectedFile.value = file.raw;
}

function handleUploadVideo() {
  upload.title = "上传视频";
  upload.open = true;
}

function handleFileError(error, file, fileList) {
  upload.isUploading = false;
  console.error('上传错误：', error);
  ElMessage.error(`上传失败: ${error.message || '未知错误'}`);
}

const handleFileChange = (file) => {
  form.value.file = file.raw;
};

function handleEditFileUploadProgress(event, file, fileList) {
  console.log('上传进度：', event.percent);
}

function handleEditFileSuccess() {
  proxy.$modal.msgSuccess("文件上传成功");
}

onMounted(() => {
  getDeptTree();
  getList();
  getCategoryOption();
  getPrimaryTagOption();
});

onUnmounted(() => {
  selectedFile.value = null;
});
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

:deep(.article-dialog) {
  --el-dialog-width: 90%;
  max-width: 1000px;
}

:deep(.video-preview-item) {
  width: 100%;
  margin: 0;
  padding: 0;
  max-height: 400px;
  overflow: hidden;
}

:deep(.el-form-item__content) {
  width: 100%;
}
</style>