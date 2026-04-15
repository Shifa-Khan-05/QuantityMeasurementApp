pipeline {
    agent any
    tools {
        maven 'Maven-3' // Use the name you gave in Global Tool Configuration
        jdk 'Java-17'   // Use the name you gave in Global Tool Configuration
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t qma-backend-image .'
            }
        }
    }
}