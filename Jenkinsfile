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
                <h2>Jenkins Build Report</h2>
               <p><b>Job:</b> Endlink Web_Automation Pipeline</p>
               <p><b>Build Status:</b> ${currentBuild.currentResult}</p>
               <p><b>Extent Report:</b> <a href="http://localhost:8080/job/Endlink%20Web_Automation%20Pipeline/Endlink_20Webapp_20Extent_20Report/">View Report</a></p>
               <br>
               <p>Regards, <br> Geekyants QA Team</p>
    """,
    mimeType: 'text/html',
    attachLog: true
            )
        }
    }
}
