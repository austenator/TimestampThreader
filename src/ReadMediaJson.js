const fs = require('fs');

const mediaFile = JSON.parse(fs.readFileSync('Test/media.json'));
console.log(mediaFile);