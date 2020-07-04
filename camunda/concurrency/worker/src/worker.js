// DON'T DO THIS IN PRODUCTION ENVIRONMENT
process.env['NODE_TLS_REJECT_UNAUTHORIZED'] = 0;

const { Client, logger, BasicAuthInterceptor } = require('camunda-external-task-client-js');

const basicAuthentication = new BasicAuthInterceptor({
  username: 'demo',
  password: 'demo',
});

// configuration for the Client:
//  - 'baseUrl': url to the Process Engine
//  - 'logger': utility to automatically log important events
//  - 'asyncResponseTimeout': long polling timeout (then a new request will be issued)
const config = {
  baseUrl: 'https://nginx.local/engine-rest',
  use: logger,
  asyncResponseTimeout: 10000,
  interceptors: basicAuthentication,
};

// create a Client instance with custom configuration
const client = new Client(config);

// susbscribe to the topic: 'charge-card'
client.subscribe('register_uuid', async function ({ task, taskService }) {
  // Get a process variable
  const uuid = task.variables.get('uuid');
  console.log(`Got ${uuid}`);

  // Complete the task
  await taskService.complete(task);
});
