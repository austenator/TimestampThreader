import * as fs from 'fs';

/**
 * Reads in a JSON file and returns the parsed string.
 * @param {string} jsonFilePath the path to the json to parse
 */
export function readJson(jsonFilePath) {
    const mediaFile = JSON.parse(fs.readFileSync(jsonFilePath));
    console.log(mediaFile);
}