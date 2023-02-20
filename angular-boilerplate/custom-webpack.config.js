const webpack = require('webpack');
module.exports = {
  plugins: [
    new webpack.DefinePlugin({
      $ENV: {
        ENVIRONMENT: JSON.stringify(process.env.ENVIRONMENT),
        API_BASE: JSON.stringify(process.env.API_BASE),
        OAUTH_REDIRECT_URL: JSON.stringify(process.env.OAUTH_REDIRECT_URL),
      }
    })
  ]
};
