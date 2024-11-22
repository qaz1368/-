<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--用户数据-->
      <el-col :span="24">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="企业名称" prop="userName">
            <el-input
                v-model="queryParams.userName"
                placeholder="请输入企业名称"
                clearable
                style="width: 240px"
                @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="所属行业" prop="phonenumber">
            <el-input
                v-model="queryParams.phonenumber"
                placeholder="请输入所属行业"
                clearable
                style="width: 240px"
                @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
                v-model="queryParams.status"
                placeholder="企业状态"
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

       <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange" class="full-width-table">
    <el-table-column type="selection" width="50" align="center" />
    <el-table-column label="企业ID" align="center" key="companyId" prop="companyId" v-if="columns[0].visible" />
    <el-table-column label="企业名称" align="center" key="companyName" prop="companyName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
    <el-table-column label="所属行业" align="center" key="industry" prop="industry" v-if="columns[2].visible" :show-overflow-tooltip="true" />
    <el-table-column label="地域" align="center" key="region" prop="region" v-if="columns[3].visible" :show-overflow-tooltip="true" />
    <el-table-column label="企业标签" align="center" key="enterpriseLabel" prop="enterpriseLabel" v-if="columns[3].visible" :show-overflow-tooltip="true" />
    <el-table-column label="注册地址" align="center" key="registeredAddress" prop="registeredAddress" v-if="columns[3].visible" :show-overflow-tooltip="true" />
    <el-table-column label="法定代表人" align="center" key="legalPerson" prop="legalPerson" v-if="columns[4].visible" width="120" />
    <el-table-column label="成立日期" align="center" key="establishmentDate" prop="establishmentDate" v-if="columns[5].visible" width="160" />
    <el-table-column label="企业状态" align="center" key="companyStatus" prop="companyStatus" v-if="columns[5].visible" width="160" />
    <el-table-column label="获得补助时间" align="center" key="subsidyReceivedDate" prop="subsidyReceivedDate" v-if="columns[5].visible" width="160" />
    <el-table-column label="获得补助金额" align="center" key="subsidyAmount" prop="subsidyAmount" v-if="columns[5].visible" width="160" />

    <!-- 新增字段 -->
    <el-table-column label="带动就业人数" align="center" key="employmentImpact" prop="employmentImpact" v-if="columns[1].visible" width="120" />
    <el-table-column label="签订劳动合同人数" align="center" key="signedContracts" prop="signedContracts" v-if="columns[1].visible" width="120" />
    <el-table-column label="缴纳社会保险人数" align="center" key="socialSecurityContributors" prop="socialSecurityContributors" v-if="columns[1].visible" width="120" />
    <el-table-column label="年营业额" align="center" key="annualRevenue" prop="annualRevenue" v-if="columns[1].visible" width="120" />
    <el-table-column label="年纳税金额" align="center" key="annualTax" prop="annualTax" v-if="columns[1].visible" width="120" />
    <el-table-column label="注册资本" align="center" key="registeredCapital" prop="registeredCapital" v-if="columns[1].visible" width="120" />
    <el-table-column label="带动扶贫人数" align="center" key="povertyAlleviation" prop="povertyAlleviation" v-if="columns[1].visible" width="120" />
    <el-table-column label="贷款金额" align="center" key="loanAmount" prop="loanAmount" v-if="columns[1].visible" width="120" />
    <el-table-column label="是否享受政府补贴" align="center" key="governmentSubsidy" prop="governmentSubsidy" v-if="columns[1].visible" width="120">
      <template #default="scope">
        {{ formatBoolean(scope.row.governmentSubsidy) }}
      </template>
    </el-table-column>
    <el-table-column label="是否缴纳社会保障" align="center" key="socialSecurity" prop="socialSecurity" v-if="columns[1].visible" width="120">
      <template #default="scope">
        {{ formatBoolean(scope.row.socialSecurity) }}
      </template>
    </el-table-column>
    <el-table-column label="是否需要创业辅导" align="center" key="entrepreneurshipGuidance" prop="entrepreneurshipGuidance" v-if="columns[1].visible" width="120">
      <template #default="scope">
        {{ formatBoolean(scope.row.entrepreneurshipGuidance) }}
      </template>
    </el-table-column>
    <el-table-column label="获奖补助金额" align="center" key="awards" prop="awards" v-if="columns[1].visible" width="120" />
    <el-table-column label="获奖情况" align="center" key="awardsReceived" prop="awardsReceived" v-if="columns[1].visible" width="160" :show-overflow-tooltip="true"  />
    <el-table-column label="备注" align="center" key="remarks" prop="remarks" v-if="columns[1].visible" width="120" />
    <el-table-column label="记录创建时间" align="center" key="createdAt" prop="createdAt" v-if="columns[1].visible" width="160" />
    <el-table-column label="最后更新时间" align="center" key="updatedAt" prop="updatedAt" v-if="columns[0].visible" width="160" />
    <el-table-column label="创业园" align="center" key="incubator" prop="incubator" v-if="columns[1].visible" width="120" />
    <el-table-column label="企业负责人" align="center" key="managerName" prop="managerName" v-if="columns[1].visible" width="120" />

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
  <el-form :model="form"  ref="userRef" label-width="120px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="企业ID" prop="companyId">
          <el-input v-model="form.companyId" placeholder="请输入企业ID" maxlength="30" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="企业名称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入企业名称" maxlength="30" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="所属行业" prop="industry">
          <el-input v-model="form.industry" placeholder="请输入所属行业" maxlength="30" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="注册地址" prop="registeredAddress">
          <el-input v-model="form.registeredAddress" placeholder="请输入注册地址" maxlength="50" />
        </el-form-item>
      </el-col>
    </el-row>
        <el-row>
      <el-col :span="12">
        <el-form-item label="地域" prop="industry">
          <el-input v-model="form.region" placeholder="请输入地域" maxlength="30" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="企业标签" prop="registeredAddress">
          <el-input v-model="form.enterpriseLabel" placeholder="请输入企业标签" maxlength="50" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="法定代表人" prop="legalPerson">
          <el-input v-model="form.legalPerson" placeholder="请输入法定代表人" maxlength="30" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="成立日期" prop="establishmentDate">
          <el-date-picker v-model="form.establishmentDate" type="date" placeholder="选择成立日期" style="width: 100%;" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
  <el-form-item label="企业状态" prop="companyStatus">
    <el-select v-model="form.companyStatus" placeholder="请选择企业状态">
      <el-option
        v-for="dict in companyStatusOptions"
        :key="dict.value"
        :label="dict.label"
        :value="dict.value"
      ></el-option>
    </el-select>
  </el-form-item>
</el-col>

      <el-col :span="12">
        <el-form-item label="获得补助时间" prop="subsidyReceivedDate">
          <el-date-picker v-model="form.subsidyReceivedDate" type="date" placeholder="选择获得补助时间" style="width: 100%;" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="获得补助金额" prop="subsidyAmount">
          <el-input v-model="form.subsidyAmount" placeholder="请输入获得补助金额" type="number" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="带动就业人数" prop="employmentImpact">
          <el-input v-model="form.employmentImpact" placeholder="请输入带动就业人数" type="number" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="签订劳动合同人数" prop="signedContracts">
          <el-input v-model="form.signedContracts" placeholder="请输入签订劳动合同人数" type="number" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="缴纳社会保险人数" prop="socialSecurityContributors">
          <el-input v-model="form.socialSecurityContributors" placeholder="请输入缴纳社会保险人数" type="number" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="年营业额" prop="annualRevenue">
          <el-input v-model="form.annualRevenue" placeholder="请输入年营业额" type="number" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="年纳税金额" prop="annualTax">
          <el-input v-model="form.annualTax" placeholder="请输入年纳税金额" type="number" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="注册资本" prop="registeredCapital">
          <el-input v-model="form.registeredCapital" placeholder="请输入注册资本" type="number" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="带动扶贫人数" prop="povertyAlleviation">
          <el-input v-model="form.povertyAlleviation" placeholder="请输入带动扶贫人数" type="number" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="贷款金额" prop="loanAmount">
          <el-input v-model="form.loanAmount" placeholder="请输入贷款金额" type="number" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否享受政府补贴" prop="governmentSubsidy">
          <el-select v-model="form.governmentSubsidy" placeholder="请选择">
            <el-option label="是" value="true"></el-option>
            <el-option label="否" value="false"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="是否缴纳社会保障" prop="socialSecurity">
          <el-select v-model="form.socialSecurity" placeholder="请选择">
            <el-option label="是" value="true"></el-option>
            <el-option label="否" value="false"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="项目获奖情况" prop="awardsReceived">
          <el-input v-model="form.awardsReceived" placeholder="请输入项目获奖情况" maxlength="50" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="是否需要创业辅导" prop="entrepreneurshipGuidance">
          <el-select v-model="form.entrepreneurshipGuidance" placeholder="请选择">
            <el-option label="是" value="true"></el-option>
            <el-option label="否" value="false"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="记录创建时间" prop="createdAt">
          <el-date-picker v-model="form.createdAt" type="datetime" placeholder="选择记录创建时间" style="width: 100%;" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="最后更新时间" prop="updatedAt">
          <el-date-picker v-model="form.updatedAt" type="datetime" placeholder="选择最后更新时间" style="width: 100%;" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="孵化器ID" prop="incubatorId">
          <el-input v-model="form.incubator" placeholder="请输入孵化器ID" maxlength="30" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="企业负责人ID" prop="managerId">
          <el-input v-model="form.managerName" placeholder="请输入企业负责人ID" maxlength="30" />
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
import {addEnterprise, listEnterprise} from "@/api/system/enterprise";
import {getCurrentInstance, reactive, ref} from "vue";

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
const formatBoolean = (value) => {
  return value ? '是' : '否';
};
 const companyStatusOptions = ref([
      { value: 'Initial', label: '初创' },
      { value: 'Growing', label: '成长' },
      { value: 'Mature', label: '成熟' },
      { value: 'Closed', label: '关闭' }
    ]);

const data = reactive({
  form: {
    companyId: null,
    companyName: null,
    industry: null,
    region: null,
    enterpriseLabel: null,
    registeredAddress: null,
    legalPerson: null,
    establishmentDate: null,
    companyStatus: null,
    subsidyReceivedDate: null,
    subsidyAmount: null,
    employmentImpact: null,
    signedContracts: null,
    socialSecurityContributors: null,
    annualRevenue: null,
    annualTax: null,
    registeredCapital: null,
    povertyAlleviation: null,
    loanAmount: null,
    governmentSubsidy: null,
    socialSecurity: null,
    awards: null,
    awardsReceived: null,
    entrepreneurshipGuidance: null,
    remarks: null,
    createdAt: null,
    updatedAt: null,
    incubator: null,
    managerName: null,

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
    userName: [{ required: true, message: "用户名称不能为空", trigger: "blur" }, { min: 2, max: 20, message: "用户名称长度必须介于 2 和 20 之间", trigger: "blur" }],
    nickName: [{ required: true, message: "用户昵称不能为空", trigger: "blur" }],
    password: [{ required: true, message: "用户密码不能为空", trigger: "blur" }, { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" }],
    email: [{ type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] }],
    phonenumber: [{ pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }]
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
/** 查询企业列表 */
function getList() {
  loading.value = true;
  listEnterprise(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
    // 状态映射对象
    const statusMap = {
      Initial: '初创',
      Growing: '成长',
      Mature: '成熟',
      Closed: '关闭'
    };

    // 日期格式化函数
    function formatDate(date) {
      if (!date) return '';
      const d = new Date(date);
      const year = d.getFullYear();
      const month = String(d.getMonth() + 1).padStart(2, '0');
      const day = String(d.getDate()).padStart(2, '0');
      const hours = String(d.getHours()).padStart(2, '0');
      const minutes = String(d.getMinutes()).padStart(2, '0');
      const seconds = String(d.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }

    // 遍历 res.records 并更新状态和日期格式
    res.records.forEach(item => {
      item.companyStatus = statusMap[item.companyStatus] || item.companyStatus;
      item.establishmentDate = formatDate(item.establishmentDate);
      item.subsidyReceivedDate = formatDate(item.subsidyReceivedDate);
      item.createdAt = formatDate(item.createdAt);
      item.updatedAt = formatDate(item.updatedAt);
    });

    console.log("企业管理", res.records)
    loading.value = false;
    userList.value = res.records;
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
  proxy.$refs.tree.setCurrentKey(null);
  handleQuery();
};
/** 删除按钮操作 */
function handleDelete(row) {
  const userIds = row.userId || ids.value;
  proxy.$modal.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(function () {
    return delUser(userIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
};
/** 导出按钮操作 */
function handleExport() {
  proxy.download("system/user/export", {
    ...queryParams.value,
  },`user_${new Date().getTime()}.xlsx`);
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
    title.value = "添加企业";
    form.value.password = initPassword.value;
  });
};
/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const userId = row.userId || ids.value;
  getUser(userId).then(response => {
    form.value = response.data;
    postOptions.value = response.posts;
    roleOptions.value = response.roles;
    form.value.postIds = response.postIds;
    form.value.roleIds = response.roleIds;
    open.value = true;
    title.value = "修改用户";
    form.password = "";
  });
};
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
        addEnterprise(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};

getDeptTree();
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