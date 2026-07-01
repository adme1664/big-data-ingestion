pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build') {
      steps {
        sh './mvnw -B clean compile'
      }
    }

    stage('Test') {
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }

      }
      steps {
        sh './mvnw -B test'
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
        archiveArtifacts(artifacts: 'target/*.jar', fingerprint: true)
      }
    }

  }
  post {
    success {
      echo 'Pipeline termine avec succes.'
    }

    failure {
      echo 'Echec du pipeline : consultez les logs de l etape en rouge.'
    }

  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '10'))
    timeout(time: 30, unit: 'MINUTES')
  }
}