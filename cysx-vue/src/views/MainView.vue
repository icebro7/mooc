<template>
  <div class="dark-theme">
    <el-container>
      <!-- 菜单 -->
      <el-aside width="200px" class="dark-aside">
        <p><router-link to="/course" class="nav-link">课程管理</router-link></p>
        <p><router-link to="/person" class="nav-link">人员管理</router-link></p>
        <p><router-link to="/chapter" class="nav-link">章节管理</router-link></p>
      </el-aside>
      <el-container>
        <!-- 头部 -->
        <el-header class="dark-header">
          <div class="header-content">
            <div class="welcome-message">欢迎 {{ userData.adminNickname }}</div>
            <div>
              <el-upload class="avatar-uploader" action="/api/fileUpload"
                         :on-success="handleAvatarSuccess" :data="userId">
                <img v-if="userData.adminFace" class="avatar" :src="`/api/${userData.adminFace}`">
              </el-upload>
            </div>
          </div>
        </el-header>

        <el-main class="dark-main">
          <!-- 二级路由展示 -->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userData: {
        adminFace: null,
        adminNickname: null
      },
      userId: { userId: null }
    };
  },
  methods: {
    handleAvatarSuccess(res) {
      localStorage.setItem("cysx_token", res.data);
      this.getAdminInfoByToken();
    },
    getAdminInfoByToken() {
      this.$axios.get('getAdminInfoByToken', {
        params: {
          token: localStorage.getItem("cysx_token")
        }
      }).then(res => {
        this.userId.userId = res.data.data.adminId;
        this.userData.adminNickname = res.data.data.adminNickname;
        this.userData.adminFace = res.data.data.adminFace;
      })
          .catch(error => {
            console.log(error);
          });
    }
  },
  mounted() {
    this.getAdminInfoByToken();
  }
};
</script>

<style scoped>
.dark-theme {
  background-color: rgb(245, 245, 247);
  color: #ffffff;
  min-height: 100vh;
}

.dark-aside {
  background-color: #2d2d2d;
  color: #ffffff;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center; /* 水平居中 */
  padding: 50px 0; /* 增加上下距离 */
}

.dark-header {
  background-color: rgba(245, 245, 247, .8);
  color: #ffffff;
  padding: 0 30px;
}

.welcome-message {
  margin-right: 20px;
  font-family: 'Roboto', sans-serif; /* 使用 Roboto 字体 */
  font-size: 17px; /* 设置字体大小 */
  font-weight: 500; /* 设置字体粗细 */
  color: #2b2d30; /* 设置字体颜色为橙色 */
}

.dark-main {
  background-color: #ffffff;
  color: #ffffff;
  padding: 20px;
}

.header-content {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.welcome-message {
  margin-right: 20px;
}

.avatar {
  height: 45px;
  width: 50px;
  border-radius: 50%;
}

.nav-link {
  color: #ffffff;
  text-decoration: none;
  display: block;
  padding: 50px;
  transition: bacnpmkground-color 0.3s;
}

.nav-link:hover {
  background-color: #444444;
}

/* 响应式布局 */
@media (max-width: 768px) {
  .el-aside {
    width: 100px;
  }

  .header-content {
    flex-direction: column;
    align-items: flex-end;
  }

  .welcome-message {
    margin-right: 0;
    margin-bottom: 10px;
  }
}
</style>