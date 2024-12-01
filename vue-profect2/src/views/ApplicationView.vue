<template>
  <div class="enterprise-form-container">
    <div class="enterprise-form-wrapper">
      <h2>企业申请表</h2>
      <form @submit.prevent="submitForm" class="enterprise-form">
        <div class="form-row">
          <div class="form-group">
            <label for="name">姓名</label>
            <input style="width: 10vw" type="text" id="name" v-model="formData.applicantName" required>
          </div>

          <div class="form-group">
            <label for="phone">电话</label>
            <input style="width: 10vw" type="tel" id="phone" v-model="formData.applicantPhone" required>
          </div>

          <div class="form-group">
            <label for="email">邮箱</label>
            <input type="email" style="width: 10vw" id="email" v-model="formData.applicantEmail" required>
          </div>
        </div>

        <div class="form-row" style="margin-top: 2vh">
          <div class="form-group">
            <label for="applicationType">申请类型</label>
            <select id="applicationType" style="width: 10vw" v-model="formData.applicationType" required>
              <option value="">请选择</option>
              <option v-for="option in applicationTypeOptions" :key="option.value" :value="option.value">
                {{ option.label }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <label for="industryName">行业类型</label>
            <select id="industryName" style="width: 10vw" v-model="formData.industryName" required>
              <option value="">请选择</option>
              <option v-for="option in industryOptions" :key="option.value" :value="option.value">
                {{ option.label }}
              </option>
            </select>
          </div>
        </div>

        <div class="form-group description-group" style="margin-top: 3vh">
          <label for="enterpriseDescription">企业介绍</label>
          <textarea id="enterpriseDescription" style="margin-top: 2vh" v-model="formData.enterpriseDescription" rows="14" required></textarea>
        </div>

        <button type="submit" class="submit-button">提交申请</button>
      </form>
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
      applicationTypeOptions: []
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
          alert('申请已提交！');
          // 清空表单
          Object.keys(this.formData).forEach(key => {
            this.formData[key] = '';
          });
        } else {
          alert('提交失败，请稍后再试。');
        }
      } catch (error) {
        console.error('Error submitting form:', error);
        alert('提交失败，请稍后再试。');
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
  height: 98vh;
  background-color: #f0f4f8;
}

.enterprise-form-wrapper {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 800px;
  height: 80vh;
}

h2 {
  color: #333;
  margin-bottom: 1.5rem;
  text-align: center;
  font-size: 2rem;
  font-weight: bold;
}

.enterprise-form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-row {
  display: flex;
  justify-content: center;
  gap: 3rem;
  margin-bottom: 1rem;
  width: 100%;
}

.form-group {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.description-group {
  width: 100%;
}

label {
  margin-bottom: 0.25rem;
  color: #555;
  font-weight: bold;
}

input, select, textarea {
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 0.9rem;
  transition: border-color 0.3s ease;
}

input:focus, select:focus, textarea:focus {
  outline: none;
  border-color: #4CAF50;
}

textarea {
  width: 100%;
  resize: vertical;
}

.description-group textarea {
  min-height: 160px;
  font-size: 1.1rem;
}

.submit-button {
  padding: 0.75rem;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-top: 1rem;
  width: 100%;
}

.submit-button:hover {
  background-color: #45a049;
}

@media (max-width: 768px) {
  .form-row {
    flex-direction: column;
    align-items: center;
  }

  .form-group {
    width: 100%;
  }
}
</style>