const { Client, logger, Variables, BasicAuthInterceptor } = require('camunda-external-task-client-js');
const { faker } = require('@faker-js/faker');
const util = require('util')

const basicAuthentication = new BasicAuthInterceptor({
  username: 'demo',
  password: 'demo',
});

// configuration for the Client:
//  - 'baseUrl': url to the Process Engine
//  - 'logger': utility to automatically log important events
//  - 'asyncResponseTimeout': long polling timeout (then a new request will be issued)
const config = {
  baseUrl: 'http://localhost:8080/engine-rest',
  use: logger,
  asyncResponseTimeout: 5000,
  interceptors: basicAuthentication,
  
};

// create a Client instance with custom configuration
const client = new Client(config);

client.on('subscribe', (topic) => {
  console.log(`Successfully subscribe to topic ${topic}`);
});

client.on('unsubscribe', (topic) => {
  console.log(`Successfully unsubscribe from topic ${topic}`);
});

client.on('poll:start', () => {
  console.log('Camunda polling started...');
});

client.on('poll:error', (error) => {
  console.log(
    error,
    'Camunda polling stopped. Something occured with the camunda client or Camunda...'
  );
  client.stop();
});

client.on('complete:error', (error) => {
  console.log({ error }, `Camunda worker couldn't complete the topic ${error.topicName}.`);
});

client.on('handleFailure:error', (task, error) => {
  console.log({ error }, `Camunda worker couldn't handle failure of task ${task.id}.`);
});

client.on('handleBpmnError:error', (task, error) => {
  console.log({ error }, `Camunda worker couldn't handle BPMN failure of task ${task.id}.`);
});

client.on('extendLock:error', (task, error) => {
  console.log({ error }, `Camunda worker couldn't extend lock of task ${task.id}.`);
});

client.on('unlock:error', (task, error) => {
  console.log({ error }, `Camunda worker couldn't unlock task ${task.id}.`);
});

client.on('extendLock:error', (task, error) => {
  console.log({ error }, `Camunda worker couldn't lock task ${task.id}.`);
});

// Use to wait
function timeout(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

client.subscribe('GENERATE_OVER_LIMIT_JSON', async ({ task, taskService }) => {
  try {
    console.log('Received GENERATE_OVER_LIMIT_JSON message');

    // Generate over 4000 char json
    const bigJson = {};
    for(let i = 0; i < 1000; i++) {
      bigJson[`prop${i}`] = faker.random.alphaNumeric(15);
    }

    const variables = new Variables();
    variables.set('generatedOverLimitVariable', bigJson);

    await taskService.complete(task, variables);
  } catch(err) {
    console.log(err);
  }
});

// TRANSMIT_OVER_LIMIT_JSON
client.subscribe('TRANSMIT_OVER_LIMIT_JSON', async ({ task, taskService }) => {
  try {
    console.log('Received TRANSMIT_OVER_LIMIT_JSON message');

    const overLimitVariableValue = task.variables?.get('transmittedOverLimitVariable');
    console.log(`Input value : ${util.inspect(overLimitVariableValue)}`);
  
    await taskService.complete(task);
  } catch(err) {
    console.log(err);
  }
});
