const {env: {API_TARGET: api_target}} = require('process');

console.log('Proxy configuration: ' + api_target);
const proxyConfig = [
    {
        context: ["/api/**"],
        target: api_target || "http://localhost:9090", //mock server
        pathRewrite: {'^/api': ''},
        secure: false,
        changeOrigin: true
    },
    {
        context: ["/oauth2/**"],
        target: "http://localhost:7777",
        // pathRewrite: {'^/links': ''},
        secure: false,
        changeOrigin: true
    }
];

module.exports = proxyConfig
