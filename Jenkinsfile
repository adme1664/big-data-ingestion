pipeline {
    agent any
     options {
            // Garde les 10 derniers builds, et coupe un build bloque apres 30 min.
            buildDiscarder(logRotator(numToKeepStr: '10'))
            timeout(time: 30, unit: 'MINUTES')
        }
    stages {
        stage('Checkout') {
            steps {
               // mode multibranch
                checkout scm
            }
        }
        stage('Build'){
          steps{
          sh './mvnw -B clean compile'
          }
        }
        stage('Test') {
            steps {
               sh './mvnw -B test'
            }
            post{
            always {
            junit 'target/surefire-reports/*.xml'}
            }


        }
        stage('SonarQube Analysis') {
                    steps {
                        withSonarQubeEnv('SonarQubeServer') {
                            sh './mvnw -B sonar:sonar -Dsonar.projectKey=big-data-ingestion'
                        }
                    }
                }
        stage('Package') {
                    steps {
                        sh './mvnw -B package -DskipTests'
                        // Archive le JAR : telechargeable depuis la page du build / Blue Ocean
                        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    }
                }
    }
    post {
            success { echo 'Pipeline termine avec succes.' }
            failure { echo 'Echec du pipeline : consultez les logs de l etape en rouge.' }
        }
}