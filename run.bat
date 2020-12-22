javac src/test/*.java src/main/java/utfj/*.java -d "./src/test/build" -cp lib/jna-5.6.0.jar
java -cp "./lib/jna-5.6.0.jar;./src/test/build" Main