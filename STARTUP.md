## Gradle Setup ##
The initiatives board is a gradle project, all the dependencies are in the build.gradle file and all that is required to build the program is gradle version 6+ installed as well as Java 8.

## Pipeline Setup ##
The most optimable way to build the CI/CD pipeline for the project is to use 2 EC2 instances to host the front end pipeline and another for the Spring Boot Application.
The dockerfile and Jenkinsfile are provided in the repository in order to be used in a Jenkins multibranch pipeline.

## Common problems and fixes ##
When running a gradle build in the EC2 through Jenkins the imga might run out of swap memory, for that making a swapfile with more memory will fix the issue:

sudo fallocate -l 1G /swapfile

**sudo chmod 600 /swapfile**

**sudo mkswap /swapfile**

**sudo swapon /swapfile**

The series of commands above will create a swapfile with 1GB of space and will resolve the gradle build problem.

----------------------------------------------------------------------------------------------------------------------

Another common problem is the docker daemon refusing to start and connect to the docker sock, navigating to **var/run/** directory in Amazon Linux 2 and changing the permissions of the docker.sock file will resolve the problem.
