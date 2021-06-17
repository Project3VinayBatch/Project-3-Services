node{
   def buildNum = BUILD_NUMBER
  stage("clone"){
        git url: "https://github.com/Project3VinayBatch/Project-3-Services", branch: 'master'
    }
  stage("Build app"){
        sh "chmod +x gradlew"
        sh "./gradlew clean build --no-daemon"
    }
    stage("Build docker image"){
        sh "docker build -t project3-services:${buildNum} ." 
    }
    
  stage("Run docker image"){
   sh  "docker rm -f project3-services || true"
  sh "docker run -t -dp 80:8080 --env-file /secret.properties --name project3-services project3-services:${buildNum}"
  }

}
