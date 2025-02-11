const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,

  devServer: {
    proxy: {
      '/api': {
        target: "http://127.0.0.1:8088",  // 后端服务地址
        pathRewrite: { '^/api': '' }      // 去掉 /api 前缀
      }
    }
  }
})