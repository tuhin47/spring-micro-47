const {env: {API_TARGET: api_target, API_HOST: host}} = require('process');

console.log({api_target, host});
const proxyConfig = [
  {
    context: ['/api/ws/**', '/api/chat/**'],
    target: api_target || `http://${host ? host : 'localhost'}:8084`,
    pathRewrite: {'^/api': ''},
    secure: false,
    changeOrigin: true
  }, {
    context: ['/api/**'],
    target: api_target || `http://${host ? host : 'localhost'}:9090`,
    pathRewrite: {'^/api': ''},
    secure: false,
    changeOrigin: true
  },
  {
    context: ['/oauth2/**'],
    target: api_target || `http://${host ? host : 'localhost'}:7777`,
    secure: false,
    changeOrigin: true
  }
];

module.exports = proxyConfig
