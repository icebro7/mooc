<template>
  <div>
    <!-- 查询条件 -->
    <div style="display: flex; justify-content: space-evenly; margin-bottom: 30px;">
      <div>
        <el-input v-model="chapterName" placeholder="请输入章节名称">
          <template slot="prepend">章节名称</template>
        </el-input>
      </div>
      <div>
        <el-button @click="queryChapterOfName" type="primary">查询</el-button>
        <el-button @click="resetQuery" type="primary">重置</el-button>
      </div>
    </div>

    <!-- 添加章节按钮 -->
    <div style="margin-bottom: 20px;">
      <el-button @click="dialogAddVisible = true" type="primary">添加章节</el-button>
    </div>

    <!-- 章节表格 -->
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="chapterId" label="序号"></el-table-column>
      <el-table-column prop="chapterName" label="章节名称"></el-table-column>
      <el-table-column prop="courseId" label="课程ID"></el-table-column>
      <el-table-column prop="orderId" label="排序"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="openEditDialog(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteChapter(scope.row)">删除</el-button>
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

    <!-- 添加章节对话框 -->
    <el-dialog title="添加章节" :visible.sync="dialogAddVisible" width="30%">
      <el-form :model="addForm" label-width="100px">
        <el-form-item label="章节名称">
          <el-input v-model="addForm.chapterName" placeholder="请输入章节名称"></el-input>
        </el-form-item>
        <el-form-item label="课程ID">
          <el-input v-model="addForm.courseId" placeholder="请输入课程ID"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="addForm.orderId" placeholder="请输入排序"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogAddVisible = false">取消</el-button>
        <el-button type="primary" @click="addChapter">确认</el-button>
      </span>
    </el-dialog>

    <!-- 编辑章节对话框 -->
    <el-dialog title="编辑章节" :visible.sync="dialogEditVisible" width="30%">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="章节名称">
          <el-input v-model="editForm.chapterName" placeholder="请输入章节名称"></el-input>
        </el-form-item>
        <el-form-item label="课程ID">
          <el-input v-model="editForm.courseId" placeholder="请输入课程ID"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="editForm.orderId" placeholder="请输入排序"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogEditVisible = false">取消</el-button>
        <el-button type="primary" @click="updateChapter">确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 查询条件
      chapterName: "",

      // 表格数据
      tableData: [],

      // 分页
      currentPage: 1,
      pageSize: 5,
      total: 0,

      // 添加章节对话框
      dialogAddVisible: false,
      addForm: {
        chapterName: "",
        courseId: "",
        orderId: "",
      },

      // 编辑章节对话框
      dialogEditVisible: false,
      editForm: {
        chapterId: "",
        chapterName: "",
        courseId: "",
        orderId: "",
      },
    };
  },
  methods: {
    // 查询章节
    queryChapterOfName() {
      this.currentPage = 1;
      this.queryChapterHandler();
    },

    // 查询章节数据
    queryChapterHandler() {
      this.$axios
          .get("/queryChapter", {
            params: {
              currentPage: this.currentPage,
              pageSize: this.pageSize,
              chapterName: this.chapterName,
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
      this.chapterName = "";
      this.queryChapterOfName();
    },

    // 分页大小变化
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.queryChapterHandler();
    },

    // 当前页变化
    handleCurrentChange(val) {
      this.currentPage = val;
      this.queryChapterHandler();
    },

    // 添加章节
    addChapter() {
      this.$axios
          .post("/addChapter", this.addForm)
          .then((res) => {
            if (res.data.code === 1) {
              this.$message({
                message: "添加成功",
                type: "success",
              });
              this.dialogAddVisible = false;
              this.queryChapterHandler();
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
        chapterId: row.chapterId,
        chapterName: row.chapterName,
        courseId: row.courseId,
        orderId: row.orderId,
      };
      this.dialogEditVisible = true;
    },

    // 更新章节信息
    updateChapter() {
      this.$axios
          .post("/updateChapter", this.editForm)
          .then((res) => {
            if (res.data.code === 1) {
              this.$message({
                message: "更新成功",
                type: "success",
              });
              this.dialogEditVisible = false;
              this.queryChapterHandler();
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

    // 删除章节
    deleteChapter(row) {
      this.$axios
          .post("/deleteChapter", { chapterId: row.chapterId })
          .then((res) => {
            if (res.data.code === 1) {
              this.$message({
                message: "删除成功",
                type: "success",
              });
              this.queryChapterHandler();
            } else {
              this.$message({
                message: "删除失败",
                type: "error",
              });
            }
          })
          .catch((error) => {
            console.log(error);
            this.$message({
              message: "删除失败",
              type: "error",
            });
          });
    },
  },
  mounted() {
    this.queryChapterHandler();
  },
};
</script>

<style>
</style>