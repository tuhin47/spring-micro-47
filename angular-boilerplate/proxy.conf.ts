const proxyConfig = [
    {
        context: ["/api/**"],
        target: "http://localhost:9090",
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
