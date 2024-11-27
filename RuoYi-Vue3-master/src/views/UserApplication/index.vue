<template>
    <div class="enterprise-form-container">
        <h2>企业申请表</h2>
        <form @submit.prevent="submitForm" class="enterprise-form">
            <div class="form-group">
                <label for="name">姓名</label>
                <input type="text" id="name" v-model="formData.name" required>
            </div>

            <div class="form-group">
                <label for="phone">电话</label>
                <input type="tel" id="phone" v-model="formData.phone" required>
            </div>

            <div class="form-group">
                <label for="email">邮箱</label>
                <input type="email" id="email" v-model="formData.email" required>
            </div>

            <div class="form-group">
                <label >申请类型</label>
                <select id="applicationType" v-model="formData.applicationType" required>
                    <option value="">请选择</option>
                    <option value="enter">进入创业园</option>
                    <option value="exit">退出创业园</option>
                </select>
            </div>

            <div class="form-group">
                <label >企业类型</label>
                <select id="enterpriseType" v-model="formData.enterpriseType" required>
                    <option value="">请选择</option>
                    <option value="ecommerce">电商</option>
                    <option value="cultural">文创</option>
                    <option value="tech">科技</option>
                    <option value="service">服务业</option>
                    <option value="manufacturing">制造业</option>
                </select>
            </div>

            <div class="form-group">
                <label>企业介绍</label>
                <textarea id="enterpriseIntro" v-model="formData.enterpriseIntro" rows="4" required></textarea>
            </div>

            <button type="submit" class="submit-button">提交申请</button>
        </form>
    </div>
</template>

<script setup>
    import { reactive } from 'vue';
    import axios from 'axios';

    const formData = reactive({
        name: '',
        phone: '',
        email: '',
        applicationType: '',
        enterpriseType: '',
        enterpriseIntro: ''
    });

    const postApplication = async (data) => {

        const response = await axios.post('http://localhost:8091/api/application/add', data);
        return response.data;

    };

    const submitForm = async () => {
        try {
            const data = {
                applicantName: formData.name,
                applicantPhone: formData.phone,
                applicantEmail: formData.email,
                applicationTypeId: formData.applicationType === 'enter' ? 1 : 2, // 假设进入创业园对应ID为1，退出创业园对应ID为2
                industryId: getIndustryId(formData.enterpriseType), // 获取行业ID
                enterpriseDescription: formData.enterpriseIntro,
                applicationDate: new Date().toISOString(),
                status: 'pending',
                createdAt: new Date().toISOString(),
                updatedAt: new Date().toISOString()
            };

            const response = await postApplication(data);

            if (response) {
                alert('申请已提交！');
                // 清空表单
                Object.keys(formData).forEach(key => {
                    formData[key] = '';
                });
            } else {
                alert('提交失败，请稍后再试。');
            }
        } catch (error) {
            console.error('Error submitting form:', error);
            alert('提交失败，请稍后再试。');
        }
    };

    // 获取行业ID的辅助函数
    const getIndustryId = (type) => {
        switch (type) {
            case 'ecommerce':
                return 1;
            case 'cultural':
                return 2;
            case 'tech':
                return 3;
            case 'service':
                return 4;
            case 'manufacturing':
                return 5;
            default:
                return null;
        }
    };
</script>

<style scoped>
    .enterprise-form-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        min-height: 100vh;
        background-color: #f0f4f8;
        font-family: Arial, sans-serif;
        padding: 2rem;
    }

    h2 {
        color: #333;
        margin-bottom: 2rem;
        text-align: center;
    }

    .enterprise-form {
        background-color: white;
        padding: 2rem;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        width: 100%;
        max-width: 500px;
    }

    .form-group {
        margin-bottom: 1.5rem;
    }

    label {
        display: block;
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

    @media (max-width: 600px) {
        .enterprise-form {
            padding: 1.5rem;
        }
    }
</style>
