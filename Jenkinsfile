pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
          PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
          DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
          DOCKERHUB_REPO = 'annialanen/fuel_calc'
          DOCKER_IMAGE_TAG = 'v1'
      }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/annimala/ShoppingCart.git'
            }
        }

        stage('Build & test') {
            steps {
                bat 'mvn clean install'
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