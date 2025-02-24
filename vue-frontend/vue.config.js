  // const { defineConfig } = require('@vue/cli-service')
  //
  // module.exports = defineConfig({
  //   transpileDependencies: true,
  //   lintOnSave:false, // 语法提示
  //
  //   devServer: {
  //     proxy: {
  //       '/api': {
  //         target: 'http://localhost:8080',  // 后端 API 地址
  //         changeOrigin: true,  // 允许修改请求头中的 Origin 字段
  //         pathRewrite: {
  //           '^/api': '',  // 将请求中的 /api 去掉，直接访问后端 API
  //         },
  //       },
  //     },
  //   },
  //
  //   chainWebpack: (config) => { // 关闭runtime-core.esm-bundler.js:4264   Feature flag __VUE_PROD_HYDRATION_MISMATCH_DETAILS__ is not explicitly defined.提示
  //     config.plugin('define').tap((definitions) => {
  //       Object.assign(definitions[0], {
  //         __VUE_OPTIONS_API__: 'true',
  //         __VUE_PROD_DEVTOOLS__: 'false',
  //         __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'false'
  //       })
  //       return definitions
  //     })
  //   },
  // })
  const { defineConfig } = require('@vue/cli-service')

  module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
      // 配置代理
      proxy: {
        '/api': {
          target: 'http://localhost:8080',  // 后端 API 地址
          changeOrigin: true,  // 允许修改请求头中的 Origin 字段
          pathRewrite: {
            '^/api': '',  // 将请求中的 /api 去掉，直接访问后端 API
          },
        },
      },
      // // 配置前端端口（如果有冲突，可以修改成不同的端口，比如 8081）
       port: 8081
    },
    publicPath: '/',
    outputDir:'dist',
    assetsDir:'static',
    chainWebpack: (config) => { // 关闭runtime-core.esm-bundler.js:4264   Feature flag __VUE_PROD_HYDRATION_MISMATCH_DETAILS__ is not explicitly defined.提示
      config.plugin('define').tap((definitions) => {
        Object.assign(definitions[0], {
          __VUE_OPTIONS_API__: 'true',
          __VUE_PROD_DEVTOOLS__: 'false',
          __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'false'
        })
        return definitions
      })
    },
  })
