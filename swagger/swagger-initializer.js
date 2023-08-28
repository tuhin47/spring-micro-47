window.onload = function() {
  //<editor-fold desc="Changeable Configuration Block">

  // the following lines will be replaced by docker/configurator, when it runs in a docker-container
  window.ui = SwaggerUIBundle({
    urls: [
      /*{url:"https://tinyurl.com/spring47Auth",name :"Auth" },
      {url:"https://tinyurl.com/spring47Product",name :"Product" },
      {url:"https://tinyurl.com/spring47Payment",name :"Payment" },
      {url:"https://tinyurl.com/spring47Order",name :"Order" },*/
      {url:"swagger/json/auth.json",name :"Auth" },
      {url:"swagger/json/product.json",name :"Product" },
      {url:"swagger/json/payment.json",name :"Payment" },
      {url:"swagger/json/order.json",name :"Order" },
    ],
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout"
  });

  console.log(SwaggerUIBundle.plugins.DownloadUrl);

  //</editor-fold>
};
