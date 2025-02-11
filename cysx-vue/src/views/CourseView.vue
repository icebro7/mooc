<template>
  <div>
    <!-- 查询条件 -->
    <div style="display: flex; justify-content: space-evenly; margin-bottom: 30px;">
      <div>
        <el-input v-model="courseName" placeholder="请输入课程名称">
          <template slot="prepend">课程名称</template>
        </el-input>
      </div>
      <div>
        类别:
        <el-select v-model="courseSort" placeholder="请选择类别">
          <el-option label="前端" value="1"></el-option>
          <el-option label="后端" value="2"></el-option>
        </el-select>
      </div>
      <div>
        课程状态:
        <el-select v-model="courseStatus" placeholder="请选择状态">
          <el-option label="上架" value="1"></el-option>
          <el-option label="下架" value="0"></el-option>
        </el-select>
      </div>
      <div>
        <el-button @click="queryCourseOfName" type="primary">查询</el-button>
        <el-button @click="resetQuery" type="primary">重置</el-button>
      </div>
    </div>

    <!-- 添加课程按钮 -->
    <div style="margin-bottom: 20px;">
      <el-button @click="dialogAddVisible = true" type="primary">添加课程</el-button>
    </div>

    <!-- 课程表格 -->
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="courseId" label="序号"></el-table-column>
      <el-table-column prop="courseName" label="课程名称"></el-table-column>
      <el-table-column prop="courseSort" label="课程类别">
        <template slot-scope="scope">
          {{ scope.row.courseSort === "1" ? "前端" : "后端" }}
        </template>
      </el-table-column>
      <el-table-column prop="courseHits" label="点击量"></el-table-column>
      <el-table-column prop="coursePrice" label="课程价格"></el-table-column>
      <el-table-column prop="courseCoverPath" label="课程封面">
        <template slot-scope="scope">
          <img :src="scope.row.courseCoverPath" style="width: 50px; height: 50px;" />
        </template>
      </el-table-column>
      <el-table-column prop="courseStatus" label="状态">
        <template slot-scope="scope">
          {{ scope.row.courseStatus === 1 ? "上架" : "下架" }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="openEditDialog(scope.row)">编辑</el-button>
          <el-button size="mini" @click="toggleStatusHandler(scope.row)" type="info">
            {{ scope.row.courseStatus === 0 ? "上架" : "下架" }}
          </el-button>
          <el-button size="mini" type="danger" @click="showIntroduction(scope.row)">课程简介</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15, 30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
    ></el-pagination>

    <!-- 添加课程对话框 -->
    <el-dialog title="添加课程" :visible.sync="dialogAddVisible" width="30%">
      <el-form :model="addForm" label-width="100px">
        <el-form-item label="课程名称">
          <el-input v-model="addForm.courseName" placeholder="请输入课程名称"></el-input>
        </el-form-item>
        <el-form-item label="课程类别">
          <el-select v-model="addForm.courseSort" placeholder="请选择类别">
            <el-option label="前端" value="1"></el-option>
            <el-option label="后端" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程简介">
          <el-input v-model="addForm.courseIntroduction" placeholder="请输入课程简介"></el-input>
        </el-form-item>
        <el-form-item label="课程价格">
          <el-input v-model="addForm.coursePrice" placeholder="请输入课程价格"></el-input>
        </el-form-item>
        <el-form-item label="课程封面">
          <el-input v-model="addForm.courseCoverPath" placeholder="请输入课程封面链接"></el-input>
        </el-form-item>
        <el-form-item label="课程状态">
          <el-select v-model="addForm.courseStatus" placeholder="请选择状态">
            <el-option label="上架" value="1"></el-option>
            <el-option label="下架" value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogAddVisible = false">取消</el-button>
        <el-button type="primary" @click="addCourse">确认</el-button>
      </span>
    </el-dialog>

    <!-- 编辑课程对话框 -->
    <el-dialog title="编辑课程" :visible.sync="dialogEditVisible" width="30%">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="课程名称">
          <el-input v-model="editForm.courseName" placeholder="请输入课程名称"></el-input>
        </el-form-item>
        <el-form-item label="课程类别">
          <el-select v-model="editForm.courseSort" placeholder="请选择类别">
            <el-option label="前端" value="1"></el-option>
            <el-option label="后端" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程简介">
          <el-input v-model="editForm.courseIntroduction" placeholder="请输入课程简介"></el-input>
        </el-form-item>
        <el-form-item label="课程价格">
          <el-input v-model="editForm.coursePrice" placeholder="请输入课程价格"></el-input>
        </el-form-item>
        <el-form-item label="课程封面">
          <el-input v-model="editForm.courseCoverPath" placeholder="请输入课程封面链接"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogEditVisible = false">取消</el-button>
        <el-button type="primary" @click="updateCourse">确认</el-button>
      </span>
    </el-dialog>

    <!-- 显示课程简介的对话框 -->
    <el-dialog title="课程简介" :visible.sync="dialogIntroductionVisible" width="30%">
      <p>{{ introductionContent }}</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogIntroductionVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 查询条件
      courseName: "",
      courseSort: "",
      courseStatus: "",

      // 表格数据
      tableData: [],

      // 分页
      currentPage: 1,
      pageSize: 5,
      total: 0,

      // 添加课程对话框
      dialogAddVisible: false,
      addForm: {
        courseName: "",
        courseSort: "",
        courseIntroduction: "",
        coursePrice: "",
        courseCoverPath: "",
        courseStatus: "",
      },

      // 编辑课程对话框
      dialogEditVisible: false,
      editForm: {
        courseId: "",
        courseName: "",
        courseSort: "",
        courseIntroduction: "",
        coursePrice: "",
        courseCoverPath: "",
      },

      // 显示课程简介的对话框
      dialogIntroductionVisible: false,
      introductionContent: "", // 课程简介内容
    };
  },
  methods: {
    // 查询课程
    queryCourseOfName() {
      this.currentPage = 1;
      this.queryCourseHandler();
    },

    // 查询课程数据
    queryCourseHandler() {
      this.$axios
          .get("/queryCourse", {
            params: {
              currentPage: this.currentPage,
              pageSize: this.pageSize,
              courseName: this.courseName,
              courseSort: this.courseSort,
              courseStatus: this.courseStatus,
            },
          })
          .then((res) => {
            this.tableData = res.data.data.list;
            this.total = res.data.data.total;
          })
          .catch((error) => {
            console.log(error);
          });
    },

    // 重置查询条件
    resetQuery() {
      this.courseName = "";
      this.courseSort = "";
      this.courseStatus = "";
      this.queryCourseOfName();
    },

    // 分页大小变化
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.queryCourseHandler();
    },

    // 当前页变化
    handleCurrentChange(val) {
      this.currentPage = val;
      this.queryCourseHandler();
    },

    // 添加课程
    addCourse() {
      this.$axios
          .post("/addCourse", this.addForm)
          .then((res) => {
            if (res.data.code === 1) {
              this.$message({
                message: "添加成功",
                type: "success",
              });
              this.dialogAddVisible = false;
              this.queryCourseHandler();
            } else {
              this.$message({
                message: "添加失败",
                type: "error",
              });
            }
          })
          .catch((error) => {
            console.log(error);
            this.$message({
              message: "添加失败",
              type: "error",
            });
          });
    },

    // 打开编辑对话框
    openEditDialog(row) {
      this.editForm = {
        courseId: row.courseId,
        courseName: row.courseName,
        courseSort: row.courseSort,
        courseIntroduction: row.courseIntroduction,
        coursePrice: row.coursePrice,
        courseCoverPath: row.courseCoverPath,
      };
      this.dialogEditVisible = true;
    },

    // 更新课程信息
    updateCourse() {
      this.$axios
          .post("/updateCourse", this.editForm)
          .then((res) => {
            if (res.data.code === 1) {
              this.$message({
                message: "更新成功",
                type: "success",
              });
              this.dialogEditVisible = false;
              this.queryCourseHandler();
            } else {
              this.$message({
                message: "更新失败",
                type: "error",
              });
            }
          })
          .catch((error) => {
            console.log(error);
            this.$message({
              message: "更新失败",
              type: "error",
            });
          });
    },

    // 切换课程状态
    toggleStatusHandler(row) {
      const newStatus = row.courseStatus === 0 ? 1 : 0; // 切换状态
      this.$axios
          .post("/toggleCourseStatus", {
            courseId: row.courseId,
            courseStatus: newStatus,
          })
          .then((res) => {
            if (res.data.code === 1) {
              this.$message({
                message: "状态切换成功",
                type: "success",
              });
              this.queryCourseHandler(); // 刷新课程列表
            } else {
              this.$message({
                message: "状态切换失败",
                type: "error",
              });
            }
          })
          .catch((error) => {
            console.log(error);
            this.$message({
              message: "状态切换失败",
              type: "error",
            });
          });
    },

    // 显示课程简介
    showIntroduction(row) {
      this.$axios
          .get("/getCourseIntroduction", {
            params: {
              courseId: row.courseId,
            },
          })
          .then((res) => {
            if (res.data.code === 1) {
              this.introductionContent = res.data.data;
              this.dialogIntroductionVisible = true;
            } else {
              this.$message.error("获取课程简介失败");
            }
          })
          .catch((error) => {
            console.log(error);
            this.$message.error("获取课程简介失败");
          });
    },
  },
  mounted() {
    this.queryCourseHandler();
  },
};
</script>

<style>
</style>