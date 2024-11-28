<template>
  <div class="enterprise-form-container">
    <div class="enterprise-form-wrapper">
      <h2>企业申请表</h2>
      <form @submit.prevent="submitForm" class="enterprise-form">
        <div class="form-group">
          <label for="name">姓名</label>
          <input type="text" id="name" v-model="formData.applicantName" required>
        </div>

        <div class="form-group">
          <label for="phone">电话</label>
          <input type="tel" id="phone" v-model="formData.applicantPhone" required>
        </div>

        <div class="form-group">
          <label for="email">邮箱</label>
          <input type="email" id="email" v-model="formData.applicantEmail" required>
        </div>

        <div class="form-group">
          <label>申请类型</label>
          <select id="applicationType" v-model="formData.applicationType" required>
            <option value="">请选择</option>
            <option v-for="option in applicationTypeOptions" :key="option.value" :value="option.value">
              {{ option.label }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label>行业类型</label>
          <select id="industryName" v-model="formData.industryName" required>
            <option value="">请选择</option>
            <option v-for="option in industryOptions" :key="option.value" :value="option.value">
              {{ option.label }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label>企业介绍</label>
          <textarea id="enterpriseDescription" v-model="formData.enterpriseDescription" rows="4" required></textarea>
        </div>

        <button type="submit" class="submit-button">提交申请</button>
      </form>

      <!-- 显示提交结果 -->
      <div v-if="formStatus === 'success'" class="success-message">提交成功！</div>
      <div v-if="formStatus === 'error'" class="error-message">提交失败，请稍后再试。</div>
    </div>
  </div>
</template>


<script>
import axios from 'axios';

export default {
  data() {
    return {
      formData: {
        applicantName: '',
        applicantPhone: '',
        applicantEmail: '',
        applicationType: '',
        industryName: '',
        enterpriseDescription: ''
      },
      industryOptions: [],
      applicationTypeOptions: [],
      formStatus: '' // 新增状态变量
    };
  },
  methods: {
    async getIndustryOptions() {
      try {
        const response = await axios.get('http://localhost:8091/industries/getIndustryOptions');
        this.industryOptions = response.data.map(item => ({
          value: item.industryName,
          label: item.industryName
        }));
      } catch (error) {
        console.error("Error fetching industry options:", error);
      }
    },
    async getApplicationTypeOptions() {
      try {
        const response = await axios.get('http://localhost:8091/application-type/getApplicationTypeOptions');
        this.applicationTypeOptions = response.data.map(item => ({
          value: item.applicationName,
          label: item.applicationName
        }));
      } catch (error) {
        console.error("Error fetching application type options:", error);
      }
    },
    async submitForm() {
      const data = {
        ...this.formData,
        applicationDate: new Date().toISOString(),
        status: 'pending',
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      };

      try {
        const response = await axios.post('http://localhost:8091/api/application/add', data);
        if (response.data) {
          this.formStatus = 'success'; // 设置成功状态
          // 清空表单
          Object.keys(this.formData).forEach(key => {
            this.formData[key] = '';
          });
        } else {
          this.formStatus = 'error'; // 设置失败状态
        }
      } catch (error) {
        this.formStatus = 'error'; // 设置失败状态
        console.error('Error submitting form:', error);
      }
    }
  },
  mounted() {
    this.getIndustryOptions();
    this.getApplicationTypeOptions();
  }
};
</script>


<style scoped>
.enterprise-form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f0f4f8;
  padding: 2rem;
}

.enterprise-form-wrapper {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 500px;
}

h2 {
  color: #333;
  margin-bottom: 2rem;
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
}

.enterprise-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 0.5rem;
  color: #555;
  font-weight: bold;
}

input, select, textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  transition: border-color 0.3s ease;
}

input:focus, select:focus, textarea:focus {
  outline: none;
  border-color: #4CAF50;
}

textarea {
  resize: vertical;
}

.submit-button {
  width: 100%;
  padding: 1rem;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-button:hover {
  background-color: #45a049;
}

.success-message {
  color: green;
  text-align: center;
  margin-top: 1rem;
}

.error-message {
  color: red;
  text-align: center;
  margin-top: 1rem;
}

@media (max-width: 600px) {
  .enterprise-form-wrapper {
    padding: 1.5rem;
  }
}
</style>
