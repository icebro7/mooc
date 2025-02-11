<template>
    <div class="main-container">
        <div class="login-container">
            <h2>管理员登录</h2>

            <label for="username">账号：</label>
            <input type="text" id="username" name="username" v-model="userData.account" placeholder="请输入账号" required>

            <label for="password">密码：</label>
            <input type="password" id="password" name="password" v-model="userData.password" placeholder="请输入密码" required>

            <label for="captcha">验证码：</label>
            <div class="captcha-container">
                <input type="text" id="captcha" v-model="userData.verificationCode" name="captcha" placeholder="请输入验证码" required>
                <div  @click="getCode" class="captcha" id="captchaDisplay">
                    <img :src="`data:image/png;base64,${imageBase64}`">
                </div>
            </div>

            <button @click="loginHandler" button>登录</button>
        </div>
    </div>

</template>

<script>

export default {
    data() {
        return {
            //用户数据
            userData: {
                account: "",
                password: "",
                verificationCode: "",
                unique: null
            },

            //验证码
            imageBase64: ""

        }
    }, methods: {
        //验证码
        getCode(){
            this.$axios.get("/code",{
                params:{
                    unique:this.userData.unique
                }
            })
            .then(res=>{
                console.log(res.data)
                this.imageBase64=res.data.data.codeImage
                this.userData.unique=res.data.data.uuid
            }).catch(error=>{
                console.log(error)
            })
        },
        //登录
        loginHandler(){
            this.$axios.get('/alogin',{
                params:this.userData
            })
            .then(response => {
    // 处理成功情况
                 console.log(response.data.data);
                 if(response.data.code===1){
                    this.$message({
                    message:'登录成功',
                    type:'success'
            });

                localStorage.setItem("cysx_token",response.data.data)
                this.$router.push("/main")
                }else if(response.data.code===2){
                    this.$message.error("验证码错误");
                }else if(response.data.code===3){
                    this.$message.error("账号被冻结，请联系管理员！");
                }
                   else {
                this.$message.error("登录失败");
        }
  })
  .catch(function (error) {
    // 处理错误情况
    console.log(error);
  })
  .finally(function () {
    // 总是会执行
  });
        }

    },

    mounted(){
        this.getCode();
    }
}
</script>

<style scoped>
* {
    box-sizing: border-box;
}

.main-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f0f2f5;
}

.login-container {
    width: 360px;
    padding: 25px;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.login-container h2 {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
    font-size: 1.8em;
}

.login-container label {
    font-size: 0.95em;
    color: #555;
}

.login-container input[type="text"],
.login-container input[type="password"] {
    width: 100%;
    padding: 12px;
    margin: 10px 0 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 1em;
    outline: none;
    transition: border-color 0.3s ease;
}

.login-container input[type="text"]:focus,
.login-container input[type="password"]:focus {
    border-color: #4CAF50;
}

.captcha-container {
    display: flex;
    align-items: center;
    gap: 10px;
}

.captcha {
    background-color: #e0e0e0;
    padding: 10px;
    font-size: 1.2em;
    color: #333;
    border-radius: 5px;
    user-select: none;
    font-weight: bold;
}

.login-container button {
    width: 100%;
    padding: 12px;
    background-color: #4CAF50;
    color: white;
    font-size: 1em;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.login-container button:hover {
    background-color: #45a049;
}
</style>