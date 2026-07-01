pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/adme1664/big-data-ingestion.git'
            }
        }
        stage('Build & Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh './mvnw -B clean package'
                    } else {
                        bat 'mvnw -B clean package'
                    }
                }
            }
        }
        stage('SonarQube Analysis') {
                    steps {
                        withSonarQubeEnv('SonarQubeServer') {
                            sh './mvnw -B sonar:sonar -Dsonar.projectKey=big-data-ingestion'
                        }
                    }
                }
                stage('Quality Gate') {
                    steps {
                        timeout(time: 2, unit: 'MINUTES') {
                            waitForQualityGate abortPipeline: true
                        }
                    }
                }
    }
    post {
        always {
            // Publie le rapport des tests unitaires (visible dans "Test Result")
            junit '**/target/surefire-reports/*.xml'
            // Archive le JAR genere (telechargeable depuis la page du build)
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: true
        }
    }
}