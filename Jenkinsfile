pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
          PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
          DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
          DOCKERHUB_REPO = 'annialanen/fuel_calc'
          DOCKER_IMAGE_TAG = 'latest'
          SONARQUBE_SERVER = 'SonarQubeServer'
      }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/annimala/FuelCalculator.git'
            }
        }

        stage('Build & test') {
            steps {
                bat 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    bat """
                                ${tool 'SonarScanner'}\\bin\\sonar-scanner ^
                                -Dsonar.projectKey=myproject ^
                                -Dsonar.sources=src/main/java ^
                                -Dsonar.projectName=FuelCalculator ^
                                -Dsonar.host.url=http://localhost:9000 ^
                                -Dsonar.token=${env.SONAR_TOKEN} ^
                                -Dsonar.java.binaries=target/classes ^
                                -Dsonar.java.test.binaries=target/test-classes ^
                                -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                                -Dsonar.coverage.exclusions=**/FuelController.java,**/Main.java
                            """
                }
            }
        }


         stage('Build Docker Image') {
              steps {
                 script {
                     docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                 }
              }
         }

         stage('Push Docker Image to Docker Hub') {
                  steps {
                      script {
                          docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                              docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                          }
                      }
                  }
              }
         }
}