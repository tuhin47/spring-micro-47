/* eslint-disable @typescript-eslint/naming-convention */
// Install - npm install @r35007/mock-server

const path = require("path");
const { MockServer, watcher } = require("@r35007/mock-server");


const config = {
  root: path.resolve(__dirname, "../"), // All fetch paths will be relative to this path
  port: 9090, // Set Port to 0 to pick a random available port. default: 9090
  host: "localhost", // Set empty string to set your Local Ip Address
  quiet: false, // Set to true to suppress console logs
  log: false // Set to true to see setter logs. If quiet is false the we cant see the setter logs.
};
const mockServer = MockServer.Create(config);

const startServer = async () => {
  await mockServer.launchServer("./db.json", {
    injectors: "./injectors.json",
    middlewares: "./middlewares.js",
    rewriters: "./rewriters.json",
    store: "./store.json",
  });
};

startServer().then(() => {
  const watch = watcher.watch(mockServer.config.root);

  // Restart server on change
  watch.on('change', async () => {
    if (!mockServer.server) return; // return if no server to stop
    await MockServer.Destroy(mockServer);
    await startServer();
  });
});
