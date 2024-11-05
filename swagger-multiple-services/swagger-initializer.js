window.onload = function() {
  //<editor-fold desc="Changeable Configuration Block">

  // the following lines will be replaced by docker/configurator, when it runs in a docker-container
  window.ui = SwaggerUIBundle({
    urls: [
        {
            url: "/api-doc/pet-service/swagger.yml",
            name: "pet-service"
        },
        {
            url: "/api-doc/store-service/swagger.yml",
            name: "store-service"
        },
        {
            url: "/api-doc/user-service/swagger.yml",
            name: "user-service"
        }
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

  //</editor-fold>
};
