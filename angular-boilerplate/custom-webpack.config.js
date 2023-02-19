const webpack = require('webpack');
module.exports = {
  plugins: [
    new webpack.DefinePlugin({
      $ENV: {
        ENVIRONMENT: JSON.stringify(process.env.ENVIRONMENT),
        API_CONTEXT: JSON.stringify(process.env.API_CONTEXT),
        API_GATEWAY: JSON.stringify(process.env.API_GATEWAY),
        REDIRECT_URL: JSON.stringify(process.env.REDIRECT_URL),
      }
    })
  ]
};
