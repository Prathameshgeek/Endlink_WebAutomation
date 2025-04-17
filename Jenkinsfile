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
                sh 'mvn clean install' // Execute Maven from the workspace root
                sh 'pwd'
                sh 'ls -al target'
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
                reportName: 'Endlink Webapp Extent Report'
            ])
            emailext(
                to: 'prathamesh@geekyants.com',
    subject: "Jenkins Build Report - Endlink Webapp",
    body: """
        <h2>Jenkins Build Report</h2>
        <p><b>Job:</b> Endlink Web_Automation Pipeline</p>
        <p><b>Build Status:</b> ${currentBuild.currentResult}</p>
        <p><b>Extent Report:</b> <a href="${JENKINS_URL}job/${JOB_NAME}/${BUILD_NUMBER}/HTML_20Report/">View Report</a></p>
        <br>
        <p>Regards, <br> Jenkins Pipeline</p>
    """,
    mimeType: 'text/html',
    attachLog: true
            )
        }
    }
}
