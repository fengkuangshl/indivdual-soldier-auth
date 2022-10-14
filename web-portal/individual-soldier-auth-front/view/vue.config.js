module.exports = {
  outputDir: 'build/' + (process.env.outputDir ? process.env.outputDir : 'dist'),
  devServer: {
    proxy: {
      '/api': {
        // target: 'https://192.168.1.147:8443',
        target: 'http://192.168.1.116:9200',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}
