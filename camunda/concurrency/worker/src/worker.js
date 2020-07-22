// DON'T DO THIS IN PRODUCTION ENVIRONMENT
process.env['NODE_TLS_REJECT_UNAUTHORIZED'] = 0;

// Mongo database connection
let db = null;
let MongoClient = require('mongodb').MongoClient;

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
  asyncResponseTimeout: 5000,
  interceptors: basicAuthentication,
};

// create a Client instance with custom configuration
const client = new Client(config);

// Use to wait
function timeout(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

async function start() {
  const mongoClient = await MongoClient.connect('mongodb://root@localhost:27017/', {
    password: 'root',
  });
  db = mongoClient.db();

  // susbscribe to the topic: 'charge-card'
  await client.subscribe('register_uuid', async function ({ task, taskService }) {
    // Get a process variable
    const uuid = task.variables.get('uuid');
    console.log(`Got ${uuid}`);

    try {
      await db.collection('UUIDCollection').insertOne({ uuid });
    } catch (err) {
      if (err.code === 11000) {
        // Duplicate key
        console.log(`Erreur de duplication de clé pour ${uuid}.`);
      } else {
        await taskService.unlock(task);
        throw err;
      }
    }

    // Complete the task
    await taskService.complete(task);
  });
}

start();
