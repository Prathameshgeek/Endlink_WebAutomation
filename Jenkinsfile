pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '7'))
    }
    tools {
        jdk 'JDK-11'
        maven 'Maven-3.8.6'
    }
    triggers {
        cron('H 9 * * *')
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'github-repo-access', url: 'https://github.com/Prathameshgeek/Endlink_WebAutomation.git'
            }
        }
        stage('Build and Test') {
            steps {
                sh 'mvn clean install' 
            }
        }
    }
    post {
        always {
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: false,
                reportDir: 'Reports', // Adjust path if needed
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
            emailext(
                to: 'prathamesh@geekyants.com',
                subject: "Jenkins Build Report - Endlink Webapp",
                body: """
                Jenkins Build Report
                Job: Endlink Web_Automation Pipeline
                Build Status: ${currentBuild.currentResult}
                Extent Report: ${BUILD_URL}/artifact/Reports/ExtentReport.html

                Regards,
                Geekyants QA Team
    """,
    mimeType: 'text/html',
    attachLog: true
            )
        }
    }
}
