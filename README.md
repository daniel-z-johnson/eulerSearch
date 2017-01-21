# Project Euler Search
The goal of this project is to create simple web app that enables searching [projectEuler](projecteuler.net) problems using key words by scrapping the website and putting the problems in an Elasticsearch database.

####Set up
1. Download and install/start Elasticsearch you should read the user guide also if you konw nothing of Elasticsearch
2. Create the index needed for application, I procide two methods for this
   1. If you have a mac of Linux machine 
      - then you could copy the line in src/main/resources/elasticsearchSetup/mappingCurlCommand.txt 
      - past the line in terminal. Press the Enter key
      - You should get a response like this {"acknowledged":true,"shards_acknowledged":true} if everything went right.
   2. If you have a Windows machine or do not want to use the command line for some reason
      - Use the Postman plugin for Chrome
      - type '127.0.0.1:9200/euler' in the place for the url
      - make the request a 'PUT' 
      - click on 'body'
      - click 'raw' 
      - from the drop down menu select 'application/json'
      - copy and past the content from src/main/resources/elasticsearchSetUp/euler.json 
      - click send 
      - you should get a '200 Ok' response if everything went right
      
      
####Testing
1. Use an IDE to run tests or on the command line run the command 'mvn test'
   - Tests will assume that you have elastic search running on localhost with transport client on port 9200
   - One way to override with url and port is as follows
    - -Des.port=9200 to override the port use, replace 9200 with the port you want to use
    - -Des.url=127.0.0.1 to override the url used, replace 127.0.0.1 with the url you want to use