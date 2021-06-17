# Project-3-Services
## EC2 Setup
Install Jenkins on your Es2 and use the recommended plugins.
Install Java 1.8.0
Install Docker

## Project Overview 
Repository for all the services in Project 3.
**Revature's Strategic Initiatives Board** is an application designed for business level and development level users to track facilitate and manage team based collaboration on community driven initiatives. This application will enable users to view all initiatives currently being developed. Strategic initiatives board will additionally allow users to create new initiatives and assign users to lead them. Strategic Initiatives Board will provide a space to opt into initiatives, and designate which developers are working on which initiatives. This application also provides a space to share brainstorming and progress reports on these initiatives. There are two roles in the application, ADMIN and USER, ADMIN can do everything an user can, view specific initiatives, view all initiatives, post files to the S3 bucket, join an initiative, and view points of contacts and files that have been uploaded. ADMIN can also add new inititives and mark a point of contact for an initiative.

## Technical Points
The fie upload works with S3, a secret.properties file must be created or environment variables set in the deployment and production environments so that the application can choose the right S3 bucket to upload to as well as the database that it will be using. The secret.properties or environment variables will also have to be set for the github user that will be authorizing accounts for login to the application. **DO NOT** expose the secret.properties file on github or anywhere else on the internet, this can put the S3 and AWS accounts in a severe security risk.

## Setup
After cloning this repository in the root directory and installing gradle running **gradle build** will create a jar in the **build/lib** directory where the springboot appliction can be started from, otherwise running it straight from an IDE will also suffice. All that is needed to run is Java 1.8.0 and a gradle version above 6.

