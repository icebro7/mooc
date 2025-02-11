<template>
  <div>
    <!-- 导航栏 -->
    <div class="header">
      <div class="centerBox">
        <div>
          <img class="logoImgSty" src="@/assets/logo-01.png" alt="">
        </div>
        <div class="boxCenter">
          <ul class="bar">
            <li></li>
            <li id="courseCenter"></li>
            <li></li>
            <li></li>
          </ul>
        </div>
        <div class="inpBox"></div>
        <div class="leftBox" style="width:800px">
          <a href="http://localhost:8080/#/login">登录</a>&nbsp; /&nbsp; <a href="#">注册</a>
        </div>
      </div>
    </div>

    <!-- 轮播图 -->
    <div style="display: flex; justify-content: center; margin-top: 120px;">
      <el-carousel :interval="5000" arrow="always" style="width: 80%;" height="400px">
        <el-carousel-item v-for="item in imgWrap" :key="item.url">
          <img class="bannerImg" :src="item.url" />
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 展示课程 -->
    <div class="course-container">
      <div class="course-list">
        <div
            class="course-card"
            v-for="(item, index) in courseData"
            :key="item.courseId"
            @click="expandCard(index)"
        >
          <!-- 从 assets 中加载图片 -->
          <img :src="getCourseCover(index)" alt="课程封面" class="course-cover">
          <div class="course-info">
            <h3 class="course-title">{{ item.courseName }}</h3>
            <p class="course-description">{{ item.courseIntroduction }}</p>
            <div class="course-price">￥{{ item.coursePrice }}.00</div>
            <div class="course-tag" v-if="item.courseStatus === 1">上架</div>
            <div class="course-tag" v-else>下架</div>
          </div>
        </div>
      </div>

      <!-- 分页组件 -->
      <div class="pagination">
        <el-pagination
            background
            layout="prev, pager, next"
            :total="total"
            :page-size="pageSize"
            :current-page="currentPage"
            @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>

    <!-- 放大后的课程卡片 -->
    <transition name="zoom">
      <div v-if="expandedIndex !== null" class="expanded-card-overlay" @click.self="closeExpandedCard">
        <div class="expanded-card">
          <!-- 左侧：课程信息 -->
          <div class="expanded-left">
            <img :src="getCourseCover(expandedIndex)" alt="课程封面" class="expanded-cover">
            <div class="expanded-info">
              <h3 class="expanded-title">{{ courseData[expandedIndex].courseName }}</h3>
              <p class="expanded-description">{{ courseData[expandedIndex].courseIntroduction }}</p>
              <div class="expanded-price">￥{{ courseData[expandedIndex].coursePrice }}.00</div>
              <div class="expanded-tag" v-if="courseData[expandedIndex].courseStatus === 1">上架</div>
              <div class="expanded-tag" v-else>下架</div>
              <div class="expanded-actions">
                <button class="buy-button" @click="buy">立即购买</button>
                <button class="cancel-button" @click="closeExpandedCard">取消</button>
              </div>
            </div>
          </div>

          <!-- 右侧：评论部分 -->
          <div class="expanded-right">
            <div class="comment-section">
              <h4>评论</h4>
              <div v-if="comments.length > 0">
                <div v-for="comment in comments" :key="comment.id" class="comment-item">
                  <p>{{ comment.content }}</p>
                  <small>用户ID: {{ comment.userId }} - {{ formatDate(comment.createdAt) }}</small>
                </div>
              </div>
              <div v-else>
                <p>暂无评论</p>
              </div>
              <div class="comment-input">
                <textarea v-model="newComment" placeholder="请输入评论内容"></textarea>
                <button @click="submitComment">提交评论</button>
              </div>
            </div>

            <!-- 点赞部分 -->
            <div class="like-section">
              <button
                  v-if="isLiked"
                  class="like-button liked"
                  @click="unlikeCourse"
              >
                取消点赞
              </button>
              <button
                  v-else
                  class="like-button"
                  @click="likeCourse"
              >
                点赞
              </button>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      courseData: [], // 课程数据
      imgWrap: [
        { url: require("@/assets/1.jpg") },
        { url: require("@/assets/2.jpg") },
        { url: require("@/assets/3.jpg") },
        { url: require("@/assets/4.jpg") },
        { url: require("@/assets/5.jpg") },
      ],
      currentPage: 1, // 当前页码
      pageSize: 8,    // 每页显示的课程数量
      total: 0,       // 总课程数量
      expandedIndex: null, // 当前放大的卡片索引
      comments: [],   // 评论列表
      newComment: '', // 新评论内容
      isLiked: false, // 是否已点赞
    };
  },
  methods: {
    // 获取课程数据
    fetchCourseData() {
      axios.get('/api/queryCourse', {
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          courseName: '',
          courseSort: '',
          courseStatus: 1
        }
      })
          .then(response => {
            if (response.data.code === 1) {
              this.courseData = response.data.data.list;
              this.total = response.data.data.total; // 更新总课程数量
            } else {
              console.error('获取课程数据失败');
            }
          })
          .catch(error => {
            console.error('请求课程数据时出错:', error);
          });
    },
    // 根据索引获取课程封面图片
    getCourseCover(index) {
      const covers = [
        require("@/assets/kechen1.jpg"),
        require("@/assets/kechen2.jpg"),
        require("@/assets/kechen3.jpg"),
        require("@/assets/kechen4.jpg"),
        require("@/assets/kechen5.jpg"),
        require("@/assets/kechen6.jpg"),
        require("@/assets/kechen7.jpg"),
        require("@/assets/kechen8.jpg")
      ];
      return covers[index % covers.length]; // 循环使用图片
    },
    // 分页切换
    handlePageChange(page) {
      this.currentPage = page; // 更新当前页码
      this.fetchCourseData();  // 重新获取课程数据
    },
    // 放大卡片
    expandCard(index) {
      this.expandedIndex = index;
      this.fetchComments(this.courseData[index].courseId); // 获取评论
      this.fetchLikeStatus(this.courseData[index].courseId); // 获取点赞状态
    },
    // 关闭放大卡片
    closeExpandedCard() {
      this.expandedIndex = null;
      this.comments = []; // 清空评论
      this.isLiked = false; // 重置点赞状态
    },
    // 获取评论
    fetchComments(courseId) {
      axios.get('/api/courseComments', {
        params: {
          courseId: courseId
        }
      })
          .then(response => {
            if (response.data.code === 1) {
              this.comments = response.data.data;
            } else {
              console.error('获取评论失败');
            }
          })
          .catch(error => {
            console.error('请求评论数据时出错:', error);
          });
    },
    // 提交评论
    submitComment() {
      if (this.newComment.trim() === '') {
        alert('评论内容不能为空');
        return;
      }
      const courseId = this.courseData[this.expandedIndex].courseId;
      axios.post('/api/addCourseComment', {
        courseId: courseId,
        userId: 1, // 假设当前用户ID为1
        content: this.newComment
      })
          .then(response => {
            if (response.data.code === 1) {
              this.newComment = '';
              this.fetchComments(courseId); // 重新获取评论
            } else {
              console.error('提交评论失败');
            }
          })
          .catch(error => {
            console.error('提交评论时出错:', error);
          });
    },
    // 获取点赞状态
    fetchLikeStatus(courseId) {
      const userId = 1; // 假设当前用户ID为1
      axios.get('/api/checkLikeStatus', {
        params: {
          courseId: courseId,
          userId: userId
        }
      })
          .then(response => {
            if (response.data.code === 1) {
              this.isLiked = response.data.data;
            } else {
              console.error('获取点赞状态失败');
            }
          })
          .catch(error => {
            console.error('请求点赞状态时出错:', error);
          });
    },
    // 点赞课程
    likeCourse() {
      const courseId = this.courseData[this.expandedIndex].courseId;
      const userId = 1; // 假设当前用户ID为1
      axios.post('/api/likeCourse', {
        courseId: courseId,
        userId: userId
      })
          .then(response => {
            if (response.data.code === 1) {
              this.isLiked = true;
            } else {
              console.error('点赞失败');
            }
          })
          .catch(error => {
            console.error('点赞时出错:', error);
          });
    },
    // 取消点赞课程
    unlikeCourse() {
      const courseId = this.courseData[this.expandedIndex].courseId;
      const userId = 1; // 假设当前用户ID为1
      axios.post('/api/unlikeCourse', {
        courseId: courseId,
        userId: userId
      })
          .then(response => {
            if (response.data.code === 1) {
              this.isLiked = false;
            } else {
              console.error('取消点赞失败');
            }
          })
          .catch(error => {
            console.error('取消点赞时出错:', error);
          });
    },
    // 格式化日期
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleString(); // 返回本地化日期时间
    },
    // 购买课程
    buy() {
      alert("未完待续");
    }
  },
  mounted() {
    this.fetchCourseData(); // 页面加载时获取课程数据
  }
};
</script>

<style scoped>
/* 导航栏样式 */
.header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 80px;
  background-color: #fff;
  box-shadow: 0 4px 8px 0 rgba(7, 17, 27, 0.1);
  z-index: 999;
}

.centerBox {
  padding: 0 30px;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.logoImgSty {
  height: 80px;
}

.boxCenter {
  margin-left: 40px;
  margin-right: 50px;
}

.bar {
  display: flex;
  flex-direction: row;
}

.bar li {
  margin-left: 80px;
  list-style: none;
}

.leftBox {
  display: flex;
  justify-content: flex-end;
  flex-direction: row;
}

.leftBox a {
  color: #333;
  text-decoration: none;
}

.leftBox a:hover {
  color: #e94d0b;
}

/* 轮播图样式 */
.bannerImg {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
  box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.3);
}

/* 课程展示样式 */
.course-container {
  padding: 20px;
  background-color: #f5f7fa;
}

.course-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: flex-start; /* 左对齐 */
  max-width: 1280px; /* 限制最大宽度 */
  margin: 0 auto; /* 居中 */
}

.course-card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  width: calc(25% - 15px); /* 每行四个卡片，减去间隙 */
  overflow: hidden;
  transition: transform 0.3s ease;
  cursor: pointer;
}

.course-card:hover {
  transform: translateY(-5px);
}

.course-cover {
  width: 100%;
  height: 180px;
  object-fit: cover;
}

.course-info {
  padding: 15px;
}

.course-title {
  font-size: 18px;
  margin-bottom: 10px;
  color: #333;
}

.course-description {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}

.course-price {
  font-size: 16px;
  color: #f01414;
  font-weight: bold;
}

.course-tag {
  font-size: 12px;
  color: #fff;
  background-color: #67c23a;
  padding: 4px 8px;
  border-radius: 4px;
  display: inline-block;
  margin-top: 10px;
}

/* 分页样式 */
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

/* 放大卡片样式 */
.expanded-card-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.expanded-card {
  background-color: #fff;
  border-radius: 8px;
  width: 90%;
  max-width: 1200px; /* 增加最大宽度 */
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  display: flex;
}

.expanded-left {
  flex: 1;
  margin-right: 20px;
}

.expanded-right {
  flex: 1;
  margin-left: 20px;
}

.expanded-cover {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 8px;
}

.expanded-info {
  margin-top: 20px;
  text-align: center;
}

.expanded-title {
  font-size: 24px;
  margin-bottom: 10px;
  color: #333;
}

.expanded-description {
  font-size: 16px;
  color: #666;
  margin-bottom: 10px;
}

.expanded-price {
  font-size: 20px;
  color: #f01414;
  font-weight: bold;
}

.expanded-tag {
  font-size: 14px;
  color: #fff;
  background-color: #67c23a;
  padding: 4px 8px;
  border-radius: 4px;
  display: inline-block;
  margin-top: 10px;
}

.expanded-actions {
  margin-top: 20px;
}

.buy-button {
  background-color: #67c23a;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 10px;
}

.cancel-button {
  background-color: #f01414;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

/* 评论部分样式 */
.comment-section {
  margin-top: 20px;
  width: 100%;
}

.comment-item {
  margin-bottom: 10px;
  padding: 10px;
  border-bottom: 1px solid #eee;
}

.comment-item p {
  margin: 0;
}

.comment-item small {
  color: #999;
}

.comment-input textarea {
  width: 100%;
  height: 60px;
  margin-top: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.comment-input button {
  margin-top: 10px;
  padding: 5px 10px;
  background-color: #67c23a;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.comment-input button:hover {
  background-color: #5cb85c;
}

/* 点赞部分样式 */
.like-section {
  margin-top: 20px;
}

.like-button {
  padding: 10px 20px;
  background-color: #67c23a;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.like-button.liked {
  background-color: #f01414;
}

.like-button:hover {
  opacity: 0.8;
}

/* 放大动画 */
.zoom-enter-active,
.zoom-leave-active {
  transition: opacity 0.3s, transform 0.3s;
}

.zoom-enter-from,
.zoom-leave-to {
  opacity: 0;
  transform: scale(0.8);
}
</style>

