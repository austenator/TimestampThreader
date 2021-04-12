# Social Media Timestamp Threader
Threads downloaded social media data with their respective timestamps.
### Trello Board
- https://trello.com/b/MjoNYlHw/time-stamp-threader
## Problem
The problem this solves is that when you download your social media data, they do not include the original timestamp of
the images in the picture's medatdata, but instead keep it in JSON files.

## How This Solves the Problem
This Java application takes in the path of the downloaded folder and then outputs a folder of all your images with their
respective timestamps.

## Supported Social Media Apps (Currently)
1. Instagram
2. Snapchat

# Setup
Clean and package the JAR
> mvn clean package

Change directories into the target folder (or wherever you move the JAR).
> cd target

Run the JAR.
> java -jar TimestampThreader-1.0-SNAPSHOT.jar
