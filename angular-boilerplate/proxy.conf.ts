var process = require("process");
var api_target = process.env.API_TARGET;
console.log('Proxy configuration: ' + api_target);
const proxyConfig = [
    {
        context: ["/api/**"],
        target: api_target || "http://localhost:3000", //mock server
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
