<template>
    <div>
      <div style="display: flex;justify-content:space-evenly; margin-bottom: 30px;">
        <div>
          <el-input v-model="personName" placeholder="请输入内容">
            <template slot="prepend">姓名</template>
          </el-input>
        </div>
        <div>
          角色:
          <el-select v-model="personRole" placeholder="请选择">
            <el-option label="超级管理员" value="1">超级管理员</el-option>
            <el-option label="课程管理员" value="2">课程管理员</el-option>
            <el-option label="章节管理员" value="3">章节管理员</el-option>
          </el-select>
        </div>
        <div>
          状态:
          <el-select v-model="personStatus" placeholder="请选择">
            <el-option label="启用" value="1">启用</el-option>
            <el-option label="禁用" value="0">禁用</el-option>
          </el-select>
        </div>
        <div>
          <el-button  @click="queryAdminHandler" type="primary">查询</el-button>
          <el-button  @click="resetHandler" type="success">重置</el-button>
        </div>
      </div>
      <div> 
        <el-button @click="dialogAddVisible = true" type="primary">添加人员</el-button>
      </div>

     
  
      <div>
        <el-table :data="tableData" stripe>
          <el-table-column prop="adminId" label="序号" ></el-table-column>
          <el-table-column prop="adminNickname" label="姓名" ></el-table-column>
          <el-table-column prop="roleName" label="角色"> </el-table-column>
          <el-table-column prop="adminStatus" label="状态">
            <template slot-scope="scope">
                {{scope.row.adminStatus===1?"启用":"禁用"}}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button size="mini" @click="editHandler(scope.row)" type="primary">编辑</el-button>
                <el-button size="mini"  @click="toggleStatusHandler(scope.row)"  type="info">{{scope.row.adminStatus===0?"启用":"禁用"}}</el-button>
                <el-button @click="resetHandler(scope.row)" size="mini" type="danger" >重置密码</el-button>
            </template>
    </el-table-column>
        </el-table>
      </div>

<!--分页-->
      <div>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="currentPage" :page-sizes="[5, 10, 15, 30]" :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
      </div>
            

            <el-dialog title="修改信息" :visible.sync="dialogFormVisible">
                <el-form :model="formEditData">
                 
                    <el-form-item label="角色" >
                        <el-select v-model="formEditData.personRoleId" placeholder="请选择">
                            <el-option label="超级管理员" :value="1">超级管理员</el-option>
                            <el-option label="课程管理员" :value="2">课程管理员</el-option>
                            <el-option label="章节管理员" :value="3">章节管理员</el-option>
                        </el-select>
                    </el-form-item>
                </el-form>

                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="confirmHandler">确 定</el-button>
                </div>
            </el-dialog>


            <el-dialog title="添加人员" :visible.sync="dialogAddVisible">
              <el-form :model="addFormData">
                <el-form-item label="姓名">
                  <el-input v-model="addFormData.personName"></el-input>
                </el-form-item>
                <el-form-item label="角色">
                  <el-select v-model="addFormData.personRoleId" placeholder="请选择">
                    <el-option label="超级管理员" :value="1">超级管理员</el-option>
                    <el-option label="课程管理员" :value="2">课程管理员</el-option>
                    <el-option label="章节管理员" :value="3">章节管理员</el-option>
                  </el-select>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogAddVisible = false">取 消</el-button>
                <el-button type="primary" @click="addPersonConfirmHandler">确 定</el-button>
              </div>
            </el-dialog>


    </div>
  </template>
  <script>


  export default {
    data() {
      return {
        formEditData:{
            personId:null,
            personRoleId:null
        },
        dialogFormVisible:false,

        personName:null,
        personRole:null,
        personStatus:null,

        tableData: [],

          currentPage:1,
          pageSize:5,
          total:null,
           addFormData: {
              personName: null,
              personRoleId: null
            },
            dialogAddVisible: false,
      }
    },
    methods: {
        
        //重置密码
        resetHandler(val) {
            this.$confirm(`是否重置${val.adminNickname}的密码`, '温馨提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios.get("resetPasswordById",{params:{
                adminId:val.adminId
            }})
            .then(res=>{
                if(res.data.code==1){
                    this.$message({
                        message:"修改成功",
                        type:"success"
                    })

                }else{
                    this.$message({

                        message:"修改失败",
                        type:"error"
                    })
                }
            })
            .catch(error=>{
                console.log(error)
            })


            
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
            // this.$axios.get("resetPasswordById",{params:{
            //     adminId:val.adminId
            // }})
            // .then(res=>{
            //     if(res.data.code==1){
            //     //     this.$message({
            //     //         message:"修改成功",
            //     //         type:"success"
            //     //     })

            //     // }else{
            //         this.$message({

            //             message:"修改失败",
            //             type:"error"
            //         })
            //     }
            // })
            // .catch(error=>{
            //     console.log(error)
            // })
        
           
        },

  //添加

  addPersonConfirmHandler() {
  this.$axios.post("addPerson", this.addFormData)
    .then(res => {
      if (res.data.code === 1) {
        this.$message({
          message: "添加成功",
          type: "success"
        });
        this.queryAdminHandler();
        this.dialogAddVisible = false;
      } else {
        this.$message({
          message: "添加失败",
          type: "error"
        });
      }
    })
    .catch(error => {
      console.log(error);
    });
},

        //状态
        toggleStatusHandler(val) {
            this.$axios.get("startAndStopByAdminId",{
              params:{
                adminId: val.adminId,
                adminStatus: val.adminStatus
              }
            }).then(res=>{
              if(res.data.code==1){
                this.$message({
                  message:'修改成功',
                  type:"success"

                })
              }else{
                this.$message({
                  message:'修改失败',
                  type:"error"
              })
            }
          this.queryAdminHandler();   
        }).catch(error=>{
          console.log(error)
        })
      },


             confirmHandler(){


            this.$axios.get("updateAdminRoleById",{params: this.formEditData})

            .then(res=>{
                if(res.data.code==1){
                    this.$message({
                        message:"修改成功",
                        type:"success"
                    })
                    this.queryAdminHandler();
                }else{
                    this.$message({

                        message:"修改失败",
                        type:"error"
                    })
                }
            })
            .catch(error=>{
                console.log(error)
            })
            //关闭弹窗
            this.dialogFormVisible=false;
        },
        //编辑弹窗
        editHandler(val){
            console.log(val)
            this.formEditData.personId=val.adminId
            this.formEditData.personRoleId=val.adminRoleId
            this.dialogFormVisible=true;
        },
        
      handleSizeChange(val) {
        // console.log(`每页 ${val} 条`);
        this.pageSize=val;
      },
      handleCurrentChange(val) {
        // console.log(`当前页: ${val}`);
        this.currentPage=val;
        this.queryAdminHandler();
      },
      queryAdminHandler(){
        this.$axios.get('queryAdmin',{
            params: {
                currentPage:this.currentPage,
                pageSize:this.pageSize,
                personName:this.personName,
                personRole:this.personRole,
                personStatus:this.personStatus
            }
      })
        .then(res=>{

            this.tableData = res.data.data.list
            this.total=res.data.data.total
        }).catch(error=>{
            console.log(error)
        })
      }
    },
    mounted(){
        this.queryAdminHandler();
    }
  }
  </script>
  <style>
  </style>