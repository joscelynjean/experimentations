const path = require('path');
const fs = require('fs');
const axios = require('axios');

async function main() {

  const bylaws = [];

  // Get bylaws
  const files = fs.readdirSync('./test-data');
  for(var i = 0; i < files.length; i++){
      const filePath = files[i];
      if(filePath.indexOf('.pdf') >= 0) {
          const fileName = filePath.substring(0, filePath.indexOf('.pdf'));
          console.log(`Push ${fileName}`)
          bylaws.push(fileName);
      }
  };

  // For each bylaw, push it to ElasticSearch
  for(var i = 0; i < bylaws.length; i++) {
    const bylawName = bylaws[i];

    console.log(`Loading ${bylawName}...`);
    const data = require(`./test-data/${bylawName}.json`);
    data.fileContent = fs.readFileSync(`./test-data/${bylawName}.pdf`, {encoding: 'base64'});

    var options = {
        method: 'PUT',
        url: `http://localhost:9200/bylaws/_doc/${data.id}`,
        params: {pipeline: 'my-custom-pipeline'},
        headers: {'user-agent': 'vscode-restclient', 'content-type': 'application/json'},
        data: data
      };
        
    try {
      const response = await axios.request(options);
    } catch(err) {
      console.log(`Something wrong with ${bylawName}`);
    }
  }
}

main().then(() => {
  console.log("Done!");
})

